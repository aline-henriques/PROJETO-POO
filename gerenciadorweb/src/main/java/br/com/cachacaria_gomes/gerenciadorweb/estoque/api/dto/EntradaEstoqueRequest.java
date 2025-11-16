package br.com.cachacaria_gomes.gerenciadorweb.estoque.api.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class EntradaEstoqueRequest {
    private UUID produtoId;
    private int quantidade;
    private String motivo; // Ex: "Compra Fornecedor NF 1234"
    private String referencia; // Ex: Número da Nota Fiscal
}