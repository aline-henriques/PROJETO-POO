package br.com.cachacaria_gomes.gerenciadorweb.produto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;

@Getter
@Setter
@SuperBuilder(toBuilder = true) // Habilita o Builder para herança
@Entity
@Table(name = "tb_bebidas")
@NoArgsConstructor // Mantenha
@EqualsAndHashCode(callSuper = true)
public class BebidaModel extends ProdutoModel { 

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private Double teorAlcoolico;

    private Integer envelhecimento; 

    private String madeiraEnvelhecimento; 

    private String avaliacaoSommelier;
}