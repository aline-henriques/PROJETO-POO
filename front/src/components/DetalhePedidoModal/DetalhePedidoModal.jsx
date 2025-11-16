// src/components/DetalhePedidoModal/DetalhePedidoModal.jsx
import { useEffect, useState } from "react";
import styles from "./DetalhePedidoModal.module.css";
import { pedidoService } from "../../Services/pedidoService";

export default function DetalhePedidoModal({ isOpen, onClose, pedidoId }) {
  const [pedido, setPedido] = useState(null);
  const [historico, setHistorico] = useState([]);
  const [loading, setLoading] = useState(false);

  const carregar = async () => {
    try {
      setLoading(true);

      const p = await pedidoService.buscarPorId(pedidoId);
      let h = await pedidoService.listarHistorico(pedidoId);

      // Normalizando histórico
      h = (h || []).map((item) => ({
        ...item,
        data: item.dataHoraRegistro,
        texto:
          item.observacao ||
          `${item.statusAnterior} → ${item.novoStatus}`,
      }));

      setPedido(p);
      setHistorico(h);
    } catch (err) {
      console.error("Erro ao carregar detalhe do pedido:", err);
      alert("Erro ao carregar detalhes do pedido.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isOpen && pedidoId) carregar();
    if (!isOpen) {
      setPedido(null);
      setHistorico([]);
      setLoading(false);
    }
  }, [isOpen, pedidoId]);

  if (!isOpen || loading || !pedido) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <button className={styles.btnFechar} onClick={onClose}>X</button>

        <h2>Pedido #{pedido.id}</h2>

        <h3>Cliente</h3>
        <p><b>CPF:</b> {pedido.clienteId}</p>

        {pedido.enderecoEntrega && (
          <p>
            {pedido.enderecoEntrega.logradouro},{' '}
            {pedido.enderecoEntrega.numero}{' '}
            {pedido.enderecoEntrega.bairro && `- ${pedido.enderecoEntrega.bairro}`}
          </p>
        )}

        <h3>Itens</h3>
        <ul className={styles.itensLista}>
          {pedido.itens.map((item, index) => (
            <li key={index}>
              <span>{item.quantidade}x {item.produtoId}</span>
              <span>R$ {item.precoUnitario.toFixed(2)}</span>
            </li>
          ))}
        </ul>

        <h3>Histórico</h3>
        <ul className={styles.historico}>
          {historico.length > 0 ? (
            historico.map((h) => (
              <li key={h.id}>
                <div>
                  <b>{new Date(h.data).toLocaleString()}</b>
                </div>
                <div>{h.texto}</div>
              </li>
            ))
          ) : (
            <p>Nenhuma anotação registrada.</p>
          )}
        </ul>
      </div>
    </div>
  );
}
