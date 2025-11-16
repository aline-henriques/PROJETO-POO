// src/pages/GestaoPedidos/GestaoPedidos.jsx
import React, { useEffect, useState } from "react";
import PedidoTable from "../../components/PedidoTable/PedidoTable";
import { pedidoService } from "../../Services/pedidoService";
import NavbarAdmin from "../../components/NavbarAdmin/NavbarAdmin";
import Sidebar from "../../components/sidebar/sidebar";
import styles from "./GestaoPedidos.module.css";

export default function GestaoPedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [filtros, setFiltros] = useState({
    status: "",
    dataInicio: "",
    dataFim: "",
    clienteId: "",
    valorMin: "",
    valorMax: "",
  });

  const carregar = async () => {
    try {
      const data = await pedidoService.listar(filtros);
      setPedidos(data.content || data);
    } catch (err) {
      console.error("Erro ao carregar pedidos:", err);
    }
  };

  // agora recebe id explicitamente
  const atualizarPedido = async (id, acao, valor) => {
  try {
    if (acao === "status") {
      await pedidoService.atualizarStatus(id, valor);
    } else if (acao === "cancelar") {
      await pedidoService.cancelar(id, valor);
    } else {
      throw new Error("Ação desconhecida");
    }

    await carregar();
  } catch (err) {
    console.error("Erro ao atualizar pedido:", err);
    alert("Erro ao atualizar o pedido.");
  }
};

  useEffect(() => {
    carregar();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [filtros]);

  return (
    <div>
      <NavbarAdmin />

      <div className={styles.heroSection}>
        <Sidebar />

        <div className={styles.content}>
          <h1>Gestão de Pedidos</h1>

          <div style={{ marginBottom: "20px", display: "flex", gap: "10px" }}>
            <input
              placeholder="CPF do cliente"
              value={filtros.clienteId}
              onChange={(e) =>
                setFiltros({ ...filtros, clienteId: e.target.value })
              }
            />

            <select
              value={filtros.status}
              onChange={(e) =>
                setFiltros({ ...filtros, status: e.target.value })
              }
            >
              <option value="">Status</option>
              <option value="AGUARDANDO_PAGAMENTO">Aguardando Pagamento</option>
              <option value="PAGO">Pago</option>
              <option value="ENVIADO">Enviado</option>
              <option value="ENTREGUE">Entregue</option>
              <option value="CANCELADO">Cancelado</option>
            </select>

            <input
              type="date"
              value={filtros.dataInicio}
              onChange={(e) =>
                setFiltros({ ...filtros, dataInicio: e.target.value })
              }
            />

            <input
              type="date"
              value={filtros.dataFim}
              onChange={(e) =>
                setFiltros({ ...filtros, dataFim: e.target.value })
              }
            />

            <input
              type="number"
              step="0.01"
              placeholder="Valor mínimo"
              value={filtros.valorMin}
              onChange={(e) =>
                setFiltros({ ...filtros, valorMin: e.target.value })
              }
            />

            <input
              type="number"
              step="0.01"
              placeholder="Valor máximo"
              value={filtros.valorMax}
              onChange={(e) =>
                setFiltros({ ...filtros, valorMax: e.target.value })
              }
            />

            <button onClick={carregar}>Filtrar</button>
          </div>

          <PedidoTable pedidos={pedidos} onAtualizar={atualizarPedido} />
        </div>
      </div>
    </div>
  );
}
