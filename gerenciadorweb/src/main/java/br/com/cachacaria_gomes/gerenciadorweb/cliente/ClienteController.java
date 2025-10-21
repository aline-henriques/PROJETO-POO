package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("/")
    public ResponseEntity createUser(@Valid @RequestBody ClienteModel clienteModel){
        try {
            var userCreated = this.clienteService.createUser(clienteModel);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
            
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            
            if (errorMessage.equals("Usuário já existe!") || errorMessage.equals("CPF já cadastrado")) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); 
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append(". ");
        });
        
        if (errors.length() > 0) {
            errors.setLength(errors.length() - 2); 
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }
}