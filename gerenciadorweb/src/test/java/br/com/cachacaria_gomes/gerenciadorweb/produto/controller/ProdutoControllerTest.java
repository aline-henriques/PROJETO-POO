package br.com.cachacaria_gomes.gerenciadorweb.produto.controller;

import br.com.cachacaria_gomes.gerenciadorweb.produto.model.BebidaModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.CategoriaProdutos;
import br.com.cachacaria_gomes.gerenciadorweb.produto.model.ProdutoModel;
import br.com.cachacaria_gomes.gerenciadorweb.produto.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de Integração da Camada Web (Controller) para ProdutoController.
 * Simula requisições HTTP para verificar o mapeamento e o tratamento de JSON/Status HTTP.
 */
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula requisições HTTP

    @Autowired
    private ObjectMapper objectMapper; // Converte objetos Java/JSON

    @MockBean
    private ProdutoService produtoService; // Isola a lógica de negócio do Controller

    private BebidaModel produtoCachaca;
    private final UUID ID_VALIDO = UUID.randomUUID();
    
    // Rota base com a barra final, conforme o seu Controller
    private final String URL_BASE_COM_BARRA = "/produtos/"; 
    private final String URL_BASE_SEM_BARRA = "/produtos"; 


    @BeforeEach
    void setUp() {
        // Objeto BebidaModel (usando Builder)
        produtoCachaca = BebidaModel.builder()
            .id(ID_VALIDO)
            .nome("Cachaça Teste")
            .preco(new BigDecimal("50.00"))
            .quantidadeEmEstoque(10)
            .fotosUrls(Arrays.asList("url1"))
            .categoria(CategoriaProdutos.CACHACA)
            .avaliacaoGeral(4.5)
            .origem("Minas Gerais")
            .teorAlcoolico(40.0)
            .envelhecimento(5)
            .madeiraEnvelhecimento("Carvalho")
            .avaliacaoSommelier("Excelente")
            .build();
    }

    // --- Testes de POST (Criação) ---

    @Test
    @DisplayName("1. POST /produtos/: Deve criar um produto e retornar status 201")
    void postProduto_DeveRetornarStatus201_Criado() throws Exception {
        // ARRANGE
        // Mockando o Service para retornar o objeto salvo
        when(produtoService.salvarProduto(any(ProdutoModel.class))).thenReturn(produtoCachaca);

        String jsonCorpo = objectMapper.writeValueAsString(produtoCachaca);
        
        // ACT & ASSERT: Usando URL_BASE_COM_BARRA para corresponder ao @PostMapping("/")
        mockMvc.perform(post(URL_BASE_COM_BARRA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCorpo))
            .andExpect(status().isCreated()) // Espera Status 201
            .andExpect(jsonPath("$.nome", is("Cachaça Teste")))
            .andExpect(jsonPath("$.id", is(ID_VALIDO.toString())));

        verify(produtoService, times(1)).salvarProduto(any(ProdutoModel.class));
    }
    
    // --- Testes de GET (Listagem e Busca) ---

    @Test
    @DisplayName("2. GET /produtos/: Deve retornar uma lista de produtos e status 200")
    void getProdutos_DeveRetornarListaEStatus200() throws Exception {
        // ARRANGE
        List<ProdutoModel> lista = Arrays.asList(produtoCachaca);
        when(produtoService.listarTodos()).thenReturn(lista);

        // ACT & ASSERT: Usando URL_BASE_COM_BARRA para corresponder ao @GetMapping("/")
        mockMvc.perform(get(URL_BASE_COM_BARRA) 
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Espera Status 200 OK
            .andExpect(jsonPath("$.length()", is(1)))
            .andExpect(jsonPath("$[0].nome", is("Cachaça Teste")));

        verify(produtoService, times(1)).listarTodos();
    }
    
    @Test
    @DisplayName("3. GET /produtos/{id}: Deve retornar um produto por ID e status 200")
    void getProdutoPorId_DeveRetornarProdutoEStatus200() throws Exception {
        // ARRANGE
        when(produtoService.buscarPorId(ID_VALIDO)).thenReturn(produtoCachaca);

        // ACT & ASSERT
        mockMvc.perform(get(URL_BASE_SEM_BARRA + "/{id}", ID_VALIDO) // Rota correta: /produtos/{id}
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) 
            .andExpect(jsonPath("$.id", is(ID_VALIDO.toString())));

        verify(produtoService, times(1)).buscarPorId(ID_VALIDO);
    }
    
    @Test
    @DisplayName("4. GET /produtos/{id}: Deve retornar status 404 quando produto não encontrado")
    void getProdutoPorId_DeveRetornarStatus404_NaoEncontrado() throws Exception {
        // ARRANGE: Mockando o service para retornar nulo, que no Controller resulta em 404
        when(produtoService.buscarPorId(any(UUID.class))).thenReturn(null);

        // ACT & ASSERT
        mockMvc.perform(get(URL_BASE_SEM_BARRA + "/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()); // Espera Status 404
        
        verify(produtoService, times(1)).buscarPorId(any(UUID.class));
    }

    // --- Testes de PUT (Atualização) ---

    @Test
    @DisplayName("5. PUT /produtos/{id}: Deve atualizar o produto e retornar status 200")
    void putProduto_DeveAtualizarEStatus200() throws Exception {
        // ARRANGE: Simula produto existente e mocka o retorno da atualização
        when(produtoService.buscarPorId(ID_VALIDO)).thenReturn(produtoCachaca);
        
        BebidaModel produtoAtualizado = produtoCachaca.toBuilder().nome("Cachaça Nova").build();
        when(produtoService.salvarProduto(any(ProdutoModel.class))).thenReturn(produtoAtualizado);

        String jsonCorpo = objectMapper.writeValueAsString(produtoAtualizado);
        
        // ACT & ASSERT
        mockMvc.perform(put(URL_BASE_SEM_BARRA + "/{id}", ID_VALIDO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCorpo))
            .andExpect(status().isOk()) 
            .andExpect(jsonPath("$.nome", is("Cachaça Nova")));

        verify(produtoService, times(1)).buscarPorId(ID_VALIDO);
        verify(produtoService, times(1)).salvarProduto(any(ProdutoModel.class));
    }

    // --- Testes de DELETE ---

    @Test
    @DisplayName("6. DELETE /produtos/{id}: Deve deletar o produto e retornar status 204")
    void deleteProduto_DeveRetornarStatus204_SemConteudo() throws Exception {
        // ARRANGE: Simula que o produto existe para passar a verificação de 404 no Controller
        when(produtoService.buscarPorId(ID_VALIDO)).thenReturn(produtoCachaca);
        doNothing().when(produtoService).deletarProduto(ID_VALIDO);

        // ACT & ASSERT
        mockMvc.perform(delete(URL_BASE_SEM_BARRA + "/{id}", ID_VALIDO)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()); // Espera Status 204

        verify(produtoService, times(1)).buscarPorId(ID_VALIDO);
        verify(produtoService, times(1)).deletarProduto(ID_VALIDO);
    }
}
