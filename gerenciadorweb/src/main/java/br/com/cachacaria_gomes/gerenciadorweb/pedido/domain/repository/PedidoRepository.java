package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.repository;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

       /**
        * Busca pedidos aplicando filtros dinâmicos e paginação.
        */
       @Query("SELECT p FROM Pedido p WHERE " +
                     "(:status IS NULL OR p.status = :status) AND " +
                     "(:dataInicial IS NULL OR p.dataCriacao >= :dataInicial) AND " +
                     "(:dataFinal IS NULL OR p.dataCriacao <= :dataFinal) AND " +
                     "(:clienteId IS NULL OR p.clienteId = :clienteId) AND " +
                     "(:valorMinimo IS NULL OR p.valorTotal >= :valorMinimo) AND " +
                     "(:valorMaximo IS NULL OR p.valorTotal <= :valorMaximo) " +
                     "ORDER BY p.dataCriacao DESC")
       Page<Pedido> buscarComFiltros(
                     @Param("status") StatusPedido status,
                     @Param("dataInicial") LocalDateTime dataInicial,
                     @Param("dataFinal") LocalDateTime dataFinal,
                     @Param("clienteId") String clienteId,
                     @Param("valorMinimo") LocalDateTime valorMinimo,
                     @Param("valorMaximo") LocalDateTime valorMaximo,
                     Pageable pageable);

       List<Pedido> findByClienteId(String clienteId);
}