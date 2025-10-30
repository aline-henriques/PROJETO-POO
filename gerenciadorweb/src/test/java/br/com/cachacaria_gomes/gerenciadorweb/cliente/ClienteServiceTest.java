package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    // Injeta o mock do repositório no serviço que queremos testar
    @InjectMocks
    private ClienteService clienteService;

    // Cria uma instância mock do repositório para simular o DB
    @Mock
    private IClienteRepository clienteRepository;

    // Objeto base para testes
    private ClienteModel clienteValido;
    private final LocalDate DATA_NASCIMENTO_VALIDA = LocalDate.now().minusYears(20);

    @BeforeEach
    void setup() {
        // Inicializa um ClienteModel válido usando setters
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
        clienteValido.setDataNascimento(DATA_NASCIMENTO_VALIDA); 
        clienteValido.setEmail("joao@email.com");
        clienteValido.setSenha("SenhaSegura123"); 
    }

    // --- CENÁRIOS DE SUCESSO ---

    @Test
    @DisplayName("Deve criar o cliente com sucesso quando os dados são válidos")
    void shouldCreateUserSuccessfullyWhenDataIsValid() throws Exception {
        // Cenário: Nenhum usuário/CPF encontrado (DB vazio) - Ambos devem ser mockados!
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        
        // Simula o salvamento e retorno do objeto (com a senha Criptografada)
        when(clienteRepository.save(any(ClienteModel.class))).thenAnswer(i -> i.getArguments()[0]);

        // Ação
        ClienteModel clienteCriado = clienteService.createUser(clienteValido);

        // Verificação
        assertNotNull(clienteCriado, "O objeto criado não deve ser nulo");
        assertNotEquals("SenhaSegura123", clienteCriado.getSenha(), "A senha deve ser criptografada (hash)");
        verify(clienteRepository, times(1)).save(any(ClienteModel.class));
    }

    // --- CENÁRIOS DE ERRO (REGRAS DE NEGÓCIO) ---

    @Test
    @DisplayName("Deve lançar exceção (Usuário já existe) quando o username é duplicado")
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Apenas o primeiro mock (findByUsuario) é necessário, pois o código para aqui.
        when(clienteRepository.findByUsuario(clienteValido.getUsuario())).thenReturn(clienteValido);
        
        // REMOVIDO: o mock para findByCpf, que causava a exceção UnnecessaryStubbingException

        // Ação e Verificação
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });

        assertEquals("Usuário já existe!", exception.getMessage());
        verify(clienteRepository, never()).save(any(ClienteModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção (CPF já cadastrado) quando o CPF é duplicado")
    void shouldThrowExceptionWhenCpfIsAlreadyRegistered() {
        // Cenário: findByUsuario deve retornar null (primeira verificação OK)
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        
        // Apenas o segundo mock (findByCpf) é necessário para a falha.
        when(clienteRepository.findByCpf(clienteValido.getCpf())).thenReturn(clienteValido);
        
        // Ação e Verificação
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });

        assertEquals("CPF já cadastrado", exception.getMessage());
        verify(clienteRepository, never()).save(any(ClienteModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção (Idade mínima 18 anos) quando o cliente é menor de idade")
    void shouldThrowExceptionWhenClientIsUnderage() {
        // Prepara: Define a data de nascimento para um menor de idade (15 anos)
        clienteValido.setDataNascimento(LocalDate.now().minusYears(15)); 
        
        // Cenário (Validações de unicidade passam) - Ambos mockados como null
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);

        // Ação e Verificação
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });

        assertEquals("Idade mínima 18 anos", exception.getMessage());
        verify(clienteRepository, never()).save(any(ClienteModel.class));
    }

    // --- TESTES DE VALIDAÇÃO DE SENHA ---
    
    // (Os testes de validação de senha não precisavam de correção, pois a busca por usuário/CPF
    // sempre passava, e o Mockito não considerava o save() como desnecessário)

    @Test
    @DisplayName("Deve lançar exceção quando a senha tem menos de 8 caracteres")
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        clienteValido.setSenha("Short"); 
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });
        assertEquals("A senha deve ter no mínimo 8 caracteres.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando a senha não contiver números")
    void shouldThrowExceptionWhenPasswordHasNoNumbers() {
        clienteValido.setSenha("apenasletrasvalida"); 
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });
        assertEquals("A senha deve conter letras e números.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha não contiver letras")
    void shouldThrowExceptionWhenPasswordHasNoLetters() {
        clienteValido.setSenha("123456789012"); 
        when(clienteRepository.findByUsuario(anyString())).thenReturn(null);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.createUser(clienteValido);
        });
        assertEquals("A senha deve conter letras e números.", exception.getMessage());
    }
}