package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles; // Importação necessária

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


// 1. Diz ao Spring para carregar a fatia JPA
@DataJpaTest 
// 2. Ativa o profile 'test' (que carrega o application-test.properties)
@ActiveProfiles("test") 
// 3. Essencial: Desativa o banco de dados em memória (H2) e força o uso do DataSource configurado
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class IClienteRepositoryTest {

    @Autowired
    private IClienteRepository clienteRepository;

    private ClienteModel clienteDeTeste;

    @BeforeEach
    void setUp() {
        // Garantimos o isolamento: limpa a tabela no DB de TESTE
        clienteRepository.deleteAll();

        // Prepara o objeto a ser persistido (mantido igual)
        clienteDeTeste = new ClienteModel();
        clienteDeTeste.setCpf("00011122233");
        clienteDeTeste.setNomeCompleto("Cliente Teste");
        clienteDeTeste.setUsuario("usuario_repo_teste");
        clienteDeTeste.setEmail("repo@email.com");
        clienteDeTeste.setSenha("SenhaForte123"); 
        clienteDeTeste.setDataNascimento(LocalDate.now().minusYears(30)); 
        
        clienteDeTeste.setRua("Rua Teste");
        clienteDeTeste.setNumero("10");
        clienteDeTeste.setBairro("Bairro Teste");
        clienteDeTeste.setCidade("Cidade Teste");
        clienteDeTeste.setEstado("TS"); 
        clienteDeTeste.setCep("99999999");

        clienteRepository.save(clienteDeTeste);
    }

    // --- TESTES DE BUSCA CUSTOMIZADA ---

    @Test
    @DisplayName("Deve encontrar o cliente pelo nome de usuário (findByUsuario)")
    void shouldFindClientByUsuario() {
        ClienteModel foundCliente = clienteRepository.findByUsuario("usuario_repo_teste");
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getCpf()).isEqualTo("00011122233");
    }

    @Test
    @DisplayName("Deve retornar null se o nome de usuário não for encontrado")
    void shouldReturnNullWhenUsuarioNotFound() {
        ClienteModel foundCliente = clienteRepository.findByUsuario("usuario_inexistente");
        assertThat(foundCliente).isNull();
    }

    @Test
    @DisplayName("Deve encontrar o cliente pelo CPF (findByCpf)")
    void shouldFindClientByCpf() {
        ClienteModel foundCliente = clienteRepository.findByCpf("00011122233");
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getUsuario()).isEqualTo(clienteDeTeste.getUsuario());
    }
    
    @Test
    @DisplayName("Deve retornar null se o CPF não for encontrado")
    void shouldReturnNullWhenCpfNotFound() {
        ClienteModel foundCliente = clienteRepository.findByCpf("99999999999");
        assertThat(foundCliente).isNull();
    }
}