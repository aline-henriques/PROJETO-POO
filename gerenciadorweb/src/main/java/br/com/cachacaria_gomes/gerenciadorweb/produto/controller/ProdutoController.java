package br.com.cachacaria_gomes.gerenciadorweb.produto.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired; // Importante
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.service.IProdutoService;

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

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarProduto(
        @PathVariable UUID id,
        @RequestBody ProdutoModel produtoAtualizado) {

        ProdutoModel produtoExistente = produtoService.buscarPorId(id);

        if (produtoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        produtoAtualizado.setId(id); // garante que o id seja o mesmo
        ProdutoModel atualizado = produtoService.salvarProduto(produtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

}