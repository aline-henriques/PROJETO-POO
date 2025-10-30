package br.com.cachacaria_gomes.gerenciadorweb.produto.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoProduto")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BebidaModel.class, name = "bebida"),
        @JsonSubTypes.Type(value = ArtesanatoModel.class, name = "artesanato")
})

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "tb_produtos")
@NoArgsConstructor
public abstract class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEmEstoque;

    @Column(name = "fotos_urls", length = 1000, nullable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> fotosUrls;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProdutos categoria;

    private Double avaliacaoGeral;
}