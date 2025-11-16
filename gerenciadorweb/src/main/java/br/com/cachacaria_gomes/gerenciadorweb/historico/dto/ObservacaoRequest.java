package br.com.cachacaria_gomes.gerenciadorweb.historico.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value // Usamos @Value do Lombok para criar um DTO imutável
public class ObservacaoRequest {
    
    // O campo que conterá o texto da observação
    @NotBlank(message = "A observação não pode ser vazia.")
    String observacao;
}