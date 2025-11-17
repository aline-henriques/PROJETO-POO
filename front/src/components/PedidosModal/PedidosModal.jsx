// src/components/PedidosModal/PedidosModal.jsx
import { useState } from "react";
import styles from "./PedidosModal.module.css";

function PedidoModal({ isOpen, onClose, pedido, onSave }) {
  const [novoStatus, setNovoStatus] = useState("");
  const [motivo, setMotivo] = useState("");

  if (!isOpen || !pedido) return null;

  const bloqueado = pedido.status === "CANCELADO" || pedido.status === "ENTREGUE";

  const salvar = () => {
    if (!novoStatus) return alert("Escolha um status");
    onSave(pedido.id, "status", novoStatus);
    onClose();
  };

  const cancelarPedido = () => {
    if (!motivo) return alert("Informe o motivo");
    onSave(pedido.id, "cancelar", motivo);
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <button className={styles.btnFechar} onClick={onClose}>X</button>

        <h2>Status do Pedido #{pedido.id}</h2>

        {bloqueado && (
          <div className={styles.alerta}>
            ⚠ Este pedido já está finalizado e não pode ser alterado.
          </div>
        )}

        <label>Novo Status</label>
        <select
          disabled={bloqueado}
          value={novoStatus}
          className={styles.select}
          onChange={(e) => setNovoStatus(e.target.value)}
        >
          <option value="">Selecione...</option>
          <option value="PAGO">PAGO</option>
          <option value="ENVIADO">ENVIADO</option>
          <option value="ENTREGUE">ENTREGUE</option>
        </select>

        <button
          className={`${styles.botaoSalvar} ${bloqueado ? styles.disabled : ""}`}
          disabled={bloqueado}
          onClick={salvar}
        >
          Atualizar Status
        </button>

        <label>Motivo do Cancelamento</label>
        <textarea
          disabled={bloqueado}
          value={motivo}
          className={styles.textarea}
          onChange={(e) => setMotivo(e.target.value)}
        />

        <button
          className={`${styles.botaoCancelar} ${bloqueado ? styles.disabled : ""}`}
          disabled={bloqueado}
          onClick={cancelarPedido}
        >
          Cancelar Pedido
        </button>
      </div>
    </div>
  );
}
export default PedidoModal