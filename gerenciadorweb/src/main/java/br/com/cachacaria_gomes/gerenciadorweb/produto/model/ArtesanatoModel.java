package br.com.cachacaria_gomes.gerenciadorweb.produto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter; 
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import jakarta.persistence.Column;


@Getter
@Setter
@Entity
@Table(name = "tb_artesanatos")
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ArtesanatoModel extends ProdutoModel {

    @Column(nullable = false)
    private String tipoArtesanato;

    @Column(nullable = false)
    private String materialPrincipal;

    private String dimensoes; 

    private BigDecimal pesoKg;

    private String avaliacaoArtesao;
}