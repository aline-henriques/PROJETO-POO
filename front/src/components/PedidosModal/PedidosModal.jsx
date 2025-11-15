import React, { useState } from "react";
import styles from "./PedidosModal.module.css";

export default function PedidoModal({
  isOpen,
  onClose,
  pedido,
  onSaveStatus,
  onCancelOrder,
}) {
  const [novoStatus, setNovoStatus] = useState("");
  const [motivo, setMotivo] = useState("");
  

  if (!isOpen || !pedido) return null;

  const isBloqueado =
    pedido?.statusEntrega === "ENTREGUE" ||
    pedido?.statusPagamento === "CANCELADO";

  const statusList = ["Em Separação", "Enviado", "Entregue"];

  const handleSalvar = () => {
    if (!novoStatus) return alert("Selecione um novo status!");
    onSaveStatus( novoStatus);
    setNovoStatus("");
    onClose();
  };

  const handleCancelarPedido = () => {
    if (!motivo) return alert("Informe o motivo do cancelamento!");
    onCancelOrder(motivo);
    setMotivo("");
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2>Status do Pedido #{pedido.id}</h2>

        <button className={styles.btnFechar} onClick={onClose}>
          X
        </button>

        {isBloqueado && (
          <div className={styles.alerta}>
            ⚠ Este pedido não pode mais ser alterado.
          </div>
        )}

        <label>Status de Envio</label>
        <select
          className={styles.select}
          value={novoStatus}
          onChange={(e) => setNovoStatus(e.target.value)}
          disabled={isBloqueado}
        >
          <option value="">Selecione...</option>
          {statusList.map((s) => (
            <option key={s} value={s}>
              {s}
            </option>
          ))}
        </select>

        <button className={styles.botaoSalvar} onClick={handleSalvar} disabled={isBloqueado}>
          Atualizar Status
          
        </button>

        {pedido.statusEnvio !== "Cancelado" && (
          <>
            <label>Motivo do cancelamento</label>
            <textarea
              className={styles.textarea}
              value={motivo}
              onChange={(e) => setMotivo(e.target.value)}
              disabled={isBloqueado}
            />

            <button
              className={styles.botaoCancelar}
              onClick={handleCancelarPedido}
              disabled={isBloqueado}
            >
              Cancelar Pedido
            </button>
          </>
        )}

        <h3>Histórico de Alterações</h3>
        {pedido.historicoStatus && pedido.historicoStatus.length > 0 ? (
          <ul className={styles.historico}>
            {pedido.historicoStatus.map((h) => (
              <li key={h.id}>
                {new Date(h.data).toLocaleString()} — <b>{h.campo}</b> alterado
                de <b>{h.de}</b> para <b>{h.para}</b>
              </li>
            ))}
          </ul>
        ) : (
          <p>Nenhuma alteração registrada.</p>
        )}
      </div>
    </div>
  );
}
