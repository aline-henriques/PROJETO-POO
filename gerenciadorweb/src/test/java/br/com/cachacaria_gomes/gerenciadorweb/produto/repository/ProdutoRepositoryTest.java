package br.com.cachacaria_gomes.gerenciadorweb.produto.repository;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.BebidaModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de Persistência (Repository) para IProdutoRepository.
 * Utiliza @DataJpaTest para carregar apenas a infraestrutura JPA.
 * O perfil 'test' garante que o application-test.properties seja usado.
 */
@DataJpaTest
@ActiveProfiles("test") // Garante que o application-test.properties seja carregado
public class ProdutoRepositoryTest {

    @Autowired
    private IProdutoRepository produtoRepository; // Repositório a ser testado

    @Autowired
    private TestEntityManager entityManager; // Utilitário para interagir com o DB em testes

    private BebidaModel cachacaOuro;
    private BebidaModel cachaçaPrata;

    @BeforeEach
    void setUp() {
        // Objeto 1: Cachaça de Ouro (Produto do tipo BebidaModel)
        cachacaOuro = BebidaModel.builder()
            .nome("Cachaça Ouro Envelhecida")
            .preco(new BigDecimal("100.00"))
            .quantidadeEmEstoque(50)
            .fotosUrls(Arrays.asList("url/ouro"))
            .categoria(CategoriaProdutos.CACHACA)
            .teorAlcoolico(42.0)
            .envelhecimento(7)
            .madeiraEnvelhecimento("Bálsamo")
            .avaliacaoGeral(4.5) 
            .avaliacaoSommelier("Aprovado")
            .origem("MG")
            .build();

        // Objeto 2: Cachaça Prata (Produto do tipo BebidaModel)
        cachaçaPrata = BebidaModel.builder()
            .nome("Cachaça Prata Nova")
            .preco(new BigDecimal("50.00"))
            .quantidadeEmEstoque(100)
            .fotosUrls(Arrays.asList("url/prata"))
            .categoria(CategoriaProdutos.CACHACA)
            .teorAlcoolico(40.0)
            .envelhecimento(0)
            .madeiraEnvelhecimento("Nenhuma")
            .avaliacaoGeral(4.0)
            .avaliacaoSommelier("Recomendado")
            .origem("ES")
            .build();
    }

    // --- Testes de CRUD Padrão (do JpaRepository) ---

    @Test
    @DisplayName("1. SALVAR: Deve persistir um Produto e retornar o ID não nulo")
    void save_DevePersistirProduto() {
        // ACT
        ProdutoModel produtoSalvo = produtoRepository.save(cachacaOuro);

        // ASSERT
        assertThat(produtoSalvo).isNotNull();
        assertThat(produtoSalvo.getId()).isNotNull(); 
    }

    @Test
    @DisplayName("2. BUSCAR POR ID: Deve retornar um produto salvo pelo seu ID")
    void findById_DeveRetornarProduto() {
        // ARRANGE
        entityManager.persist(cachacaOuro);
        entityManager.flush();

        // ACT
        Optional<ProdutoModel> encontrado = produtoRepository.findById(cachacaOuro.getId());

        // ASSERT
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Cachaça Ouro Envelhecida");
    }

    @Test
    @DisplayName("3. BUSCAR TODOS: Deve retornar a lista de todos os produtos salvos")
    void findAll_DeveRetornarTodosOsProdutos() {
        // ARRANGE
        entityManager.persist(cachacaOuro);
        entityManager.persist(cachaçaPrata);
        entityManager.flush();

        // ACT
        List<ProdutoModel> produtos = produtoRepository.findAll();

        // ASSERT
        assertThat(produtos).hasSize(2);
        assertThat(produtos).extracting(ProdutoModel::getNome)
            .containsExactlyInAnyOrder("Cachaça Ouro Envelhecida", "Cachaça Prata Nova");
    }

    @Test
    @DisplayName("4. DELETAR: Deve remover um produto do banco de dados")
    void delete_DeveRemoverProduto() {
        // ARRANGE
        entityManager.persist(cachacaOuro);
        entityManager.flush();

        // ACT
        produtoRepository.delete(cachacaOuro);
        
        // ASSERT
        Optional<ProdutoModel> encontrado = produtoRepository.findById(cachacaOuro.getId());
        assertThat(encontrado).isNotPresent();
    }


    // --- Teste de Query Customizada (findByCategoria) ---

    @Test
    @DisplayName("5. findByCategoria: Deve retornar produtos apenas da categoria CACHACA")
    void findByCategoria_DeveRetornarApenasCachacas() {
        // ARRANGE
        entityManager.persist(cachacaOuro);
        entityManager.persist(cachaçaPrata);
        
        // CORREÇÃO: Usando a categoria 'LICOR', que existe na enum, para criar o produto que será ignorado.
        BebidaModel licor = BebidaModel.builder() 
            .nome("Licor de Menta")
            .preco(new BigDecimal("45.00"))
            .quantidadeEmEstoque(15)
            .fotosUrls(Arrays.asList("url/licor"))
            .categoria(CategoriaProdutos.LICOR) // CATEGORIA VÁLIDA DIFERENTE DE CACHACA
            .teorAlcoolico(20.0) 
            .envelhecimento(1) 
            .madeiraEnvelhecimento("N/A") 
            .avaliacaoGeral(4.2)
            .avaliacaoSommelier("Doce")
            .origem("SP")
            .build(); 

        entityManager.persist(licor);

        entityManager.flush();

        // ACT
        List<ProdutoModel> cachacasEncontradas = 
            produtoRepository.findByCategoria(CategoriaProdutos.CACHACA);

        // ASSERT
        assertThat(cachacasEncontradas).isNotNull();
        assertThat(cachacasEncontradas).hasSize(2);
        // Garante que o produto "Licor de Menta" não foi incluído na lista
        assertThat(cachacasEncontradas).extracting(ProdutoModel::getNome)
            .containsExactlyInAnyOrder("Cachaça Ouro Envelhecida", "Cachaça Prata Nova");
        assertThat(cachacasEncontradas).extracting(ProdutoModel::getNome)
            .doesNotContain("Licor de Menta"); 
    }
}
