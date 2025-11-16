// br.com.cachacaria_gomes.gerenciadorweb.historico.HistoricoPedidoService.java

package br.com.cachacaria_gomes.gerenciadorweb.historico;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener; // ⬅️ IMPORTAR ESTE
import org.springframework.transaction.event.TransactionPhase; // ⬅️ IMPORTAR ESTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.events.PedidoStatusAlteradoEvent;

import java.util.List;
import java.util.UUID;

@Service
public class HistoricoPedidoService {

    @Autowired
    private IHistoricoPedidoRepository historicoRepository;

    // 1. Listener do Evento (CORRIGIDO E TRANSACIONAL)
    @Async // Executa em um thread separado
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // Roda APÓS o commit do Pedido
    public void handleStatusAlterado(PedidoStatusAlteradoEvent event) {
        
        // Construtor CORRETO (5 ARGUMENTOS)
        var novoRegistro = new HistoricoPedidoModel(
            event.getPedidoId(),        // 1. UUID pedidoId
            event.getStatusAnterior(),  // 2. String statusAnterior
            event.getNovoStatus(),      // 3. String novoStatus
            event.getResponsavel(),     // 4. String responsavel
            event.getObservacao()       // 5. String observacao
        );

        historicoRepository.save(novoRegistro);
    }

    // 2. Método de Leitura (para o Endpoint GET)
    public List<HistoricoPedidoModel> buscarHistoricoPorPedido(UUID pedidoId) {
        return historicoRepository.findByPedidoIdOrderByDataHoraRegistroAsc(pedidoId);
    }

    // 3. Método para adicionar observação manual (CORRIGIDO)
    public HistoricoPedidoModel registrarObservacao(UUID pedidoId, String responsavel, String observacao) {

        String marcador = "OBSERVACAO MANUAL";

        // Construtor CORRETO (5 ARGUMENTOS)
        var novoRegistro = new HistoricoPedidoModel(
            pedidoId, 
            marcador,       // statusAnterior
            marcador,       // novoStatus
            responsavel, 
            observacao
        );

        return historicoRepository.save(novoRegistro);
    }
}