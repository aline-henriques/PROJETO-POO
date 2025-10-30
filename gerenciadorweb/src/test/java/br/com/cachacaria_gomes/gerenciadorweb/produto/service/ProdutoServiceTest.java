package br.com.cachacaria_gomes.gerenciadorweb.produto.service;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ArtesanatoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.BebidaModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.repository.IProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes Unitários para a camada de Serviço (ProdutoService).
 * Usa Mockito para isolar a dependência do Repositório.
 */
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService; 

    @Mock
    private IProdutoRepository produtoRepository; 

    private ProdutoModel produtoCachaca;
    private ProdutoModel produtoArtesanato; // NOVO: Campo para o Artesanato
    
    private final UUID ID_VALIDO_BEBIDA = UUID.randomUUID();
    private final UUID ID_VALIDO_ARTESANATO = UUID.randomUUID(); // NOVO: ID para Artesanato
    private final UUID ID_INEXISTENTE = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // 1. Configuração do BEBIDAMODEL (usando o Builder)
        produtoCachaca = BebidaModel.builder()
            .id(ID_VALIDO_BEBIDA)
            .nome("Cachaça Ouro")
            .preco(new BigDecimal("50.00"))
            .quantidadeEmEstoque(10)
            .fotosUrls(Arrays.asList("url1", "url2"))
            .categoria(CategoriaProdutos.CACHACA)
            .avaliacaoGeral(4.5)
            .origem("Minas Gerais")
            .teorAlcoolico(40.0)
            .envelhecimento(5)
            .madeiraEnvelhecimento("Carvalho")
            .avaliacaoSommelier("Excelente")
            .build();
            
        // 2. Configuração do ARTESANATOMODEL (usando o Builder)
        produtoArtesanato = ArtesanatoModel.builder()
            .id(ID_VALIDO_ARTESANATO)
            .nome("Vaso de Cerâmica")
            .preco(new BigDecimal("120.00"))
            .quantidadeEmEstoque(5)
            .fotosUrls(Arrays.asList("url/vaso.jpg"))
            .categoria(CategoriaProdutos.CERAMICA)
            .avaliacaoGeral(5.0)
            .tipoArtesanato("Escultura")
            .materialPrincipal("Barro")
            .dimensoes("30x20x20")
            .pesoKg(new BigDecimal("2.5"))
            .avaliacaoArtesao("Obra Prima")
            .build();
    }

    // --- Testes para listarTodos() ---
    
    @Test
    @DisplayName("1. listarTodos: Deve retornar uma lista com Bebida e Artesanato")
    void listarTodos_DeveRetornarListaDeProdutos() {
        // ARRANGE
        // Retornando ambos os produtos
        List<ProdutoModel> listaEsperada = Arrays.asList(produtoCachaca, produtoArtesanato);
        when(produtoRepository.findAll()).thenReturn(listaEsperada);

        // ACT
        List<ProdutoModel> listaRetornada = produtoService.listarTodos();

        // ASSERT
        assertNotNull(listaRetornada);
        assertEquals(2, listaRetornada.size()); // Verifica que ambos estão presentes
        
        verify(produtoRepository, times(1)).findAll(); 
    }

    // ... (listarTodos_DeveRetornarListaVazia permanece inalterado)

    // --- Testes para buscarPorId() ---

    @Test
    @DisplayName("3.1 buscarPorId: Deve retornar a Bebida quando o ID for encontrado")
    void buscarPorId_DeveRetornarBebida_QuandoIDExiste() {
        // ARRANGE
        when(produtoRepository.findById(ID_VALIDO_BEBIDA)).thenReturn(Optional.of(produtoCachaca));

        // ACT
        ProdutoModel produtoEncontrado = produtoService.buscarPorId(ID_VALIDO_BEBIDA);

        // ASSERT
        assertNotNull(produtoEncontrado);
        assertEquals(ID_VALIDO_BEBIDA, produtoEncontrado.getId());
        // Garante que é uma Bebida
        assertTrue(produtoEncontrado instanceof BebidaModel);
        verify(produtoRepository, times(1)).findById(ID_VALIDO_BEBIDA);
    }
    
    @Test
    @DisplayName("3.2 buscarPorId: Deve retornar o Artesanato quando o ID for encontrado")
    void buscarPorId_DeveRetornarArtesanato_QuandoIDExiste() {
        // ARRANGE
        when(produtoRepository.findById(ID_VALIDO_ARTESANATO)).thenReturn(Optional.of(produtoArtesanato));

        // ACT
        ProdutoModel produtoEncontrado = produtoService.buscarPorId(ID_VALIDO_ARTESANATO);

        // ASSERT
        assertNotNull(produtoEncontrado);
        assertEquals(ID_VALIDO_ARTESANATO, produtoEncontrado.getId());
        // Garante que é um Artesanato
        assertTrue(produtoEncontrado instanceof ArtesanatoModel);
        verify(produtoRepository, times(1)).findById(ID_VALIDO_ARTESANATO);
    }


    // ... (buscarPorId_DeveLancarExcecao_QuandoIDNaoExiste permanece inalterado)

    // --- Testes para buscarPorCategoria() ---
    
    @Test
    @DisplayName("5.1 buscarPorCategoria: Deve retornar a Bebida da categoria CACHACA")
    void buscarPorCategoria_DeveRetornarBebida_QuandoCategoriaCorreta() {
        // ARRANGE
        List<ProdutoModel> listaEsperada = Arrays.asList(produtoCachaca);
        CategoriaProdutos categoria = CategoriaProdutos.CACHACA;
        when(produtoRepository.findByCategoria(categoria)).thenReturn(listaEsperada);

        // ACT
        List<ProdutoModel> listaRetornada = produtoService.buscarPorCategoria(categoria);

        // ASSERT
        assertFalse(listaRetornada.isEmpty());
        assertEquals(1, listaRetornada.size());
        assertEquals(categoria, listaRetornada.get(0).getCategoria());
        verify(produtoRepository, times(1)).findByCategoria(categoria);
    }

    @Test
    @DisplayName("5.2 buscarPorCategoria: Deve retornar o Artesanato da categoria CERAMICA")
    void buscarPorCategoria_DeveRetornarArtesanato_QuandoCategoriaCorreta() {
        // ARRANGE
        List<ProdutoModel> listaEsperada = Arrays.asList(produtoArtesanato);
        CategoriaProdutos categoria = CategoriaProdutos.CERAMICA;
        when(produtoRepository.findByCategoria(categoria)).thenReturn(listaEsperada);

        // ACT
        List<ProdutoModel> listaRetornada = produtoService.buscarPorCategoria(categoria);

        // ASSERT
        assertFalse(listaRetornada.isEmpty());
        assertEquals(1, listaRetornada.size());
        assertEquals(categoria, listaRetornada.get(0).getCategoria());
        verify(produtoRepository, times(1)).findByCategoria(categoria);
    }

    // ... (O restante dos métodos permanece inalterado: salvarProduto e deletarProduto)
    
    // O restante dos métodos (listarTodos_DeveRetornarListaVazia, buscarPorId_DeveLancarExcecao, etc.)
    // pode permanecer inalterado se já estiverem no seu arquivo.
    
    // Código para listarTodos_DeveRetornarListaVazia
    @Test
    @DisplayName("2. listarTodos: Deve retornar uma lista vazia quando não houver produtos")
    void listarTodos_DeveRetornarListaVazia() {
        // ARRANGE
        when(produtoRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT
        List<ProdutoModel> listaRetornada = produtoService.listarTodos();

        // ASSERT
        assertTrue(listaRetornada.isEmpty());
        verify(produtoRepository, times(1)).findAll();
    }
    
    // Código para buscarPorId_DeveLancarExcecao_QuandoIDNaoExiste
    @Test
    @DisplayName("4. buscarPorId: Deve lançar RuntimeException quando o ID não for encontrado")
    void buscarPorId_DeveLancarExcecao_QuandoIDNaoExiste() {
        // ARRANGE
        when(produtoRepository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(ID_INEXISTENTE);
        });

        assertEquals("Produto não encontrado", excecao.getMessage());
        verify(produtoRepository, times(1)).findById(ID_INEXISTENTE);
    }
    
    // Código para buscarPorCategoria_DeveLancarExcecao_QuandoCategoriaForNula
    @Test
    @DisplayName("6. buscarPorCategoria: Deve lançar IllegalArgumentException para categoria nula")
    void buscarPorCategoria_DeveLancarExcecao_QuandoCategoriaForNula() {
        // ACT & ASSERT
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.buscarPorCategoria(null);
        });

        assertEquals("A categoria de produto não pode ser nula.", excecao.getMessage());
        verify(produtoRepository, never()).findByCategoria(any()); 
    }
    
    // Código para salvarProduto_DeveRetornarProdutoSalvo
    @Test
    @DisplayName("7. salvarProduto: Deve chamar o save do repositório e retornar o produto salvo")
    void salvarProduto_DeveRetornarProdutoSalvo() {
        // ARRANGE
        when(produtoRepository.save(produtoCachaca)).thenReturn(produtoCachaca);

        // ACT
        ProdutoModel produtoSalvo = produtoService.salvarProduto(produtoCachaca);

        // ASSERT
        assertNotNull(produtoSalvo);
        assertEquals(produtoCachaca.getNome(), produtoSalvo.getNome());
        verify(produtoRepository, times(1)).save(produtoCachaca);
    }
    
    // Código para deletarProduto_DeveChamarDeleteById
    @Test
    @DisplayName("8. deletarProduto: Deve chamar o deleteById do repositório com o ID correto")
    void deletarProduto_DeveChamarDeleteById() {
        // ARRANGE
        doNothing().when(produtoRepository).deleteById(ID_VALIDO_BEBIDA);

        // ACT
        produtoService.deletarProduto(ID_VALIDO_BEBIDA);

        // ASSERT
        verify(produtoRepository, times(1)).deleteById(ID_VALIDO_BEBIDA);
    }
}