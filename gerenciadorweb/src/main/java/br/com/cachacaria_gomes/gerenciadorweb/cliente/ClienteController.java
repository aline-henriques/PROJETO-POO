package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService; // Injete o Service, não mais o Repository

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody ClienteModel clienteModel){
        try {
            var userCreated = this.clienteService.createUser(clienteModel);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
            
        } catch (Exception e) {
            if (e.getMessage().equals("Usuário já existe!")) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            
            // Para outros erros
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
        }
    }
}
