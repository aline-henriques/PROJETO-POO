package br.com.cachacaria_gomes.gerenciadorweb.produto.service;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import java.util.List;
import java.util.UUID;

public interface IProdutoService {
    
    List<ProdutoModel> listarTodos();
    ProdutoModel buscarPorId(UUID id);
    List<ProdutoModel> buscarPorCategoria(CategoriaProdutos categoria);
    ProdutoModel salvarProduto(ProdutoModel produto);
    void deletarProduto(UUID id);
}
