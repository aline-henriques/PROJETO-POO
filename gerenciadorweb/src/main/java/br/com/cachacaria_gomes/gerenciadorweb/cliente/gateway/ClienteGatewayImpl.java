package br.com.cachacaria_gomes.gerenciadorweb.cliente.gateway;

import br.com.cachacaria_gomes.gerenciadorweb.cliente.ClienteModel;
import br.com.cachacaria_gomes.gerenciadorweb.cliente.IClienteRepository; 
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.EnderecoEntrega;
import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Import necessário para @Service

import java.util.Optional;

@Service
public class ClienteGatewayImpl implements ClienteGateway {

    @Autowired
    private IClienteRepository userRepository; 

    @Override
    public EnderecoEntrega buscarEnderecoPorCpf(String cpf) {
        Optional<ClienteModel> cliente = userRepository.findById(cpf);
        
        if (cliente.isPresent()) {
            ClienteModel model = cliente.get();
            EnderecoEntrega endereco = new EnderecoEntrega();
            
            endereco.setCep(model.getCep());
            endereco.setLogradouro(model.getRua()); 
            endereco.setNumero(model.getNumero());
            endereco.setBairro(model.getBairro());
            endereco.setCidade(model.getCidade());
            endereco.setEstado(model.getEstado());
            
            return endereco;
        }
        return null; 
    }

    @Override
    public boolean clienteExiste(String cpf) {
        return userRepository.existsById(cpf);
    }
}