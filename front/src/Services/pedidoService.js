// src/Services/pedidoService.js
import axios from "axios";

const API_URL = "http://localhost:8080/pedidos";

export const pedidoService = {
  listar: async (filtros = {}) => {
    // remove chaves com valores vazios/null
    const clean = Object.fromEntries(
      Object.entries(filtros).filter(([k, v]) => v !== "" && v != null)
    );

    const query = new URLSearchParams(clean).toString();
    const url = query ? `${API_URL}?${query}` : API_URL;

    const { data } = await axios.get(url);
    return data;
  },

  buscarPorId: async (id) => {
    const { data } = await axios.get(`${API_URL}/${id}`);
    return data;
  },

  atualizarStatus: async (id, status) => {
    const { data } = await axios.put(`${API_URL}/${id}/status`, {
      novoStatus: status,
    });
    return data;
  },

  cancelar: async (id, motivo) => {
    await axios.patch(`${API_URL}/${id}/cancelar`, {
      motivoCancelamento: motivo,
    });
  },

  listarHistorico: async (pedidoId) => {
    const { data } = await axios.get(`${API_URL}/${pedidoId}/historico`);

    return data.map((item) => ({
      ...item,
      data:
        item.data ||
        item.dataHora ||
        item.timestamp ||
        item.dataAlteracao ||
        null,
    }));
  },
};
