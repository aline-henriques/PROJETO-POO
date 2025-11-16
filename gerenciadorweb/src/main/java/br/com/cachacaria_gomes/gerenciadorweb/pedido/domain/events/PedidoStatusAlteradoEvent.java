// br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.events.PedidoStatusAlteradoEvent.java

package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.events;

import org.springframework.context.ApplicationEvent;
import java.util.UUID;

public class PedidoStatusAlteradoEvent extends ApplicationEvent {
    
    private final UUID pedidoId;
    private final String statusAnterior;
    private final String novoStatus;
    private final String responsavel;
    private final String observacao;

    public PedidoStatusAlteradoEvent(Object source, UUID pedidoId, String statusAnterior, String novoStatus, String responsavel, String observacao) {
        super(source);
        this.pedidoId = pedidoId;
        this.statusAnterior = statusAnterior;
        this.novoStatus = novoStatus;
        this.responsavel = responsavel;
        this.observacao = observacao;
    }
    
    public UUID getPedidoId() { return pedidoId; }
    public String getStatusAnterior() { return statusAnterior; }
    public String getNovoStatus() { return novoStatus; }
    public String getResponsavel() { return responsavel; }
    public String getObservacao() { return observacao; }
}