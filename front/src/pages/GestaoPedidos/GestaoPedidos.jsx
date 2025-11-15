import React, { useEffect, useState } from "react";
import PedidoTable from "../../components/PedidoTable/PedidoTable";
import { pedidoService } from "../../Services/pedidoService.mock";
import NavbarAdmin from "../../components/NavbarAdmin/NavbarAdmin";
import Sidebar from "../../components/sidebar/sidebar";
import styles from "./GestaoPedidos.module.css"

export default function GestaoPedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [filtros, setFiltros] = useState({
    statusPagamento: "",
    statusEnvio: "",
    statusEntrega: "",
    cliente: "",
  });

  const carregarPedidos = async () => {
    try {
      const data = await pedidoService.listar(filtros);
      setPedidos(data);
    } catch (err) {
      console.error("Erro ao carregar pedidos:", err);
    }
  };

  useEffect(() => {
    carregarPedidos();
  }, [filtros]);

  return (
    
    
    <div>
      <NavbarAdmin />
      <div className={styles.heroSection}>
        <div className={styles.sidebar}>
          <Sidebar  />
        </div>
        <div className={styles.content}>
          <h1>Gestão de Pedidos</h1>
          <div style={{ marginBottom: "20px" }}>
            <input
              placeholder="Cliente..."
              value={filtros.cliente}
              onChange={(e) => setFiltros({ ...filtros, cliente: e.target.value })}
            />

            <select
              value={filtros.statusPagamento}
              onChange={(e) =>
                setFiltros({ ...filtros, statusPagamento: e.target.value })
              }
            >
              <option value="">Pagamento</option>
              <option value="AGUARDANDO">Aguardando</option>
              <option value="PAGO">Pago</option>
              <option value="RECUSADO">Recusado</option>
            </select>

            <select
              value={filtros.statusEnvio}
              onChange={(e) =>
                setFiltros({ ...filtros, statusEnvio: e.target.value })
              }
            >
              <option value="">Envio</option>
              <option value="PREPARANDO">Preparando</option>
              <option value="ENVIADO">Enviado</option>
            </select>

            <select
              value={filtros.statusEntrega}
              onChange={(e) =>
                setFiltros({ ...filtros, statusEntrega: e.target.value })
              }
            >
              <option value="">Entrega</option>
              <option value="EM_TRANSITO">Em Trânsito</option>
              <option value="ENTREGUE">Entregue</option>
            </select>

            <button onClick={carregarPedidos}>Filtrar</button>
          </div>

          <PedidoTable pedidos={pedidos} />

        </div>
      </div>
    </div>  
  
      
  );
}
