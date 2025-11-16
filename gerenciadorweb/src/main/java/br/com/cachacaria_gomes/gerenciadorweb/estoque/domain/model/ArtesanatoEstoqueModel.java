package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ARTESANATO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ArtesanatoEstoqueModel extends EstoqueBaseModel {
    private String material; // Ex: madeira, barro, palha
}