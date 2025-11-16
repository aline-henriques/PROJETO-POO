// src/components/PedidoTable/PedidoTable.jsx
import React, { useState } from "react";
import styles from "./PedidoTable.module.css"; 
import PedidoModal from "../PedidosModal/PedidosModal";
import DetalhePedidoModal from "../DetalhePedidoModal/DetalhePedidoModal";

export default function PedidoTable({ pedidos, onAtualizar }) {
  const [modalOpen, setModalOpen] = useState(false);
  const [detalheOpen, setDetalheOpen] = useState(false);
  const [pedidoSelecionado, setPedidoSelecionado] = useState(null);
  const [pedidoIdDetalhe, setPedidoIdDetalhe] = useState(null);

  const abrirModal = (pedido) => {
    setPedidoSelecionado(pedido);
    setModalOpen(true);
  };

  const abrirDetalhes = (id) => {
    setPedidoIdDetalhe(id);
    setDetalheOpen(true);
  };

  return (
    <>
      <table className={styles.tabela}>
        <thead>
          <tr className={styles.theadTr}>
            <th>ID</th>
            <th>Cliente (CPF)</th>
            <th>Data</th>
            <th>Total</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>

        <tbody>
          {pedidos.map((p) => {
            const cpf = p.clienteId.replace(
              /(\d{3})(\d{3})(\d{3})(\d{2})/,
              "$1.$2.$3-$4"
            );

            const data = new Date(p.dataCriacao).toLocaleString("pt-BR");

            return (
              <tr key={p.id}>
                <td>{p.id.slice(0, 8)}...</td>
                <td>{cpf}</td>
                <td>{data}</td>
                <td>R$ {p.valorTotal.toFixed(2)}</td>

                <td>
                  <span className={`${styles.badge} ${styles[`status-${p.status}`]}`}>
                    {p.status.replace("_", " ")}
                  </span>
                </td>

                <td>
                  <button
                    className={`${styles.botao} ${styles.btnDetalhes}`}
                    onClick={() => abrirDetalhes(p.id)}
                  >
                    Ver Detalhes
                  </button>

                  <button
                    className={`${styles.botao} ${styles.btnStatus}`}
                    onClick={() => abrirModal(p)}
                  >
                    Atualizar Status
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>

      <DetalhePedidoModal
        isOpen={detalheOpen}
        onClose={() => setDetalheOpen(false)}
        pedidoId={pedidoIdDetalhe}
      />

      {modalOpen && (
        <PedidoModal
          isOpen={modalOpen}
          onClose={() => setModalOpen(false)}
          pedido={pedidoSelecionado}
          onSave={onAtualizar}
        />
      )}
    </>
  );
}
