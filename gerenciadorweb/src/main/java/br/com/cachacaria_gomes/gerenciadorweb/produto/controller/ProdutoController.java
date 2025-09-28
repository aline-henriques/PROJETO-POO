package br.com.cachacaria_gomes.gerenciadorweb.produto.controller;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.service.IProdutoService; // Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired 
    private IProdutoService produtoService; 

    @GetMapping("/")
    public ResponseEntity<List<ProdutoModel>> listarTodos() {
        List<ProdutoModel> produtos = produtoService.listarTodos();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(produtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> buscarPorId(@PathVariable UUID id) {
        ProdutoModel produto = produtoService.buscarPorId(id);

        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/filtrar") // Exemplo de URL: /api/produtos/filtrar?categoria=CACHACA
    public ResponseEntity<List<ProdutoModel>> buscarPorCategoria(@RequestParam CategoriaProdutos categoria) {
        List<ProdutoModel> produtos = produtoService.buscarPorCategoria(categoria);
        
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        }
        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/")
    public ResponseEntity<ProdutoModel> salvar(@RequestBody ProdutoModel produto) {
        ProdutoModel novoProduto = produtoService.salvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoModel> deletarProduto(@PathVariable UUID id) {
        ProdutoModel produto = produtoService.buscarPorId(id);

        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }

        produtoService.deletarProduto(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 

    }

}