package br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_movimentacoes_estoque")
@Data
@NoArgsConstructor
public class MovimentacaoEstoqueModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID produtoId; // Referência ao EstoqueBaseModel

    @CreationTimestamp
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private int quantidade;
    
    @Column(name = "quantidade_final")
    private int quantidadeFinal;
    
    private String motivo;
    
    private String referencia; // ID do Pedido ou Nota Fiscal
}