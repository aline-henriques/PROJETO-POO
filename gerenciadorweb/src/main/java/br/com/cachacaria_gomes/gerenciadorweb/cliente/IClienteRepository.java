package br.com.cachacaria_gomes.gerenciadorweb.cliente;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteModel, UUID> {
    ClienteModel findByUsuario(String usuario);
    ClienteModel findByCpf(String cpf);
}
