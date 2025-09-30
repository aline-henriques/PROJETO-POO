import React, { useEffect, useState } from "react";
import ProdutoModal from "../../components/ProdutoModal/ProdutoModal"
import ProdutoTable from "../../components/TabelaProdutos/ProdutoTable";
import styles from "../PainelProdutos/PainelProdutos.module.css"

export default function ProdutosGestao() {
  const [produtos, setProdutos] = useState([]);
  const [produtoSelecionado, setProdutoSelecionado] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);

  useEffect(() => {
    carregarProdutos();
  }, []);

  const carregarProdutos = () => {
    fetch("https://projeto-poo-1-tn4v.onrender.com/produtos/")
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao buscar produtos"))
      .then((data) => setProdutos(data))
      .catch((err) => console.error(err));
  };

  const deletarProduto = (id) => {
    if (window.confirm("Deseja realmente excluir este produto?")) {
      fetch(`https://projeto-poo-1-tn4v.onrender.com/produtos/${id}`, { method: "DELETE" })
        .then((res) => {
          if (res.ok) carregarProdutos();
          else alert("Erro ao excluir produto");
        })
        .catch((err) => console.error(err));
    }
  };

  const abrirModalEdicao = (produto) => {
    setProdutoSelecionado(produto);
    setMostrarModal(true);
  };

  const salvarEdicao = (produtoAtualizado) => {
    fetch(`https://projeto-poo-1-tn4v.onrender.com/produtos/${produtoAtualizado.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(produtoAtualizado),
    })
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao atualizar"))
      .then(() => {
        setMostrarModal(false);
        carregarProdutos();
      })
      .catch((err) => console.error(err));
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.titulo}>Gestão de Produtos</h2>

      <ProdutoTable
        produtos={produtos}
        onEdit={abrirModalEdicao}
        onDelete={deletarProduto}
      />

      {mostrarModal && (
        <ProdutoModal
          produto={produtoSelecionado}
          onClose={() => setMostrarModal(false)}
          onSave={salvarEdicao}
        />
      )}
    </div>
  );
}