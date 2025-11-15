import React, { useState } from "react";
import styles from "../TabelaProdutos/ProdutoTable.module.css";
import PedidoModal from "../PedidosModal/PedidosModal";
import DetalhePedidoModal from "../DetalhePedidoModal/DetalhePedidoModal";
import { pedidoService } from "../../Services/pedidoService.mock";

export default function PedidoTable({ pedidos }) {
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
            <th className={styles.th}>ID</th>
            <th className={styles.th}>Cliente</th>
            <th className={styles.th}>Data</th>
            <th className={styles.th}>Valor Total</th>
            <th className={styles.th}>Status Pagamento</th>
            <th className={styles.th}>Status Envio</th>
            <th className={styles.th}>Entrega</th>
            <th className={styles.th}>Ações</th>
          </tr>
        </thead>

        <tbody>
          {pedidos.map((p) => (
            <tr key={p.id} className={styles.tr}>
              <td className={styles.td}>{p.id}</td>
              <td className={styles.td}>{p.cliente?.nome}</td>
              <td className={styles.td}>{new Date(p.data).toLocaleDateString()}</td>
              <td className={styles.td}>R$ {p.valorTotal}</td>
              <td className={styles.td}>{p.statusPagamento}</td>
              <td className={styles.td}>{p.statusEnvio}</td>
              <td className={styles.td}>{p.statusEntrega}</td>

              <td className={styles.td}>
                <button
                  className={styles.botaoEditar}
                  onClick={() => abrirDetalhes(p.id)}
                >
                  Ver Detalhes
                </button>

                <button
                  onClick={() => abrirModal(p)}
                  className={styles.botaoEditar}
                  style={{ marginLeft: "8px", background: "#1e90ff" }}
                >
                  Atualizar Status
                </button>
              </td>
            </tr>
          ))}
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
          onSaveStatus={async (campo, novoStatus) => {
            await pedidoService.atualizarStatus(
              pedidoSelecionado.id,
              campo,
              novoStatus
            );
            alert("Status atualizado");
          }}
          onCancelOrder={async (motivo) => {
            await pedidoService.cancelar(pedidoSelecionado.id, motivo);
            alert("Pedido cancelado");
          }}
        />
      )}
    </>
  );
}
