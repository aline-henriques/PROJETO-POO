// src/Services/produtoService.js
import axios from "axios";

const API = "http://localhost:8080/produtos";

export const produtoService = {
  listarTodos: async () => {
    try {
      const { data } = await axios.get(`${API}/`);
      return data; // retorna um array com ProdutoModel
    } catch (err) {
      console.error("Erro ao listar produtos:", err);
      throw err;
    }
  },

  buscarPorId: async (id) => {
    try {
      const { data } = await axios.get(`${API}/${id}`);
      return data;
    } catch (err) {
      console.error("Erro ao buscar produto:", err);
      throw err;
    }
  },
};
