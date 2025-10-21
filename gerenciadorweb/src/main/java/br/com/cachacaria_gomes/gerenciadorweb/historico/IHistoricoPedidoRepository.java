package br.com.cachacaria_gomes.gerenciadorweb.historico;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface IHistoricoPedidoRepository extends JpaRepository<HistoricoPedidoModel, UUID> {
    

    List<HistoricoPedidoModel> findByPedidoIdOrderByDataHoraRegistroAsc(UUID pedidoId);
}