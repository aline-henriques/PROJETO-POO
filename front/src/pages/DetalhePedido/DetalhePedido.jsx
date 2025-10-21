import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

function DetalhePedido() {
  const { id } = useParams(); 
  const [pedido, setPedido] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    fetch(`http://localhost:8080/pedidos/${id}`) 
      .then((res) => {
        if (!res.ok) throw new Error("Pedido não encontrado ou erro de servidor.");
        return res.json();
      })
      .then((data) => {
        setPedido(data);
        setLoading(false);
      })
.catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [id]);

  if (loading) return <div>Carregando detalhes do pedido...</div>;
  if (error) return <div>Erro: {error}</div>;
  if (!pedido) return <div>Nenhum dado de pedido encontrado.</div>;

  return (
    <div /* className={styles.container} */>
      <Link to="/GestaoPedidos">Voltar para Pedidos</Link>
      <h1>Detalhes do Pedido #{pedido.id}</h1>
      <p>Status: <strong>{pedido.status}</strong></p>
      <p>Cliente: {pedido.clienteNome}</p>
      <p>Data: {new Date(pedido.dataCriacao).toLocaleDateString()}</p>
      
      <h3>Itens do Pedido</h3>
      <ul>
        {/* Assumindo que seu objeto pedido tem uma lista de itens */}
        {pedido.itens && pedido.itens.map(item => (
          <li key={item.id}>
            {item.nomeProduto} - Qtd: {item.quantidade} - R$ {item.precoUnitario}
          </li>
        ))}
      </ul>
      
      {/* Adicione mais detalhes aqui (endereço, total, etc.) */}
    </div>
  );
}

export default DetalhePedido;