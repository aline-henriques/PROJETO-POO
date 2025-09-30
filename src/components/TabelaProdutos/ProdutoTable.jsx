import React from "react";
import styles from "../TabelaProdutos/ProdutoTable.module.css"

export default function ProdutoTable({ produtos, onEdit, onDelete }) {
  return (
    <table className={styles.tabela}>
      <thead>
        <tr className={styles.theadTr}>
          <th className={styles.th}>Nome</th>
          <th className={styles.th}>Categoria</th>
          <th className={styles.th}>Preço</th>
          <th className={styles.th}>Estoque</th>
          <th className={styles.th}>Origem / Tipo Artesanato</th>
          <th className={styles.th}>Teor / Material</th>
          <th className={styles.th}>Envelhecimento / Dimensões</th>
          <th className={styles.th}>Madeira / Peso</th>
          <th className={styles.th}>Avaliações</th>
          <th className={styles.th}>Ações</th>
        </tr>
      </thead>
      <tbody>
        {produtos.map((p) => (
          <tr key={p.id} className={styles.tr}>
            <td className={styles.td}>{p.nome}</td>
            <td className={styles.td}>{p.categoria}</td>
            <td className={styles.td}>R$ {p.preco}</td>
            <td className={styles.td}>{p.quantidadeEmEstoque}</td>

            {/* Bebida ou Artesanato */}
            {p.tipoProduto === "bebida" ? (
              <>
                <td className={styles.td}>{p.origem}</td>
                <td className={styles.td}>{p.teorAlcoolico}%</td>
                <td className={styles.td}>{p.envelhecimento || "-"}</td>
                <td className={styles.td}>{p.madeiraEnvelhecimento || "-"}</td>
                <td className={styles.td}>{p.avaliacaoSommelier || "-"}</td>
              </>
            ) : (
              <>
                <td className={styles.td}>{p.tipoArtesanato}</td>
                <td className={styles.td}>{p.materialPrincipal}</td>
                <td className={styles.td}>{p.dimensoes || "-"}</td>
                <td className={styles.td}>{p.pesoKg || "-"}</td>
                <td className={styles.td}>{p.avaliacaoArtesao || "-"}</td>
              </>
            )}

            <td className={styles.td}>
              <button
                className={styles.botaoEditar}
                onClick={() => onEdit(p)}
              >
                Editar
              </button>
              <button
                className={styles.botaoExcluir}
                onClick={() => onDelete(p.id)}
              >
                Excluir
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}