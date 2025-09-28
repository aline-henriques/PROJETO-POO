package br.com.cachacaria_gomes.gerenciadorweb.produto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@Data
@Entity
@Table(name = "tb_bebidas")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true) 
public class BebidaModel extends ProdutoModel { 

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private Double teorAlcoolico;

    private Integer tempoEnvelhecimentoMeses; 

    private String madeiraEnvelhecimento; 

    private String avaliacaoSommelier;
}