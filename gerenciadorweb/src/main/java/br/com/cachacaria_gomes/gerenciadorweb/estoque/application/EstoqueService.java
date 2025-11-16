package br.com.cachacaria_gomes.gerenciadorweb.estoque.application;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.BebidaEstoqueModel;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.EstoqueBaseModel;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.MovimentacaoEstoqueModel;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.repository.EstoqueRepository;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.repository.MovimentacaoRepository;
import br.com.cachacaria_gomes.gerenciadorweb.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    // ------------------------------------
    // --- MÉTODOS DE LEITURA (Consulta) ---
    // ------------------------------------

    public List<EstoqueBaseModel> listarTodoEstoque() {
        return estoqueRepository.findAll();
    }

    public List<EstoqueBaseModel> listarAlertas() {
        // Assume que 'buscarItensComAlerta' está definido no EstoqueRepository
        return estoqueRepository.buscarItensComAlerta();
    }

    // -----------------------------------------------------
    // --- MÉTODOS DE INICIALIZAÇÃO E MOVIMENTAÇÃO (Escrita) ---
    // -----------------------------------------------------

    /**
     * Cria o registro inicial de estoque para um novo produto (Tipo Bebida).
     * Deve ser chamado pelo ProdutoService após a criação de uma nova
     * Bebida/Artesanato.
     * 
     * @param produtoId   O ID do produto a ser inicializado (que será o ID do
     *                    EstoqueBaseModel).
     * @param nomeProduto O nome do produto.
     * @return O objeto EstoqueBaseModel recém-criado.
     */
    public EstoqueBaseModel inicializarEstoqueParaNovoProduto(UUID produtoId, String nomeProduto,
            int quantidadeInicial) {

        // 1. Cria e instancia o modelo concreto (usando BebidaEstoqueModel como
        // exemplo)
        BebidaEstoqueModel novoEstoque = new BebidaEstoqueModel();

        // 2. Atribuição dos valores iniciais
        novoEstoque.setId(produtoId);
        novoEstoque.setNome(nomeProduto);

        // AQUI: Usa a quantidade recebida
        novoEstoque.setQuantidadeAtual(quantidadeInicial);

        novoEstoque.setLimiteMinimo(10);

        // 3. Persiste o registro
        return estoqueRepository.save(novoEstoque);
    }

    /**
     * Registra a entrada de produtos no estoque (Compra, Devolução, Ajuste
     * Positivo).
     * 
     * @param produtoId  ID do produto.
     * @param quantidade Quantidade que entra.
     */
    @Transactional
    public EstoqueBaseModel registrarEntrada(UUID produtoId, int quantidade, String motivo, String referencia) {
        // Encontra o registro de estoque ou lança exceção (Corrige o NotFoundException)
        EstoqueBaseModel produto = estoqueRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto de estoque não encontrado."));

        int quantidadeAnterior = produto.getQuantidadeAtual();
        produto.setQuantidadeAtual(quantidadeAnterior + quantidade);
        EstoqueBaseModel produtoAtualizado = estoqueRepository.save(produto);

        registrarMovimentacao(produtoId, TipoMovimentacao.ENTRADA, quantidade, produtoAtualizado.getQuantidadeAtual(),
                motivo, referencia);

        return produtoAtualizado;
    }

    /**
     * Registra a saída de produtos (Venda, Perda, Ajuste Negativo).
     * Usado pelo PedidoService (Tipo: SAIDA) e Saída Manual (Tipo: SAIDA ou PERDA).
     * 
     * @param produtoId  ID do produto.
     * @param quantidade Quantidade que sai.
     * @param tipo       Tipo de movimentação (SAIDA ou PERDA).
     */
    @Transactional
    public EstoqueBaseModel registrarSaida(UUID produtoId, int quantidade, TipoMovimentacao tipo, String motivo,
            String referencia) {
        // Encontra o registro de estoque ou lança exceção (Corrige o NotFoundException)
        EstoqueBaseModel produto = estoqueRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto de estoque não encontrado."));

        int quantidadeAnterior = produto.getQuantidadeAtual();
        int novaQuantidade = quantidadeAnterior - quantidade;

        // Regra de Negócio: Estoque não pode ficar negativo.
        if (novaQuantidade < 0) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome() + ". Necessário: "
                    + quantidade + ", Disponível: " + quantidadeAnterior);
        }

        produto.setQuantidadeAtual(novaQuantidade);
        EstoqueBaseModel produtoAtualizado = estoqueRepository.save(produto);

        registrarMovimentacao(produtoId, tipo, quantidade, produtoAtualizado.getQuantidadeAtual(), motivo, referencia);

        return produtoAtualizado;
    }

    // ----------------------------------
    // --- MÉTODO AUXILIAR (Movimentação) ---
    // ----------------------------------

    /**
     * Registra o log detalhado de qualquer mudança de estoque.
     */
    private void registrarMovimentacao(UUID produtoId, TipoMovimentacao tipo, int quantidade, int quantidadeFinal,
            String motivo, String referencia) {
        MovimentacaoEstoqueModel movimentacao = new MovimentacaoEstoqueModel();
        movimentacao.setProdutoId(produtoId);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setQuantidadeFinal(quantidadeFinal);
        movimentacao.setMotivo(motivo);
        movimentacao.setReferencia(referencia);
        movimentacaoRepository.save(movimentacao);
    }
}