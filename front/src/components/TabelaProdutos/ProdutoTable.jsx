import React, { useState } from "react";
import styles from "../TabelaProdutos/ProdutoTable.module.css";

export default function ProdutoTable({ produtos, onEdit, onDelete }) {
  const [imagemSelecionada, setImagemSelecionada] = useState(null);

  const abrirImagem = (url) => {
    setImagemSelecionada(url);
  };

  const fecharImagem = () => {
    setImagemSelecionada(null);
  };


  return (
    <div>
      <table className={styles.tabela}>
        <thead>
          <tr className={styles.theadTr}>
            <th className={styles.th}>Nome</th>
            <th className={styles.th}>Foto</th>
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
              <td className={styles.td}>
                {Array.isArray(p.fotosUrls) && p.fotosUrls.length > 0 && p.fotosUrls[0] ? (
                  <button
                    className={styles.botaoFoto}
                    onClick={() => abrirImagem(p.fotosUrls[0])}
                  >
                    Ver Foto
                  </button>
                ) : (
                  <span className={styles.semFoto}>Nenhuma foto disponível. Adicione uma!</span>
                )}
              </td>
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

      {/* Modal de Imagem */}
      {imagemSelecionada && (
        <div className={styles.modalOverlay} onClick={fecharImagem}>
          <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
            <img
              src={imagemSelecionada}
              alt="Foto do produto"
              className={styles.modalImg}
            />
            <button className={styles.modalClose} onClick={fecharImagem}>
              Fechar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}