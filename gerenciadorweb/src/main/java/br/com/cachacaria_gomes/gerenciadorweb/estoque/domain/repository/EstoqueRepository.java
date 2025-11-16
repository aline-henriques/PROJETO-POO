package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.repository;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.EstoqueBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueBaseModel, UUID> {

    // Método para listar itens abaixo do limite mínimo (Alertas)
    List<EstoqueBaseModel> findByQuantidadeAtualLessThanEqualAndQuantidadeAtualGreaterThan(int limiteMinimo, int zero);

    // Método para listar itens esgotados (QuantidadeAtual <= 0)
    List<EstoqueBaseModel> findByQuantidadeAtualLessThanEqual(int zero);
    
    @Query("SELECT e FROM EstoqueBaseModel e WHERE e.quantidadeAtual <= e.limiteMinimo")
    List<EstoqueBaseModel> buscarItensComAlerta();
}