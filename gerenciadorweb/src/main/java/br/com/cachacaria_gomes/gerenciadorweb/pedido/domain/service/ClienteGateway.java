package br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.service;

import br.com.cachacaria_gomes.gerenciadorweb.pedido.domain.model.EnderecoEntrega;

// Esta é a porta (contrato) que o módulo Pedido exige do módulo Cliente
public interface ClienteGateway {
    
    // CORREÇÃO: Certifique-se de que o tipo é String (CPF)
    EnderecoEntrega buscarEnderecoPorCpf(String cpf); 
    
    // CORREÇÃO: Certifique-se de que o tipo é String (CPF)
    boolean clienteExiste(String cpf);
}