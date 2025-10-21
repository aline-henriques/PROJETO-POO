package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository userRepository;

    @Override
    public ClienteModel createUser(ClienteModel clienteModel) throws Exception {
        
        var userByUsername = this.userRepository.findByUsuario(clienteModel.getUsuario());
        if (userByUsername != null) {
            throw new Exception("Usuário já existe!"); 
        }

        var userByCpf = this.userRepository.findByCpf(clienteModel.getCpf());
        if (userByCpf != null) {
            throw new Exception("CPF já cadastrado");
        }

        LocalDate dataNascimento = clienteModel.getDataNascimento();
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        
        if (idade < 18) {
            throw new Exception("Idade mínima 18 anos");
        }

        String senha = clienteModel.getSenha();
        if (senha.length() < 8) {
            throw new Exception("A senha deve ter no mínimo 8 caracteres.");
        }
        if (!senha.matches(".*[a-zA-Z].*") || !senha.matches(".*[0-9].*")) {
             throw new Exception("A senha deve conter letras e números.");
        }
        
        var passwordHashred = BCrypt.withDefaults()
            .hashToString(12, senha.toCharArray());
        clienteModel.setSenha(passwordHashred);
        
        return this.userRepository.save(clienteModel);
    }
}