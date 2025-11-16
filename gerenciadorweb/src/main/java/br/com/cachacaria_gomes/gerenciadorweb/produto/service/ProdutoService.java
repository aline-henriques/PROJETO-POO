package br.com.cachacaria_gomes.gerenciadorweb.produto.service;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.application.EstoqueService;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.repository.IProdutoRepository;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService implements IProdutoService {

    @Autowired
    private IProdutoRepository produtoRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired 
    private EntityManager entityManager;

    @Override
    public List<ProdutoModel> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public ProdutoModel buscarPorId(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Override
    public List<ProdutoModel> buscarPorCategoria(CategoriaProdutos categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria de produto não pode ser nula.");
        }
        return produtoRepository.findByCategoria(categoria);
    }

    @Override
    @Transactional // ESSA transação é essencial para coordenar as duas chamadas
    public ProdutoModel salvarProduto(ProdutoModel produto) {

        ProdutoModel produtoSalvo = produtoRepository.save(produto);

        entityManager.flush();

        // Chama o método sem transação própria
        estoqueService.inicializarEstoqueParaNovoProduto(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getQuantidadeEmEstoque());

        return produtoSalvo;
    }

    @Override
    public void deletarProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}
