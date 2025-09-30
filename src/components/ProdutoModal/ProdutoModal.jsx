import React, { useState, useEffect } from "react";
import styles from "../ProdutoModal/ProdutoModal.module.css"

function ProdutoModal({ produto, onClose, onSave }) {
  const [form, setForm] = useState({
    nome: "",
    preco: "",
    quantidadeEmEstoque: "",
    categoria: "BEBIDA",
    tipoProduto: "bebida",
    origem: "",
    teorAlcoolico: "",
    tipoArtesanato: "",
    materialPrincipal: "",
    dimensoes: "",
    pesoKg: "",
    envelhecimento: "",
    madeiraEnvelhecimento: "",
    avaliacaoSommelier: "",
    avaliacaoArtesao: "",
    fotosUrls: [],
  });

  useEffect(() => {
    if (produto) {
      setForm({ ...produto });
    }
  }, [produto]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const url = produto?.id
      ? `https://projeto-poo-1-tn4v.onrender.com/produtos/${produto.id}`
      : "https://projeto-poo-1-tn4v.onrender.com/produtos/";

    const method = produto?.id ? "PUT" : "POST";

    // Converter tipos para numéricos quando necessário
    const payload = {
      ...form,
      preco: parseFloat(form.preco) || 0,
      quantidadeEmEstoque: parseInt(form.quantidadeEmEstoque) || 0,
      teorAlcoolico: form.teorAlcoolico ? parseFloat(form.teorAlcoolico) : null,
      envelhecimento: form.envelhecimento ? parseInt(form.envelhecimento) : null,
      pesoKg: form.pesoKg ? parseFloat(form.pesoKg) : null,
      fotosUrls: form.fotosUrls.length ? form.fotosUrls : [],
    };

    fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (res.ok) {
          onSave(payload);
        } else {
          res.text().then((text) => console.error("Erro:", text));
          alert("Erro ao salvar produto");
        }
      })
      .catch((err) => console.error("Erro:", err));
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h3 className={styles.titulo}>
          {produto?.id ? "Editar Produto" : "Novo Produto"}
        </h3>

        <form onSubmit={handleSubmit} className={styles.form}>
          <input
            type="text"
            name="nome"
            placeholder="Nome"
            value={form.nome}
            onChange={handleChange}
            className={styles.input}
            required
          />

          <input
            type="number"
            name="preco"
            placeholder="Preço"
            value={form.preco}
            onChange={handleChange}
            className={styles.input}
            required
          />

          <input
            type="number"
            name="quantidadeEmEstoque"
            placeholder="Estoque"
            value={form.quantidadeEmEstoque}
            onChange={handleChange}
            className={styles.input}
            required
          />

          <select
            name="tipoProduto"
            value={form.tipoProduto}
            onChange={handleChange}
            className={styles.input}
          >
            <option value="bebida">Bebida</option>
            <option value="artesanato">Artesanato</option>
          </select>

          {/* Campos Bebida */}
          {form.tipoProduto === "bebida" && (
            <>
              <input
                type="text"
                name="origem"
                placeholder="Origem"
                value={form.origem || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="number"
                name="teorAlcoolico"
                placeholder="Teor Alcoólico (%)"
                value={form.teorAlcoolico || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="number"
                name="envelhecimento"
                placeholder="Envelhecimento (anos)"
                value={form.envelhecimento || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="text"
                name="madeiraEnvelhecimento"
                placeholder="Madeira de Envelhecimento"
                value={form.madeiraEnvelhecimento || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="text"
                name="avaliacaoSommelier"
                placeholder="Avaliação do Sommelier"
                value={form.avaliacaoSommelier || ""}
                onChange={handleChange}
                className={styles.input}
              />
            </>
          )}

          {/* Campos Artesanato */}
          {form.tipoProduto === "artesanato" && (
            <>
              <input
                type="text"
                name="tipoArtesanato"
                placeholder="Tipo Artesanato"
                value={form.tipoArtesanato || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="text"
                name="materialPrincipal"
                placeholder="Material Principal"
                value={form.materialPrincipal || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="text"
                name="dimensoes"
                placeholder="Dimensões (AxLxP)"
                value={form.dimensoes || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="number"
                name="pesoKg"
                placeholder="Peso (kg)"
                value={form.pesoKg || ""}
                onChange={handleChange}
                className={styles.input}
              />
              <input
                type="text"
                name="avaliacaoArtesao"
                placeholder="Avaliação do Artesão"
                value={form.avaliacaoArtesao || ""}
                onChange={handleChange}
                className={styles.input}
              />
            </>
          )}

          <div className={styles.botoes}>
            <button
              type="button"
              className={styles.botaoCancelar}
              onClick={onClose}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className={styles.botaoSalvar}
            >
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default ProdutoModal;