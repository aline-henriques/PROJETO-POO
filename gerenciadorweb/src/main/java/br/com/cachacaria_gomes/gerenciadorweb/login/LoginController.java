package br.com.cachacaria_gomes.gerenciadorweb.login;

import br.com.cachacaria_gomes.gerenciadorweb.cliente.LoginRequest;
import br.com.cachacaria_gomes.gerenciadorweb.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173") 
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        
        Role role = loginService.autenticar(request.getEmail(), request.getSenha());

        if (role != null) {
            Map<String, String> response = new HashMap<>();
            response.put("role", role.name()); 
            response.put("message", "Login bem-sucedido.");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("message", "E-mail/Usuário ou senha inválidos."));
        }
    }
}