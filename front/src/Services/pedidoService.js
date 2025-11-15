import axios from "axios";

const API_URL = "http://localhost:8080/pedidos";

export const pedidoService = {
  listar: async (filtros = {}) => {
    const params = new URLSearchParams(filtros).toString();
    const { data } = await axios.get(`${API_URL}?${params}`);
    return data;
  },

  buscarPorId: async (id) => {
    const { data } = await axios.get(`${API_URL}/${id}`);
    return data;
  },

  atualizarStatus: async (id, status) => {
    const { data } = await axios.put(`${API_URL}/${id}/status`, status);
    return data;
  },

  cancelar: async (id, motivo) => {
    const { data } = await axios.patch(`${API_URL}/${id}/cancelar`, {
      motivo,
    });
    return data;
  },
};
