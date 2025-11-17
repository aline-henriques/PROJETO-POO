// /src/pages/GestaoEstoque/MovimentacaoSaidaModal.jsx
import { useState } from "react";
import { estoqueService } from "../../Services/estoqueService";
import styles from "../EstoqueModal/MovimentacaoSaidaModal.module.css";

export default function MovimentacaoSaidaModal({ fechar, atualizar, produtos }) {
  const [form, setForm] = useState({
    produtoId: "",
    quantidade: "",
    motivo: "",
    referencia: "",
    tipoMovimentacao: "SAIDA"
  });

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function salvar() {
    try {
      await estoqueService.saida(form);
      atualizar();
      fechar();
    } catch (e) {
      console.error(e);
    }
  }

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <button className={styles.btnFechar} onClick={fechar}>X</button>
        <h2>Saída de Estoque</h2>

        <label>Produto:</label>
        <select 
          className={styles.select}
          name="produtoId"
          onChange={handleChange}
        >
          <option value="">Selecione</option>
          {produtos.map(p => (
            <option value={p.id} key={p.id}>{p.nome}</option>
          ))}
        </select>

        <label>Quantidade:</label>
        <input 
          type="number"
          name="quantidade"
          className={styles.select}
          onChange={handleChange}
        />

        <label>Motivo:</label>
        <textarea 
          name="motivo"
          className={styles.textarea}
          onChange={handleChange}
        />

        <label>Referência:</label>
        <input 
          type="text"
          name="referencia"
          className={styles.select}
          onChange={handleChange}
        />

        <label>Tipo da Movimentação:</label>
        <select 
          name="tipoMovimentacao"
          className={styles.select}
          onChange={handleChange}
        >
          <option value="SAIDA">Saída</option>
          <option value="PERDA">Perda</option>
        </select>

        <button className={styles.botaoSalvar} onClick={salvar}>Registrar</button>
        <button className={styles.botaoCancelar} onClick={fechar}>Cancelar</button>
      </div>
    </div>
  );
}
