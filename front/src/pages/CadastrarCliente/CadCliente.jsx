import { useState } from "react";
import styles from "./CadCliente.module.css";
import { useNavigate } from "react-router-dom"; 

function CadCliente() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nomeCompleto: "",
    usuario: "",
    email: "",
    senha: "",
    cpf: "",
    dataNascimento: "",
    rua: "",
    numero: "",
    bairro: "",
    cidade: "",
    estado: "",
    cep: "",
    confirmarSenha: "", 
  });
  
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccess(false);
    setLoading(true);

    if (formData.senha !== formData.confirmarSenha) {
      setError("As senhas não coincidem.");
      setLoading(false);
      return;
    }

    const { confirmarSenha: _, ...payload } = formData;
    
    try {
      const res = await fetch("http://localhost:8080/clientes/", { 
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      setLoading(false);

      if (res.ok) {
        setSuccess(true);
        alert("Cadastro realizado com sucesso! Você será redirecionado para o login.");
        setTimeout(() => {
             navigate("/login");
        }, 1500);
      } else {
        const errorMessage = await res.text();
        setError(errorMessage); 
      }
    } catch (err) {
      setLoading(false);
      console.error("Erro na conexão:", err);
      setError("Erro de conexão com o servidor. Verifique se o back-end está ativo.");
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.titulo}>Cadastro de Cliente</h1>
      <form onSubmit={handleSubmit} className={styles.formulario}>
        
        {/* Se houver erro, exibe a mensagem do back-end ou a de validação de senha */}
        {error && <p className={styles.erro}>{error}</p>}
        {success && <p className={styles.sucesso}>Cadastro efetuado! Redirecionando...</p>}

        {/* 1. Dados Pessoais */}
        <input type="text" name="nomeCompleto" placeholder="Nome Completo" value={formData.nomeCompleto} onChange={handleChange} required className={styles.input} />
        <input type="text" name="usuario" placeholder="Nome de Usuário" value={formData.usuario} onChange={handleChange} required className={styles.input} />
        <input type="email" name="email" placeholder="E-mail" value={formData.email} onChange={handleChange} required className={styles.input} />
        <input type="text" name="cpf" placeholder="CPF (apenas números)" value={formData.cpf} onChange={handleChange} maxLength="11" required className={styles.input} />
        
        <label className={styles.label}>Data de Nascimento:</label>
        <input type="date" name="dataNascimento" value={formData.dataNascimento} onChange={handleChange} required className={styles.input} />
        <input type="password" name="senha" placeholder="Senha (Mín. 8 caracteres, letras + números)" value={formData.senha} onChange={handleChange} required className={styles.input} />
        <input type="password" name="confirmarSenha" placeholder="Confirmar Senha" value={formData.confirmarSenha} onChange={handleChange} required className={styles.input} />

        {/* 2. Endereço */}
        <h2 className={styles.subtitulo}>Endereço</h2>
        <input type="text" name="rua" placeholder="Rua" value={formData.rua} onChange={handleChange} required className={styles.input} />
        <input type="text" name="numero" placeholder="Número" value={formData.numero} onChange={handleChange} required className={styles.input} />
        <input type="text" name="bairro" placeholder="Bairro" value={formData.bairro} onChange={handleChange} required className={styles.input} />
        <input type="text" name="cidade" placeholder="Cidade" value={formData.cidade} onChange={handleChange} required className={styles.input} />
        <input type="text" name="estado" placeholder="Estado (Ex: SP)" value={formData.estado} onChange={handleChange} maxLength="2" required className={styles.input} />
        <input type="text" name="cep" placeholder="CEP (apenas números)" value={formData.cep} onChange={handleChange} maxLength="8" required className={styles.input} />
        
        {/* 3. Senhas */}

        <button 
          type="submit" 
          className={styles.botao}
          disabled={loading}
        >
          {loading ? "Cadastrando..." : "Cadastrar"}
        </button>
      </form>
    </div>
  );
}

export default CadCliente;