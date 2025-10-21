import React, { useEffect, useState } from "react";
import styles from "./GestaoPedidos.module.css";
import { useNavigate } from "react-router-dom";

function GestaoPedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    carregarPedidos();
  }, []);

  const carregarPedidos = () => {
    setLoading(true);

    fetch("http://localhost:8080/pedidos/")
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao buscar pedidos"))
      .then((data) => {
        setPedidos(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Erro ao carregar pedidos:", err);
        setLoading(false);
      });
  };

  const verDetalhes = (id) => {
    navigate(`/pedidos/${id}`);
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.titulo}>Gestão de Pedidos</h2>

      {loading && <p>Carregando pedidos...</p>}
      {!loading && pedidos.length === 0 && <p>Nenhum pedido encontrado.</p>}

      {/* Placeholder */}
      {!loading && pedidos.length > 0 && (
        <div>
          <p>Total de pedidos: {pedidos.length}</p>
          <p>Tabela de pedidos será exibida aqui com a opção de ver detalhes.</p>
          <PedidoTable pedidos={pedidos} onViewDetails={verDetalhes} />
        </div>
      )}
    </div>
  );
}

export default GestaoPedidos;