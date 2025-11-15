import React, { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import LogoCachacaria from "../../assets/Fotos/LogoCachacaria.png";
import styles from "../Login/Login.module.css";

function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [error, setError] = useState("");
  const [rememberMe, setRememberMe] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await login(email, senha);
    } catch (err) {
      setError(err.message || "Erro ao tentar fazer login. Tente novamente.");
    }
  };

  return (
    <div className={styles.containerLogin}>
      <img src={LogoCachacaria} alt="imagem chamativa da logo" onClick={() => navigate("/")}/>
      <div className={styles.formularioLogin}>
        <h1>Olá! Bem vindo</h1>
        <div className={styles.space}>
          <h4>Ainda não tem uma conta?</h4>
          <a
            onClick={() => navigate("/cadastro")}
            className={styles.linkCadastreSe}
          >
            Cadastre-se
          </a>
        </div>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="E-mail"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className={styles.inputWrite}
            required
          />
          <input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            className={styles.inputWrite}
            required
          />
          {error && <p style={{ color: "red" }}>{error}</p>}

          <div className={styles.space}>
            <label className={styles.spaceBox}>
              <input
                type="checkbox"
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
              />
              Manter-me logado
            </label>
            <p className={styles.forgetPassword}>Esqueceu a senha?</p>
          </div>

          <button type="submit" className={styles.loginButton}>Entrar</button>
        </form>
      </div>
    </div>
  );
}

export default Login;
