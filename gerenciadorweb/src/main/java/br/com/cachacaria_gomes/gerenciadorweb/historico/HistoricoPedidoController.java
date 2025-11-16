package br.com.cachacaria_gomes.gerenciadorweb.historico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cachacaria_gomes.gerenciadorweb.historico.dto.ObservacaoRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos") 
public class HistoricoPedidoController {

    @Autowired
    private HistoricoPedidoService historicoService;

    @GetMapping("/{pedidoId}/historico")
    public ResponseEntity<List<HistoricoPedidoModel>> getHistorico(@PathVariable UUID pedidoId) {
        
        List<HistoricoPedidoModel> historico = historicoService.buscarHistoricoPorPedido(pedidoId);
        
        if (historico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(historico);
    }

    @PostMapping("/{pedidoId}/historico/observacao")
    public ResponseEntity<HistoricoPedidoModel> addObservacao(
            @PathVariable UUID pedidoId, 
            @RequestBody ObservacaoRequest request) { 
        

        String responsavel = "Admin-X"; 
        

        HistoricoPedidoModel novoRegistro = historicoService.registrarObservacao(
            pedidoId, 
            responsavel, 
            request.getObservacao()
        );
        
        return ResponseEntity.ok(novoRegistro);
    }
    
}