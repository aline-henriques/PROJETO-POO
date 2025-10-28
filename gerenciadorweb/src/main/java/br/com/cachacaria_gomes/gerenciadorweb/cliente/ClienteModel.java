package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.cachacaria_gomes.gerenciadorweb.enums.*;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate; // Alterado para LocalDate
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_clientes")
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {

    @Id
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos (apenas números)")
    private String cpf;

    @NotBlank(message = "O nome completo é obrigatório")
    private String nomeCompleto;

    @NotBlank(message = "O usuário é obrigatório")
    private String usuario;

    @NotBlank(message = "A rua é obrigatória")
    private String rua;
    @NotBlank(message = "O número é obrigatório")
    private String numero;
    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;
    @NotBlank(message = "O estado é obrigatório")
    private String estado;
    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos")
    private String cep;
    
    @Past(message = "A data de nascimento deve ser uma data passada") 
    private LocalDate dataNascimento;

    
    
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ter um formato válido")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    private String senha; 

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING) 
    @Column(name = "role", nullable = false)
    private Role role = Role.CLIENTE;
}