import React, { useEffect, useState } from "react";
import styles from "./GestaoClientes.module.css"; 

export default function GestaoClientes() {
  const [clientes, setClientes] = useState([]);
  const [clienteSelecionado, setClienteSelecionado] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);

  useEffect(() => {
    carregarClientes();
  }, []);

  const carregarClientes = () => {
    fetch("http://localhost:8080/clientes/")
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao buscar clientes"))
      .then((data) => setClientes(data))
      .catch((err) => console.error("Erro ao carregar clientes:", err));
  };

  const deletarCliente = (id) => {
    if (window.confirm("Deseja realmente excluir este cliente?")) {
      fetch(`http://localhost:8080/clientes/${id}`, { method: "DELETE" })
        .then((res) => {
          if (res.ok) {
            alert("Cliente excluído com sucesso!");
            carregarClientes();
          }
          else alert("Erro ao excluir cliente");
        })
        .catch((err) => console.error(err));
    }
  };

  const abrirModalEdicao = (cliente) => {
    setClienteSelecionado(cliente);
    setMostrarModal(true);
  };

  const salvarEdicao = (clienteAtualizado) => {
    fetch(`http://localhost:8080/clientes/${clienteAtualizado.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(clienteAtualizado),
    })
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao atualizar cliente"))
      .then(() => {
        setMostrarModal(false);
        carregarClientes();
      })
      .catch((err) => console.error("Erro ao salvar edição:", err));
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.titulo}>Gestão de Clientes</h2>

      {clientes.length === 0 ? (
        <p>Nenhum cliente cadastrado.</p>
      ) : (
        <p>Tabela de clientes será exibida aqui.</p>
      )}

      {mostrarModal && (
        <ClienteModal
          cliente={clienteSelecionado}
          onClose={() => setMostrarModal(false)}
          onSave={salvarEdicao}
        />
      )}
    </div>
  );
}