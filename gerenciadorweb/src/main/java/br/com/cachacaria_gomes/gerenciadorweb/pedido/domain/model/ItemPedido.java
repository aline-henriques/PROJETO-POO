package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido; // Referência à classe Pedido

    @Column(nullable = false)
    private UUID produtoId; 

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;
    
    // Construtor padrão exigido pelo JPA
    public ItemPedido() {}

    // Getters and Setters (essenciais)
    public UUID getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public UUID getProdutoId() { return produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}