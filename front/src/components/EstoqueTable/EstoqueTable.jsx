// /src/pages/GestaoEstoque/EstoqueTable.jsx
import styles from "./EstoqueTable.module.css";

export default function EstoqueTable({ produtos }) {

  function getStatus(prod) {
    if (prod.quantidadeAtual === 0) return "ESGOTADO";
    if (prod.quantidadeAtual <= prod.limiteMinimo) return "BAIXO";
    return "OK";
  }

  console.log("ITENS RECEBIDOS NO ESTOQUETABLE:", produtos);

  return (
    <table className={styles.tabela}>
      <thead>
        <tr className={styles.theadTr}>
          <th>Produto</th>
          <th>Unidade</th>
          <th>Quantidade</th>
          <th>Limite</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        {produtos.map((prod) => {
          const status = getStatus(prod);

          return (
            <tr key={prod.id}>
              <td>{prod.nome}</td>
              <td>{prod.unidadeVenda ?? "—"}</td>
              <td>{prod.quantidadeAtual}</td>
              <td>{prod.limiteMinimo}</td>

              <td>
                <span
                  className={`${styles.badge} ${styles["status-" + status]}`}
                >
                  {status}
                </span>
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}
