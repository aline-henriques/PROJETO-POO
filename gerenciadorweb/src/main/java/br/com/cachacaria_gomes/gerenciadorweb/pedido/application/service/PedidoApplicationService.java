package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.service;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.application.EstoqueService;
import br.com.cachacaria_gomes.gerenciadorweb.exceptions.NotFoundException;
import br.com.cachacaria_gomes.gerenciadorweb.historico.HistoricoPedidoService;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.FiltroPedidoDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.PedidoRequestDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.PedidoRequestDTO.ItemPedidoRequestDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.PedidoResponseDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.events.PedidoStatusAlteradoEvent;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.EnderecoEntrega;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.ItemPedido;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.Pedido;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.repository.PedidoRepository;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service.ClienteGateway;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service.ProdutoGateway;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.application.EstoqueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoApplicationService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private HistoricoPedidoService historicoService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ClienteGateway clienteGateway; // Injete a interface ClienteGateway

    @Autowired
    private ProdutoGateway produtoGateway;

    @Autowired
    private EstoqueService estoqueService;

    // --- MÉTODOS DE BUSCA E FILTRO ---

    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> listarPedidos(FiltroPedidoDTO filtro) {
        Pageable pageable = PageRequest.of(filtro.getPagina(), filtro.getTamanho());

        Page<Pedido> pedidosPage = pedidoRepository.buscarComFiltros(
                filtro.getStatus(),
                filtro.getDataInicial(),
                filtro.getDataFinal(),
                filtro.getClienteId(),
                filtro.getValorMinimo(),
                filtro.getValorMaximo(),
                pageable);

        return pedidosPage.map(PedidoResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarDetalhes(UUID pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + pedidoId));
        return new PedidoResponseDTO(pedido);
    }

    // --- MÉTODO DE CRIAÇÃO (POST) ---

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO request) {

        String cpf = request.getClienteId();

        // 1. Validação e Busca de Dados Externos (Cliente)
        if (!clienteGateway.clienteExiste(cpf)) {
            throw new NotFoundException("Cliente não encontrado com CPF: " + cpf);
        }

        EnderecoEntrega enderecoEntrega = clienteGateway.buscarEnderecoPorCpf(cpf); // Uso do CPF (String)

        if (enderecoEntrega == null) {
            throw new NotFoundException("Endereço do cliente não pode ser encontrado ou é inválido.");
        }

        // 2. Montar o Agregado Pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(cpf);
        pedido.setEnderecoEntrega(enderecoEntrega);

        // 3. Calcular Itens e Total
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDto : request.getItens()) {

            if (!produtoGateway.produtoExiste(itemDto.getProdutoId())) {
                throw new NotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId());
            }

            BigDecimal precoUsado = itemDto.getPrecoUnitario();

            BigDecimal subtotal = precoUsado.multiply(
                    BigDecimal.valueOf(itemDto.getQuantidade()));
            valorTotal = valorTotal.add(subtotal);

            // Adicionar ItemPedido
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProdutoId(itemDto.getProdutoId());
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(precoUsado); // Use o preço

            pedido.getItens().add(item);
        }

        BigDecimal valorFrete = request.getValorFrete() != null ? request.getValorFrete() : BigDecimal.ZERO;
        valorTotal = valorTotal.add(valorFrete);

        pedido.setValorFrete(valorFrete);
        pedido.setValorTotal(valorTotal);

        // 4. Persistir
        Pedido novoPedido = pedidoRepository.save(pedido);

        String responsavel = novoPedido.getClienteId();
        String observacao = "Pedido criado com sucesso. Aguardando confirmação de pagamento.";
        String statusInicial = novoPedido.getStatus().toString();

        eventPublisher.publishEvent(new PedidoStatusAlteradoEvent(
                this,
                novoPedido.getId(),
                "INICIALIZACAO",
                statusInicial,
                responsavel,
                observacao));

        // 6. Retornar DTO
        return new PedidoResponseDTO(novoPedido);

    }

    // --- MÉTODOS DE ATUALIZAÇÃO E CANCELAMENTO ---

@Transactional
public PedidoResponseDTO atualizarStatus(UUID pedidoId, StatusPedido novoStatus) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

    StatusPedido statusAnterior = pedido.getStatus();
    
    // 1. Aplica a mudança de status no domínio
    pedido.atualizarStatus(novoStatus); 
    
    // A variável 'pedido' é a que foi modificada.
    // Nenhuma nova variável 'pedidoAtualizado' é necessária.

    if (novoStatus == StatusPedido.PAGO) {
        reduzirEstoque(pedido); // Usa 'pedido'
    } else if (novoStatus == StatusPedido.CANCELADO && statusAnterior != StatusPedido.CANCELADO) {
        devolverEstoque(pedido); // Usa 'pedido'
    }

    // 2. Persiste as mudanças no Pedido (usa o objeto 'pedido' existente)
    Pedido pedidoSalvo = pedidoRepository.save(pedido); 

    String responsavel = "Admin-X";
    String observacao = "Status alterado para " + novoStatus.toString() + " via interface administrativa.";

    // Dispara Evento (usa 'pedidoSalvo')
    eventPublisher.publishEvent(new PedidoStatusAlteradoEvent(
            this,
            pedidoSalvo.getId(), // Usa 'pedidoSalvo'
            novoStatus.toString(),
            responsavel,
            observacao, observacao));

    // 3. Retorna o DTO de Resposta (usa 'pedidoSalvo')
    return new PedidoResponseDTO(pedidoSalvo);
}

    @Transactional
    public void cancelarPedido(UUID pedidoId, String motivoCancelamento) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        StatusPedido statusAnterior = pedido.getStatus();

        pedido.cancelar(motivoCancelamento);

        String responsavel = "Admin-X";

        eventPublisher.publishEvent(new PedidoStatusAlteradoEvent(
                this,
                pedidoId,
                statusAnterior.toString(),
                StatusPedido.CANCELADO.toString(),
                responsavel,
                "Cancelado. Motivo: " + motivoCancelamento));

        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorClienteId(String clienteId) {

        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        return pedidos.stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void reduzirEstoque(Pedido pedido) {
        String referencia = "Pedido-" + pedido.getId().toString();
        String motivo = "Saída automática por Pedido Aprovado/Pago.";

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.registrarSaida(
                    item.getProdutoId(),
                    item.getQuantidade(),
                    TipoMovimentacao.SAIDA,
                    motivo,
                    referencia);
        }
    }

    // Método auxiliar para devolução (chamado quando CANCELADO)
    private void devolverEstoque(Pedido pedido) {
        String referencia = "Pedido-" + pedido.getId().toString();
        String motivo = "Devolução automática por Pedido Cancelado.";

        for (ItemPedido item : pedido.getItens()) {
            // Usa registrarEntrada para a devolução
            estoqueService.registrarEntrada(
                    item.getProdutoId(),
                    item.getQuantidade(),
                    motivo,
                    referencia);
        }
    }
}