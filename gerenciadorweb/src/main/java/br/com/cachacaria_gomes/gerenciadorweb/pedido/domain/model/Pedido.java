package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import br.com.cachacaria_gomes.gerenciadorweb.exceptions.DomainException;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false, length = 11)
    private String clienteId;

    // Detalhes do Endereço de Entrega (Value Object)
    @Embedded
    private EnderecoEntrega enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFrete;

    private String motivoCancelamento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataEntrega;

    // Construtor padrão exigido pelo JPA
    public Pedido() {
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
    }

    // Getters and Setters (Apenas essenciais para JPA/Framework. Os métodos de
    // domínio são preferidos para alteração)

    public UUID getId() {
        return id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public String getClienteId() {
        return clienteId;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    // Setters básicos
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    // MÉTODOS DE DOMÍNIO (Comportamento e Regras)

    public void atualizarStatus(StatusPedido novoStatus) {
        // Cenário: Tentar alterar status de pedido cancelado
        if (this.status == StatusPedido.CANCELADO) {
            throw new DomainException("Não é possível alterar o status de um pedido cancelado.");
        }

        // Exemplo: Não pode ir para 'Enviado' se não estiver 'Pago'
        if (novoStatus == StatusPedido.ENVIADO && this.status != StatusPedido.PAGO
                && this.status != StatusPedido.EM_SEPARACAO) {
            throw new DomainException("O pedido precisa estar pago ou em separação para ser enviado.");
        }

        // Cenário: Concluir pedido - Registrar data de entrega
        if (novoStatus == StatusPedido.ENTREGUE) {
            if (this.status != StatusPedido.ENVIADO && this.status != StatusPedido.EM_TRANSITO) {
                throw new DomainException("O pedido deve estar 'Enviado' ou 'Em Trânsito' para ser 'Entregue'.");
            }
            this.dataEntrega = LocalDateTime.now();
        }

        this.status = novoStatus;
    }

    public void cancelar(String motivo) {
        // Regra: Não pode cancelar se já foi entregue
        if (this.status == StatusPedido.ENTREGUE) {
            throw new DomainException("Pedido entregue não pode ser cancelado.");
        }

        this.status = StatusPedido.CANCELADO;
        this.motivoCancelamento = motivo;
        // Lógica de domínio: Disparar evento para estornar estoque, se necessário
    }
}