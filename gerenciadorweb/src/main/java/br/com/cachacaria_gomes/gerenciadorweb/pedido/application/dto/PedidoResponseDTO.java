package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.Pedido;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value 
public class PedidoResponseDTO {
    UUID id;
    String status;
    String clienteId;

    BigDecimal valorTotal;
    LocalDateTime dataCriacao;
    String motivoCancelamento;
    
    List<ItemPedidoResponseDTO> itens; 

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.status = pedido.getStatus().name();
        this.clienteId = pedido.getClienteId();
        this.valorTotal = pedido.getValorTotal();
        this.dataCriacao = pedido.getDataCriacao();
        this.motivoCancelamento = pedido.getMotivoCancelamento();
        
        // MAPEAMENTO DOS ITENS: Converte cada ItemPedido para ItemPedidoResponseDTO
        this.itens = pedido.getItens().stream()
                .map(ItemPedidoResponseDTO::new)
                .collect(Collectors.toList());
    }
}