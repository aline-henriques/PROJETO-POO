// br.com.cachacaria_gomes.gerenciadorweb.historico.HistoricoPedidoModel.java

package br.com.cachacaria_gomes.gerenciadorweb.historico;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_historico_pedidos")
@NoArgsConstructor
public class HistoricoPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID pedidoId; 

    @CreationTimestamp
    private LocalDateTime dataHoraRegistro;

    private String statusAnterior; 

    private String novoStatus; 

    private String responsavel; 

    @Column(columnDefinition = "TEXT")
    private String observacao; 

    public HistoricoPedidoModel(UUID pedidoId, String statusAnterior, String novoStatus, String responsavel, String observacao) {
        this.pedidoId = pedidoId;
        this.statusAnterior = statusAnterior;
        this.novoStatus = novoStatus;
        this.responsavel = responsavel;
        this.observacao = observacao;
    }
}