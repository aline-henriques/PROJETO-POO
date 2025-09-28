package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_clientes")
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nomeCompleto;
    private String usuario;
    private String endereco;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String senha;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
