package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums;

public enum TipoMovimentacao {
    ENTRADA,        // Compra de fornecedores, devoluções
    SAIDA,          // Pedidos confirmados, vendas manuais
    PERDA,          // Danos, extravios
    CANCELAMENTO    // Devolução automática após cancelamento de pedido
}