package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.repository;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.MovimentacaoEstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoqueModel, UUID> {
    // Você pode adicionar métodos de busca por produtoId, data, etc. aqui.
}