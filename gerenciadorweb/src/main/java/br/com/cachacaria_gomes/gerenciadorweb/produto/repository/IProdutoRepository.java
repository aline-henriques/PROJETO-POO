package br.com.cachacaria_gomes.gerenciadorweb.produto.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;

public interface IProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

    List<ProdutoModel> findByCategoria(CategoriaProdutos categoria);
}
