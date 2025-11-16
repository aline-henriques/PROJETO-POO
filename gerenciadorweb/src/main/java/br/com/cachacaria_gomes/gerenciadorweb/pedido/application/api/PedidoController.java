package br.com.cachacaria_gomes.gerenciadorweb.pedido.application.api;

import br.com.cachacaria_gomes.gerenciadorweb.enums.StatusPedido;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.dto.*; // Mantido DTOs
import br.com.cachacaria_gomes.gerenciadorweb.pedido.application.service.PedidoApplicationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Importado Page
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoApplicationService pedidoService;

    // 1. POST: Criação de Novo Pedido (Correto)
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO request) {

        PedidoResponseDTO novoPedido = pedidoService.criarPedido(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoPedido.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(@Valid @ModelAttribute FiltroPedidoDTO filtro) {

        Page<PedidoResponseDTO> pedidos = pedidoService.listarPedidos(filtro);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorCliente(
            @PathVariable String clienteId) {

        // Chama o método que você já criou no Service
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorClienteId(clienteId);

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarDetalhes(@PathVariable UUID id) {

        PedidoResponseDTO pedido = pedidoService.buscarDetalhes(id);

        return ResponseEntity.ok(pedido);
    }

    // 4. PUT: Atualização do Status (Correto)
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody @Valid StatusUpdateDTO request) {

        StatusPedido novoStatus = StatusPedido.valueOf(request.getNovoStatus());

        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(pedidoAtualizado);
    }

    // 5. PATCH: Cancelamento de Pedido (Correto)
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable UUID id,
            @RequestBody @Valid CancelamentoDTO request) {

        pedidoService.cancelarPedido(id, request.getMotivo());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}