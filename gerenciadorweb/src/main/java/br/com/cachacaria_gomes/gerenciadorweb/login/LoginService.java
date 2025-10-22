package br.com.cachacaria_gomes.gerenciadorweb.login;

import br.com.cachacaria_gomes.gerenciadorweb.cliente.ClienteModel;
import br.com.cachacaria_gomes.gerenciadorweb.cliente.IClienteRepository;
import br.com.cachacaria_gomes.gerenciadorweb.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager; // NOVO: Importar EntityManager
import org.springframework.transaction.annotation.Transactional; // Manter este import

@Service
public class LoginService {
    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private EntityManager entityManager; // NOVO: Injetar o gerenciador de entidades

    // Usar Transactional para controlar a sessão
    @Transactional
    public Role autenticar(String identificador, String senha) {
        
        // * PASSO CRÍTICO: LIMPAR O CACHE DA SESSÃO *
        // Isso força a próxima busca a ir ao banco de dados, ignorando dados antigos em cache.
        entityManager.clear(); 
        
        ClienteModel cliente = null;

        // Tenta buscar por email
        if (identificador.contains("@")) {
            cliente = clienteRepository.findByEmail(identificador);
        }

        // Se não encontrou por email, tenta buscar por usuario
        if (cliente == null) {
            cliente = clienteRepository.findByUsuario(identificador);
        }

        if (cliente != null) {
            // Agora, o cliente deve ser o objeto mais ATUALIZADO do banco.
            BCrypt.Result result = BCrypt.verifyer().verify(senha.toCharArray(), cliente.getSenha());

            if (result.verified) {
                // VERIFICAR AQUI ANTES DE RETORNAR (DEBUG)
                // System.out.println("Role lida do BD: " + cliente.getRole()); 
                
                return cliente.getRole();
            }
        }

        return null; // Falha na autenticação
    }
}