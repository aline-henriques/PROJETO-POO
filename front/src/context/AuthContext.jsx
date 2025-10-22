import React, { createContext, useState, useContext} from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios'; // Importar axios para requisições HTTP

// URL base do seu backend
const API_URL = "http://localhost:8080/login"; // Altere a porta se necessário

// Cria o contexto
const AuthContext = createContext();

// Provider
export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(() => {
    // Inicializa o estado lendo do localStorage
    const role = localStorage.getItem("userRole");
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    return {
      role: role,
      isLoggedIn: isLoggedIn,
    };
  });

  const navigate = useNavigate();

  const login = async (email, senha) => {
    try {
      // 1. FAZ A REQUISIÇÃO POST PARA O BACKEND
      const response = await axios.post(API_URL, { email, senha });

      // Extrai a role retornada do seu LoginController Java (ex: "ADMIN", "CLIENTE")
      const { role } = response.data;

      // Salva a autenticação no estado e no localStorage
      setAuth({ role, isLoggedIn: true });
      localStorage.setItem("userRole", role);
      localStorage.setItem("isLoggedIn", "true");

      // Lógica de Redirecionamento:
      console.log(role);
      if (role === "ADMIN") {
        // Se o papel for ADMIN, navega para a página de gestão
        navigate("/painelGestao");
      } else if (role === "CLIENTE") {
        // Se for CLIENTE, pode ir para a página inicial ou de pedidos
        navigate("/");
      } else {
        // Para qualquer outra role não prevista
        navigate("/");
      }

    } catch (err) {
      // O backend retorna 401 Unauthorized em caso de falha de credencial
      if (err.response && err.response.status === 401) {
        // Usa a mensagem de erro que você configurou no LoginController
        const errorMessage = err.response.data.message || "E-mail/Usuário ou senha inválidos.";
        throw new Error(errorMessage);
      }

      // Lança um erro genérico para outros problemas (rede, servidor fora)
      console.error("Erro de comunicação com o servidor:", err);
      throw new Error("Erro de conexão com o servidor. Tente novamente.");
    }
  };

  const logout = () => {
    setAuth({ role: null, isLoggedIn: false });
    localStorage.removeItem("userRole");
    localStorage.removeItem("isLoggedIn");
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ ...auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Hook para usar o contexto
export const useAuth = () => useContext(AuthContext);

// Export padrão do contexto (opcional, mas útil)
export default AuthContext;