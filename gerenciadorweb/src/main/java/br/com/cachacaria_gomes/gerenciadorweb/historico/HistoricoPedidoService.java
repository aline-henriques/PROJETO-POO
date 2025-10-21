package br.com.cachacaria_gomes.gerenciadorweb.historico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class HistoricoPedidoService {

    @Autowired
    private IHistoricoPedidoRepository historicoRepository;

    // 1. Método de Leitura (para o Endpoint GET)
    public List<HistoricoPedidoModel> buscarHistoricoPorPedido(UUID pedidoId) {
        return historicoRepository.findByPedidoIdOrderByDataHoraRegistroAsc(pedidoId);
    }
    
    // 2. Método de Criação (usado por outros Services, como o PedidoService)
    public HistoricoPedidoModel registrarAlteracaoStatus(UUID pedidoId, String novoStatus, String responsavel, String observacao) {
        var novoRegistro = new HistoricoPedidoModel(pedidoId, novoStatus, responsavel, observacao);
        
        // Se a observação estiver vazia e for apenas uma mudança de status
        if (observacao == null || observacao.trim().isEmpty()) {
            novoRegistro.setObservacao("Status alterado para: " + novoStatus);
        }
        
        return historicoRepository.save(novoRegistro);
    }
    
    // 3. Método para adicionar observação manual (Cenário BDD: Adicionar observação manual)
    public HistoricoPedidoModel registrarObservacao(UUID pedidoId, String responsavel, String observacao) {
        // Usa o status atual (que deve ser recuperado do PedidoModel) ou um marcador
        String statusAtual = "OBSERVACAO MANUAL"; 
        
        var novoRegistro = new HistoricoPedidoModel(pedidoId, statusAtual, responsavel, observacao);
        
        return historicoRepository.save(novoRegistro);
    }
}