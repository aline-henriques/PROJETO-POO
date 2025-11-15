import { useState } from "react";
import styles from "./CadCliente.module.css";
import { useNavigate } from "react-router-dom";
import LogoCachacaria from "../../assets/Fotos/LogoCachacaria.png";

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

  const validarCPF = (cpf) => {
    cpf = cpf.replace(/[^\d]+/g, ""); // remove tudo que não for número
    if (cpf.length !== 11) return false;

    // Elimina CPFs inválidos conhecidos
    if (/^(\d)\1+$/.test(cpf)) return false;

    let soma = 0;
    for (let i = 0; i < 9; i++) soma += parseInt(cpf.charAt(i)) * (10 - i);
    let resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;

    soma = 0;
    for (let i = 0; i < 10; i++) soma += parseInt(cpf.charAt(i)) * (11 - i);
    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(10))) return false;

    return true;
  };

  const validarSenha = (senha) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    return regex.test(senha);
  };

  const validarEnderecoViaCEP = async (cep, dados) => {
    try {
      const res = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
      const info = await res.json();

      if (info.erro) {
        return "CEP não encontrado. Verifique e tente novamente.";
      }

      // Verifica se cidade e estado batem
      if (
        info.localidade.toLowerCase() !== dados.cidade.toLowerCase() ||
        info.uf.toLowerCase() !== dados.estado.toLowerCase()
      ) {
        return "O CEP não corresponde à cidade ou estado informados.";
      }

      return null; // Endereço válido
    } catch {
      return "Erro ao validar o CEP. Tente novamente mais tarde.";
    }
  };

  const validarIdadeMinima = (dataNascimento, idadeMinima = 18) => {
    const hoje = new Date();
    const nascimento = new Date(dataNascimento);
    let idade = hoje.getFullYear() - nascimento.getFullYear();
    const mes = hoje.getMonth() - nascimento.getMonth();

    if (mes < 0 || (mes === 0 && hoje.getDate() < nascimento.getDate())) {
      idade--;
    }

    return idade >= idadeMinima;
  };

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

    if (!validarCPF(formData.cpf)) {
      setError("CPF inválido. Verifique e tente novamente.");
      setLoading(false);
      return;
    }

    if (!validarSenha(formData.senha)) {
      setError(
        "A senha deve conter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números."
      );
      setLoading(false);
      return;
    }

    if (formData.senha !== formData.confirmarSenha) {
      setError("As senhas não coincidem.");
      setLoading(false);
      return;
    }

    if (!validarIdadeMinima(formData.dataNascimento)) {
      setError("Você precisa ter pelo menos 18 anos para se cadastrar.");
      setLoading(false);
      return;
    }

    const mensagemErroEndereco = await validarEnderecoViaCEP(
      formData.cep,
      formData
    );
    if (mensagemErroEndereco) {
      setError(mensagemErroEndereco);
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
        alert(
          "Cadastro realizado com sucesso! Você será redirecionado para o login."
        );
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
      setError(
        "Erro de conexão com o servidor. Verifique se o back-end está ativo."
      );
    }
  };

  return (
    <div className={styles.containerCad}>
      
      <img src={LogoCachacaria} alt="imagem chamativa da logo" onClick={() => navigate("/")}/>
      
      <div className={styles.formularioCad}>

        <h1>Cadastro de Cliente</h1>

        <div className={styles.spaceCad}>
          <p>Você já está registrado no site?</p>
          <a onClick={() => navigate('/login')} className={styles.linkLogin}>Faça Login</a>
        </div>

        <form onSubmit={handleSubmit}>
          {/* Se houver erro, exibe a mensagem do back-end ou a de validação de senha */}
          {error && <p className={styles.erro}>{error}</p>}
          {success && (
            <p className={styles.sucesso}>
              Cadastro efetuado! Redirecionando...
            </p>
          )}

          {/* 1. Dados Pessoais */}
          <input
            type="text"
            name="nomeCompleto"
            placeholder="Nome Completo"
            value={formData.nomeCompleto}
            onChange={handleChange}
            className={styles.inputWrite}
            required
          />
          <input
            type="text"
            name="usuario"
            placeholder="Nome de Usuário"
            value={formData.usuario}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="email"
            name="email"
            placeholder="E-mail"
            value={formData.email}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="cpf"
            placeholder="CPF (apenas números)"
            value={formData.cpf}
            onChange={handleChange}
            maxLength="11"
            required
            className={styles.inputWrite}
          />

         
          <input
            type="password"
            name="senha"
            placeholder="Senha (Mín. 8 caracteres, letras + números)"
            value={formData.senha}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="password"
            name="confirmarSenha"
            placeholder="Confirmar Senha"
            value={formData.confirmarSenha}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />

          <label className={styles.subtitulo}>Data de Nascimento:</label>
          <input
            type="date"
            name="dataNascimento"
            value={formData.dataNascimento}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />

          {/* 2. Endereço */}
          <h2 className={styles.subtitulo}>Endereço</h2>
          <input
            type="text"
            name="rua"
            placeholder="Rua"
            value={formData.rua}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="numero"
            placeholder="Número"
            value={formData.numero}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="bairro"
            placeholder="Bairro"
            value={formData.bairro}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="cidade"
            placeholder="Cidade"
            value={formData.cidade}
            onChange={handleChange}
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="estado"
            placeholder="Estado (Ex: PE)"
            value={formData.estado}
            onChange={handleChange}
            maxLength="2"
            required
            className={styles.inputWrite}
          />
          <input
            type="text"
            name="cep"
            placeholder="CEP (apenas números)"
            value={formData.cep}
            onChange={handleChange}
            maxLength="8"
            required
            className={styles.inputWrite}
          />

          {/* 3. Senhas */}

          <button type="submit" className={styles.cadButton} disabled={loading}>
            {loading ? "Cadastrando..." : "Cadastrar"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default CadCliente;
