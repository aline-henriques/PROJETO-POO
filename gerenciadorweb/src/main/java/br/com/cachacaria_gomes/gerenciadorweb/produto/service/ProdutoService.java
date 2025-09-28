package br.com.cachacaria_gomes.gerenciadorweb.produto.service;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.repository.IProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService implements IProdutoService {

    @Autowired
    private IProdutoRepository produtoRepository; 

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
    public ProdutoModel salvarProduto(ProdutoModel produto) {
        return produtoRepository.save(produto);
    }
    
    @Override
    public void deletarProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}
