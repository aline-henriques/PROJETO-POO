package br.com.cachacaria_gomes.gerenciadorweb.historico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos") // Mantém o /pedidos no path
public class HistoricoPedidoController {

    @Autowired
    private HistoricoPedidoService historicoService;

    // Endpoint: GET /pedidos/{id}/historico
    // Cenário BDD: Visualizar histórico completo
    @GetMapping("/{pedidoId}/historico")
    // ❗ AUTORIZAÇÃO: Você deve garantir que esta rota seja acessível APENAS a administradores
    // (Isto será feito com Spring Security)
    public ResponseEntity<List<HistoricoPedidoModel>> getHistorico(@PathVariable UUID pedidoId) {
        
        // Adicionar lógica de verificação de permissão do usuário logado aqui
        
        List<HistoricoPedidoModel> historico = historicoService.buscarHistoricoPorPedido(pedidoId);
        
        if (historico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(historico);
    }
    
    // Opcionalmente, um endpoint para adicionar uma observação
    // POST /pedidos/{id}/historico/observacao
    /*
    @PostMapping("/{pedidoId}/historico/observacao")
    public ResponseEntity<HistoricoPedidoModel> addObservacao(@PathVariable UUID pedidoId, 
                                                            @RequestBody ObservacaoRequest request) {
        // ... Lógica para pegar o responsável (admin) e salvar ...
        // return ResponseEntity.ok(historicoService.registrarObservacao(...));
    }
    */
}