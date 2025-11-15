import styles from "./DetalhePedidoModal.module.css";
import { useEffect, useState } from "react";
import { pedidoService } from "../../Services/pedidoService.mock";

export default function DetalhePedidoModal({ isOpen, onClose, pedidoId }) {
  const [pedido, setPedido] = useState(null);
  const [loading, setLoading] = useState(true);

  const carregar = async () => {
    const data = await pedidoService.buscarPorId(pedidoId);
    setPedido(data);
    setLoading(false);
  };

  useEffect(() => {
    if (!isOpen) return;

    setLoading(true); 
    setPedido(null); 

    if (pedidoId) carregar();
  }, [isOpen, pedidoId]);

  if (!isOpen) return null;
  if (loading) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2>Detalhes do Pedido #{pedido.id}</h2>

        <button className={styles.btnFechar} onClick={onClose}>
          X
        </button>

        <div className={styles.secao}>
          <h3>Cliente</h3>
          <p>
            <b>Nome:</b> {pedido.cliente.nome}
          </p>
          <p>
            <b>Endereço:</b> {pedido.cliente.endereco?.rua}
          </p>
        </div>

        <div className={styles.secao}>
          <h3>Itens</h3>
          <ul>
            {pedido.itens.map((item) => (
              <li key={item.id}>
                {item.produto.nome} — {item.quantidade}x — R$ {item.preco}
              </li>
            ))}
          </ul>
        </div>

        <div className={styles.secao}>
          <h3>Histórico</h3>
          {pedido.historicoStatus?.length > 0 ? (
            <ul className={styles.historico}>
              {pedido.historicoStatus.map((h) => (
                <li key={h.id}>
                  <b>{new Date(h.data).toLocaleString()}</b>
                  <br />
                  {h.campo}: <b>{h.de}</b> → <b>{h.para}</b>
                </li>
              ))}
            </ul>
          ) : (
            <p>Nenhuma alteração registrada.</p>
          )}
        </div>
      </div>
    </div>
  );
}
