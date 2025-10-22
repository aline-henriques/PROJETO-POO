package br.com.cachacaria_gomes.gerenciadorweb.cliente;

public class LoginRequest {

    private String email; 
    private String senha;
    
    public LoginRequest() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}