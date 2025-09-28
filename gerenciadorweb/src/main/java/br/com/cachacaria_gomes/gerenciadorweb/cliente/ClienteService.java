package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository userRepository;

    @Override
    public ClienteModel createUser(ClienteModel clienteModel) throws Exception {
        
        var user = this.userRepository.findByUsuario(clienteModel.getUsuario());
        if (user != null) {
            throw new Exception("Usuário já existe!"); 
        }

        var passwordHashred = BCrypt.withDefaults()
            .hashToString(12, clienteModel.getSenha().toCharArray());
        clienteModel.setSenha(passwordHashred);
        
        return this.userRepository.save(clienteModel);
    }
}