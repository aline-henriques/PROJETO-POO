package br.com.cachacaria_gomes.gerenciadorweb.login;

import br.com.cachacaria_gomes.gerenciadorweb.cliente.ClienteModel;
import br.com.cachacaria_gomes.gerenciadorweb.cliente.IClienteRepository;
import br.com.cachacaria_gomes.gerenciadorweb.enums.Role;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para a lógica de negócio do LoginService, isolando dependências.
 */
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    // Onde a classe real será instanciada e os Mocks serão injetados
    @InjectMocks
    private LoginService loginService;

    // Mock para simular a interação com o banco de dados
    @Mock
    private IClienteRepository clienteRepository;

    // Mock para simular o controle do cache da sessão (entityManager.clear())
    @Mock
    private EntityManager entityManager;

    // Dados de teste
    private final String SENHA_SECRETA = "senhaSegura123";
    // É fundamental usar um hash REAL para que o BCrypt.verifyer().verify() funcione
    private final String SENHA_HASHED = BCrypt.withDefaults().hashToString(12, SENHA_SECRETA.toCharArray());
    private ClienteModel clienteAdmin;
    private ClienteModel clientePadrao;

    @BeforeEach
    void setUp() {
        // Configuração de um Cliente ADMIN
        clienteAdmin = new ClienteModel(
                "12345678901", "Admin Teste", "adminuser", "Rua A", "100", 
                "Bairro X", "Cidade Y", "Estado Z", "12345678", LocalDate.of(1990, 1, 1), 
                "admin@empresa.com", SENHA_HASHED, LocalDateTime.now(), Role.ADMIN
        );
        
        // Configuração de um Cliente Padrão
        clientePadrao = new ClienteModel(
                "11122233344", "Cliente Teste", "clientuser", "Rua B", "200", 
                "Bairro W", "Cidade K", "Estado P", "87654321", LocalDate.of(2000, 10, 10), 
                "cliente@email.com", SENHA_HASHED, LocalDateTime.now(), Role.CLIENTE
        );
        
        // Assegura que o clear() do EntityManager não faz nada (não precisamos de lógica real)
        doNothing().when(entityManager).clear();
    }

    // --- Testes de Sucesso ---
    
    @Test
    @DisplayName("1. Sucesso: Autenticar por e-mail com senha válida deve retornar ROLE correta")
    void autenticar_ComEmailValidoESenhaCorreta_RetornaRole() {
        // ARRANGE
        // A busca por e-mail deve ter sucesso
        when(clienteRepository.findByEmail(clienteAdmin.getEmail())).thenReturn(clienteAdmin);
        
        // ACT
        Role roleRetornada = loginService.autenticar(clienteAdmin.getEmail(), SENHA_SECRETA);
        
        // ASSERT
        assertEquals(Role.ADMIN, roleRetornada, "Deve retornar a Role correta após autenticação bem-sucedida por e-mail.");
        
        // Verificação de chamadas
        verify(entityManager, times(1)).clear();
        // A busca por email deve ser chamada, e a de usuário não
        verify(clienteRepository, times(1)).findByEmail(clienteAdmin.getEmail());
        verify(clienteRepository, never()).findByUsuario(anyString()); 
    }

    @Test
    @DisplayName("2. Sucesso: Autenticar por nome de usuário com senha válida deve retornar ROLE correta")
    void autenticar_ComUsuarioValidoESenhaCorreta_RetornaRole() {
        // ARRANGE
        // A busca por email não será chamada (por causa do if no Service), mas configuramos para o caso de ter sido.
        // O mais importante é que a busca por usuário tenha sucesso:
        when(clienteRepository.findByUsuario(clientePadrao.getUsuario())).thenReturn(clientePadrao);

        // ACT
        Role roleRetornada = loginService.autenticar(clientePadrao.getUsuario(), SENHA_SECRETA);

        // ASSERT
        assertEquals(Role.CLIENTE, roleRetornada, "Deve retornar a Role de CLIENTE no sucesso.");

        // Verificação de chamadas (CORRIGIDO)
        verify(entityManager, times(1)).clear();
        
        // O Service NÃO chama findByEmail se não tiver '@'
        verify(clienteRepository, never()).findByEmail(anyString()); 
        
        // O Service DEVE chamar findByUsuario
        verify(clienteRepository, times(1)).findByUsuario(clientePadrao.getUsuario()); 
    }
    
    // --- Testes de Falha ---

    @Test
    @DisplayName("3. Falha: Deve retornar null para senha inválida")
    void autenticar_ComSenhaInvalida_RetornaNull() {
        // ARRANGE
        when(clienteRepository.findByEmail(clientePadrao.getEmail())).thenReturn(clientePadrao); // Encontra o cliente
        String senhaInvalida = "SENHA_ERRADA_123";

        // ACT
        Role roleRetornada = loginService.autenticar(clientePadrao.getEmail(), senhaInvalida);

        // ASSERT
        assertNull(roleRetornada, "Deve retornar null quando a senha estiver incorreta.");
        
        // Verificação de chamadas
        verify(entityManager, times(1)).clear();
        verify(clienteRepository, times(1)).findByEmail(clientePadrao.getEmail());
        verify(clienteRepository, never()).findByUsuario(anyString());
    }

    @Test
    @DisplayName("4. Falha: Deve retornar null para identificador (e-mail/usuário) inexistente")
    void autenticar_ComIdentificadorInexistente_RetornaNull() {
        // ARRANGE
        String identificadorInexistente = "naoexiste@email.com";
        // Simula a falha nas duas buscas
        when(clienteRepository.findByEmail(identificadorInexistente)).thenReturn(null);
        when(clienteRepository.findByUsuario(identificadorInexistente)).thenReturn(null);

        // ACT
        Role roleRetornada = loginService.autenticar(identificadorInexistente, SENHA_SECRETA);

        // ASSERT
        assertNull(roleRetornada, "Deve retornar null quando o identificador não for encontrado.");

        // Verificação de chamadas
        verify(entityManager, times(1)).clear();
        verify(clienteRepository, times(1)).findByEmail(identificadorInexistente);
        // Garante que tentou buscar por usuário após a falha do e-mail
        verify(clienteRepository, times(1)).findByUsuario(identificadorInexistente); 
    }
}