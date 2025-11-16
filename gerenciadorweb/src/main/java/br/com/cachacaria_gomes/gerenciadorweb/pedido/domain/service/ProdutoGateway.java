package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

// Define o contrato de comunicação entre Pedido e Produto
public interface ProdutoGateway {
    
    boolean produtoExiste(UUID produtoId);
    
    // Método para buscar o preço atual do produto
    BigDecimal buscarPrecoUnitario(UUID produtoId); 
}