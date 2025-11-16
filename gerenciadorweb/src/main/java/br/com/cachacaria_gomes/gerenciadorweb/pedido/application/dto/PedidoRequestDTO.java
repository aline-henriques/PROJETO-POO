package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PedidoRequestDTO {

    // Cliente é identificado pelo CPF (String)
    @NotBlank(message = "O CPF do cliente é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos (apenas números)")
    private String clienteId;

    @NotEmpty(message = "O pedido deve ter pelo menos um item.")
    private List<ItemPedidoRequestDTO> itens;

    private BigDecimal valorFrete = BigDecimal.ZERO; // Valor Frete padrão 0

    // Getters e Setters
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public List<ItemPedidoRequestDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoRequestDTO> itens) { this.itens = itens; }
    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }

    // DTO interno para os itens do pedido
    public static class ItemPedidoRequestDTO {
        
        @NotNull(message = "O ID do produto é obrigatório.")
        private UUID produtoId; // Assumindo que o produto usa Long ID

        @Min(value = 1, message = "A quantidade mínima é 1.")
        private Integer quantidade;

        @NotNull(message = "O preço unitário é obrigatório.")
        private BigDecimal precoUnitario; 

        // Getters e Setters
        public UUID getProdutoId() { return produtoId; }
        public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }
        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    }
}