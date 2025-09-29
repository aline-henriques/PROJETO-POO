package br.com.cachacaria_gomes.gerenciadorweb.produto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "tipoProduto" 
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BebidaModel.class, name = "bebida"), 
    @JsonSubTypes.Type(value = ArtesanatoModel.class, name = "artesanato") 
})

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "tb_produtos")
@NoArgsConstructor
@AllArgsConstructor
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
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> fotosUrls;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProdutos categoria;

    private Double avaliacaoGeral;
}