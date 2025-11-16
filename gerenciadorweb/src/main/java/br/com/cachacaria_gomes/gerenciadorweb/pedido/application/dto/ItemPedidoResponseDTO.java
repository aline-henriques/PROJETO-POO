package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.ItemPedido;
import lombok.Value;
import java.math.BigDecimal;
import java.util.UUID;

@Value // Usando @Value do Lombok para criar um DTO imutável
public class ItemPedidoResponseDTO {
    UUID produtoId;
    Integer quantidade;
    BigDecimal precoUnitario;
    
    // Opcional: Adicionar o Nome do Produto (precisaria ser buscado/armazenado)
    // String nomeProduto; 

    public ItemPedidoResponseDTO(ItemPedido item) {
        this.produtoId = item.getProdutoId();
        this.quantidade = item.getQuantidade();
        this.precoUnitario = item.getPrecoUnitario();
    }
}