package br.com.cachacaria_gomes.gerenciadorweb.login;

import br.com.cachacaria_gomes.gerenciadorweb.cliente.LoginRequest;
import br.com.cachacaria_gomes.gerenciadorweb.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste de integração da camada MVC (Controller).
 * Usa @WebMvcTest para focar no Controller, isolando a camada de Service via MockBean.
 */
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc; // Usado para simular as requisições HTTP

    @MockBean
    private LoginService loginService; // O Service é mockado para isolar o teste do Controller

    @Autowired
    private ObjectMapper objectMapper; // Usado para converter objetos Java em String JSON

    private final String LOGIN_URL = "/login";
    private final String IDENTIFICADOR_VALIDO = "teste@email.com";
    private final String SENHA_VALIDA = "senha123";

    @Test
    @DisplayName("1. Deve retornar status 200 OK e a ROLE no login bem-sucedido")
    void login_SucessoComCredenciaisValidas_Retorna200EBodyCorreto() throws Exception {
        // ARRANGE
        LoginRequest request = new LoginRequest();
        request.setEmail(IDENTIFICADOR_VALIDO);
        request.setSenha(SENHA_VALIDA);

        Role roleEsperada = Role.CLIENTE;

        // Simula o comportamento do Service: quando autenticar é chamado, retorna a ROLE
        when(loginService.autenticar(IDENTIFICADOR_VALIDO, SENHA_VALIDA)).thenReturn(roleEsperada);

        // ACT & ASSERT
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        // Converte o objeto LoginRequest em JSON para o corpo da requisição
                        .content(objectMapper.writeValueAsString(request))) 
                .andExpect(status().isOk()) // Espera status HTTP 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.role").value(roleEsperada.name())) // Verifica se a ROLE está no JSON
                .andExpect(jsonPath("$.message").value("Login bem-sucedido.")); // Verifica a mensagem de sucesso

        // Verifica se o método 'autenticar' do service foi chamado exatamente uma vez com os parâmetros corretos
        verify(loginService, times(1)).autenticar(IDENTIFICADOR_VALIDO, SENHA_VALIDA);
    }

    @Test
    @DisplayName("2. Deve retornar status 401 UNAUTHORIZED no login com credenciais inválidas")
    void login_FalhaAutenticacao_Retorna401() throws Exception {
        // ARRANGE
        String identificadorInvalido = "invalido@email.com";
        String senhaInvalida = "senhaerrada";
        LoginRequest request = new LoginRequest();
        request.setEmail(identificadorInvalido);
        request.setSenha(senhaInvalida);

        // Simula o Service retornando null (falha na autenticação)
        when(loginService.autenticar(identificadorInvalido, senhaInvalida)).thenReturn(null);

        // ACT & ASSERT
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized()) // Espera status HTTP 401
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("E-mail/Usuário ou senha inválidos.")); // Verifica a mensagem de erro

        verify(loginService, times(1)).autenticar(identificadorInvalido, senhaInvalida);
    }
}