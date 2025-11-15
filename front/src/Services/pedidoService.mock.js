let pedidosMock = [
  {
    id: 1,
    cliente: { nome: "João", endereco: { rua: "Rua A" } },
    data: "2024-01-10",
    valorTotal: 100,
    statusPagamento: "PAGO",
    statusEnvio: "PREPARANDO",
    statusEntrega: "EM_TRANSITO",
    itens: [
      { id: 1, produto: { nome: "Produto X" }, quantidade: 2, preco: 50 }
    ],
    historicoStatus: []
  },

  {
    id: 2,
    cliente: { nome: "Maria", endereco: { rua: "Rua B" } },
    data: "2024-01-11",
    valorTotal: 200,
    statusPagamento: "AGUARDANDO",
    statusEnvio: "ENVIADO",
    statusEntrega: "EM_TRANSITO",
    itens: [
      { id: 2, produto: { nome: "Produto Y" }, quantidade: 1, preco: 200 }
    ],
    historicoStatus: []
  },

  {
    id: 3,
    cliente: { nome: "Pedro", endereco: { rua: "Rua C" } },
    data: "2024-01-12",
    valorTotal: 150,
    statusPagamento: "PAGO",
    statusEnvio: "ENVIADO",
    statusEntrega: "ENTREGUE",
    itens: [
      { id: 3, produto: { nome: "Produto Z" }, quantidade: 3, preco: 50 }
    ],
    historicoStatus: []
  }
];

export const pedidoService = {
  listar: async (filtros) => {
    return pedidosMock.filter((p) => {
      return (
        (!filtros.cliente ||
          p.cliente.nome.toLowerCase().includes(filtros.cliente.toLowerCase())) &&
        (!filtros.statusPagamento || p.statusPagamento === filtros.statusPagamento) &&
        (!filtros.statusEnvio || p.statusEnvio === filtros.statusEnvio) &&
        (!filtros.statusEntrega || p.statusEntrega === filtros.statusEntrega)
      );
    });
  },

  buscarPorId: async (id) => {
    return pedidosMock.find((p) => p.id === Number(id)); // <- AGORA FUNCIONA ✔
  },

  atualizarStatus: async (id, campo, novoStatus) => {
    const pedido = pedidosMock.find((p) => p.id === id);
    if (!pedido) return null;

    const anterior = pedido[campo];
    pedido[campo] = novoStatus;

    pedido.historicoStatus.push({
      id: Date.now(),
      campo,
      de: anterior,
      para: novoStatus,
      data: new Date().toISOString()
    });

    return pedido;
  },

  cancelar: async (id, motivo) => {
    const pedido = pedidosMock.find((p) => p.id === id);
    if (!pedido) return null;

    pedido.statusPagamento = "CANCELADO";

    pedido.historicoStatus.push({
      id: Date.now(),
      campo: "CANCELAMENTO",
      de: "-",
      para: motivo,
      data: new Date().toISOString()
    });

    return pedido;
  }
};
