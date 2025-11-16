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

    @Query("SELECT p FROM Pedido p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(p.dataCriacao >= COALESCE(:dataInicial, p.dataCriacao)) AND " + 
           "(p.dataCriacao <= COALESCE(:dataFinal, p.dataCriacao)) AND " +
           "(:clienteId IS NULL OR p.clienteId = :clienteId) AND " +
           "(p.valorTotal >= COALESCE(:valorMinimo, p.valorTotal)) AND " +
           "(p.valorTotal <= COALESCE(:valorMaximo, p.valorTotal)) " +
           "ORDER BY p.dataCriacao DESC")
    Page<Pedido> buscarComFiltros(
            @Param("status") StatusPedido status,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("clienteId") String clienteId,
            @Param("valorMinimo") BigDecimal valorMinimo,
            @Param("valorMaximo") BigDecimal valorMaximo,
            Pageable pageable);

    List<Pedido> findByClienteId(String clienteId);
}