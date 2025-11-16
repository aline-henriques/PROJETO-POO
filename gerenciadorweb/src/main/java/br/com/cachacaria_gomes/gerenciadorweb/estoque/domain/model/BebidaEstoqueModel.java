package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("BEBIDA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BebidaEstoqueModel extends EstoqueBaseModel {
    private String volume; // Ex: "700ml / 40%"

    @Column(name = "graduacao_alcoolica")
    private String graduacaoAlcoolica;

    @Column(name = "restricao_idade")
    private boolean restricaoIdade = true; // Sempre true para bebidas
}