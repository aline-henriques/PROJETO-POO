// /src/Services/estoqueService.js
const API_URL = "http://localhost:8080/estoque";

export const estoqueService = {
  listar: async () => {
    const res = await fetch(API_URL);
    if (!res.ok) throw new Error("Erro ao buscar estoque");
    return res.json();
  },

  listarAlertas: async () => {
    const res = await fetch(`${API_URL}/alertas`);
    if (!res.ok) throw new Error("Erro ao buscar alertas");
    return res.json();
  },

  entrada: async (payload) => {
    const res = await fetch(`${API_URL}/entrada`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Erro ao registrar entrada");
    return res.json?.(); // suporta caso o backend não envie JSON
  },

  // sem acento — corrigido
  saida: async (payload) => {
    const res = await fetch(`${API_URL}/saida`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Erro ao registrar saída");
    return res.json?.();
  }
};
