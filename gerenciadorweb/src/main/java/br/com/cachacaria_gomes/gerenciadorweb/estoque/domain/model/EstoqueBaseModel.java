package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_produto", discriminatorType = DiscriminatorType.STRING)
@Table(name = "tb_estoque")
@Data
@NoArgsConstructor
public abstract class EstoqueBaseModel {
    @Id
    private UUID id;

    private String nome;

    @Column(name = "unidade_venda")
    private String unidadeVenda;

    @Column(name = "quantidade_atual")
    private int quantidadeAtual;

    @Column(name = "limite_minimo")
    private int limiteMinimo;

    // Métodos para Regras de Negócio do Frontend
    public boolean isEstoqueBaixo() {
        return quantidadeAtual > 0 && quantidadeAtual <= limiteMinimo;
    }

    public boolean isEsgotado() {
        return quantidadeAtual <= 0;
    }
}