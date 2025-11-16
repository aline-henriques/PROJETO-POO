package br.com.cachacaria_gomes.gerenciadorweb.estoque.api;

import br.com.cachacaria_gomes.gerenciadorweb.estoque.application.EstoqueService;
import br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.model.EstoqueBaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

// DTOs de Request (EntradaRequest, SaidaRequest) devem ser criados separadamente.
// Por simplicidade, usaremos os dados diretamente no corpo (ex: Map ou DTO simples).

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<EstoqueBaseModel>> listarEstoque() {
        List<EstoqueBaseModel> estoque = estoqueService.listarTodoEstoque();
        return ResponseEntity.ok(estoque);
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<EstoqueBaseModel>> listarAlertasBaixoEstoque() {
        // BDD: Cenário Receber alerta de baixo estoque
        List<EstoqueBaseModel> alertas = estoqueService.listarAlertas();
        return ResponseEntity.ok(alertas);
    }

    @PostMapping("/entrada")
    public ResponseEntity<EstoqueBaseModel> registrarEntrada(@RequestBody EntradaEstoqueRequest request) {
        EstoqueBaseModel produtoAtualizado = estoqueService.registrarEntrada(
            request.getProdutoId(),
            request.getQuantidade(),
            request.getMotivo(),
            request.getReferencia()
        );
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PostMapping("/saida")
    public ResponseEntity<EstoqueBaseModel> registrarSaidaManual(@RequestBody SaidaEstoqueRequest request) {
        EstoqueBaseModel produtoAtualizado = estoqueService.registrarSaida(
            request.getProdutoId(),
            request.getQuantidade(),
            request.getTipoMovimentacao(), 
            request.getMotivo(),
            request.getReferencia()
        );
        return ResponseEntity.ok(produtoAtualizado);
    }
}

// Exemplo de DTO de Request (crie no pacote DTOs)
class EntradaEstoqueRequest {
    private UUID produtoId;
    private int quantidade;
    private String motivo;
    private String referencia;
    // Getters e Setters
    public UUID getProdutoId() { return produtoId; }
    public int getQuantidade() { return quantidade; }
    public String getMotivo() { return motivo; }
    public String getReferencia() { return referencia; }
}

class SaidaEstoqueRequest extends EntradaEstoqueRequest {
    private String tipoMovimentacao; // Ex: PERDA, SAIDA
    public br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao getTipoMovimentacao() {
        return br.com.cachacaria_gomes.gerenciadorweb.estoque.domain.enums.TipoMovimentacao.valueOf(tipoMovimentacao);
    }
}