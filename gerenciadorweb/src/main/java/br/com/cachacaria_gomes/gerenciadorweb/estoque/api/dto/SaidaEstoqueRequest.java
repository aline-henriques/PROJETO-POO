package br.com.cachacaria_gomes.gerenciadorweb.estoque.api.dto;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao;
import java.util.UUID;
import lombok.Data;

@Data
public class SaidaEstoqueRequest {
    private UUID produtoId;
    private int quantidade;
    private String motivo; // Ex: "Perda por quebra", "Venda manual"
    private String referencia; // Ex: "Ajuste manual"
    private TipoMovimentacao tipoMovimentacao; // Deve ser SAIDA ou PERDA
}