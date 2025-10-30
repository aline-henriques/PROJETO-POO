package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

// Importação necessária para usar containsString
import static org.hamcrest.Matchers.containsString; 

// Importação da Role para o ClienteModel (Ajuste o pacote se for diferente)
import br.com.cachacaria_gomes.gerenciadorweb.enums.Role; 

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClienteController.class) // Teste focado apenas no Controller
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Ferramenta para simular requisições HTTP

    @Autowired
    private ObjectMapper objectMapper; // Usado para converter objetos Java para JSON

    // Mockamos o Service para que o Controller não execute a lógica real.
    @MockBean
    private IClienteService clienteService;

    private ClienteModel clienteValido;
    private final String ENDPOINT = "/clientes/";

    @BeforeEach
    void setup() {
        // Inicializa um ClienteModel totalmente válido para o cenário de sucesso
        clienteValido = new ClienteModel();
        clienteValido.setCpf("11122233344");
        clienteValido.setNomeCompleto("João da Silva");
        clienteValido.setUsuario("joaosilva");
        clienteValido.setRua("Rua A");
        clienteValido.setNumero("100");
        clienteValido.setBairro("Bairro X");
        clienteValido.setCidade("Cidade Y");
        clienteValido.setEstado("Estado Z");
        clienteValido.setCep("12345678");
        clienteValido.setDataNascimento(LocalDate.now().minusYears(20));
        clienteValido.setEmail("joao@email.com");
        clienteValido.setSenha("SenhaSegura123");
        clienteValido.setRole(Role.CLIENTE); 
    }

    // --- CENÁRIOS DE SUCESSO (Status 201) ---
    
    @Test
    @DisplayName("POST /clientes/ deve retornar 201 CREATED ao criar cliente com sucesso")
    void shouldReturn201CreatedWhenClientIsSuccessfullyCreated() throws Exception {
        // Mock: Retorna o objeto criado
        when(clienteService.createUser(any(ClienteModel.class))).thenReturn(clienteValido);

        // Ação: Simula a requisição POST
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteValido))) 
                
                // Verificação: Espera o status 201 Created
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(clienteValido), false));
    }


    // --- CENÁRIOS DE ERRO (Status 409 Conflict) ---
    
    @Test
    @DisplayName("POST /clientes/ deve retornar 409 CONFLICT se o Usuário já existir")
    void shouldReturn409ConflictWhenUserAlreadyExists() throws Exception {
        // Mock: Service lança a exceção que o Controller mapeia para 409
        when(clienteService.createUser(any(ClienteModel.class)))
                .thenThrow(new Exception("Usuário já existe!"));

        // Ação e Verificação
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteValido)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Usuário já existe!"));
    }

    @Test
    @DisplayName("POST /clientes/ deve retornar 409 CONFLICT se o CPF já estiver cadastrado")
    void shouldReturn409ConflictWhenCpfAlreadyRegistered() throws Exception {
        // Mock: Service lança a exceção que o Controller mapeia para 409
        when(clienteService.createUser(any(ClienteModel.class)))
                .thenThrow(new Exception("CPF já cadastrado"));

        // Ação e Verificação
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteValido)))
                .andExpect(status().isConflict())
                .andExpect(content().string("CPF já cadastrado"));
    }

    // --- CENÁRIOS DE ERRO (Status 400 Bad Request) ---
    
    @Test
    @DisplayName("POST /clientes/ deve retornar 400 BAD REQUEST se o CPF for inválido (@Size)")
    void shouldReturn400BadRequestWhenCpfIsInvalidBySize() throws Exception {
        
        // CUIDADO: Começamos com o cliente válido e alteramos APENAS o campo que queremos falhar.
        // Isso evita que outros campos @NotNull ou @Size falhem junto.
        ClienteModel clienteInvalido = clienteValido; 
        clienteInvalido.setCpf("123"); // CPF muito curto (@Size)

        // Ação e Verificação
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteInvalido)))
                .andExpect(status().isBadRequest())
                // CORREÇÃO: Usamos 'containsString' porque o Spring geralmente retorna 
                // um JSON de erro mais completo, e não apenas a mensagem literal.
                .andExpect(content().string(containsString("O CPF deve ter 11 dígitos (apenas números)")));
    }
    
    @Test
    @DisplayName("POST /clientes/ deve retornar 400 BAD REQUEST para outros erros de lógica (ex: menor de idade)")
    void shouldReturn400BadRequestForOtherLogicErrors() throws Exception {
        // Mock: Service lança uma exceção de negócio (que não é 409)
        when(clienteService.createUser(any(ClienteModel.class)))
                .thenThrow(new Exception("Idade mínima 18 anos"));

        // Ação e Verificação
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteValido)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Idade mínima 18 anos"));
    }
}