package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CancelamentoDTO {
    
    // Usamos jakarta.validation
    @NotBlank(message = "O motivo do cancelamento é obrigatório.")
    private String motivo;

    // Construtor padrão (necessário)
    public CancelamentoDTO() {}

    // Getters e Setters
    public String getMotivo() { 
        return motivo; 
    }
    public void setMotivo(String motivo) { 
        this.motivo = motivo; 
    }
}