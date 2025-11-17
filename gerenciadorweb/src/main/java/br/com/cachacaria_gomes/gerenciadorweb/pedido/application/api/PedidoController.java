package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.api;

import java.net.URI;
import java.util.List; // Mantido DTOs
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus; // Importado Page
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.CancelamentoDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.FiltroPedidoDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.PedidoRequestDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.PedidoResponseDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.StatusUpdateDTO;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.service.PedidoApplicationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoApplicationService pedidoService;

    // 1. POST: Criação de Novo Pedido (Correto)
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO request) {

        PedidoResponseDTO novoPedido = pedidoService.criarPedido(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoPedido.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(@Valid @ModelAttribute FiltroPedidoDTO filtro) {

        Page<PedidoResponseDTO> pedidos = pedidoService.listarPedidos(filtro);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorCliente(
            @PathVariable String clienteId) {

        // Chama o método que você já criou no Service
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorClienteId(clienteId);

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarDetalhes(@PathVariable UUID id) {

        PedidoResponseDTO pedido = pedidoService.buscarDetalhes(id);

        return ResponseEntity.ok(pedido);
    }

    // 4. PUT: Atualização do Status (Correto)
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody @Valid StatusUpdateDTO request) {

        StatusPedido novoStatus = StatusPedido.valueOf(request.getNovoStatus());

        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(pedidoAtualizado);
    }

    // 5. PUT: Cancelamento de Pedido (Correto)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable UUID id,
            @RequestBody @Valid CancelamentoDTO request) {

        pedidoService.cancelarPedido(id, request.getMotivo());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}