package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto;

import jakarta.validation.constraints.NotBlank;

public class StatusUpdateDTO {
    
    @NotBlank(message = "O novo status é obrigatório.")
    private String novoStatus; // Ex: "PAGO", "ENVIADO"

    // Construtor padrão
    public StatusUpdateDTO() {}

    // Getters e Setters
    public String getNovoStatus() { return novoStatus; }
    public void setNovoStatus(String novoStatus) { this.novoStatus = novoStatus; }
}