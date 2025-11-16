package br.com.cachacaria_gomes.gerenciadorweb.produto.service; // Ou .gateway

import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service.ProdutoGateway;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.repository.IProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

// Implementação que usa o ProdutoRepository
@Service
public class ProdutoGatewayImpl implements ProdutoGateway { 

    @Autowired
    private IProdutoRepository produtoRepository;

    @Override
    public boolean produtoExiste(UUID produtoId) {
        return produtoRepository.existsById(produtoId);
    }

    @Override
    public BigDecimal buscarPrecoUnitario(UUID produtoId) {
        ProdutoModel produto = produtoRepository.findById(produtoId)
            .orElse(null); // Retorna null se não encontrar

        // Se o preço for nulo ou o produto não existir, você pode lançar uma exceção ou retornar 0
        if (produto == null || produto.getPreco() == null) {
            return BigDecimal.ZERO; 
        }
        return produto.getPreco();
    }
}