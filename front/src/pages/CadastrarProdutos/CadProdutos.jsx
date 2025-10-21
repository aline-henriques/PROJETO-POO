import { useState } from "react";
import styles from "../CadastrarProdutos/CadProdutos.module.css"


function CadProdutos() {
  const [tipoProduto, setTipoProduto] = useState("bebida");
  const [categoriaBebida, setCategoriaBebida] = useState("CACHACA");
  const [categoriaArtesanato, setCategoriaArtesanato] = useState("CERAMICA");
  const [formData, setFormData] = useState({
    nome: "",
    preco: "",
    fotosUrls: [],
    quantidadeEmEstoque: "",
    avaliacaoGeral: null,
    tipoProduto: "bebida",
    categoria: "CACHACA",
    // Bebida
    origem: "",
    teorAlcoolico: "",
    envelhecimento: "",
    madeiraEnvelhecimento: "",
    avaliacaoSommelier: "",
    // Artesanato
    tipoArtesanato: "CERAMICA",
    materialPrincipal: "",
    dimensoes: "",
    pesoKg: "",
    avaliacaoArtesao: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleTipoProdutoChange = (e) => {
    const value = e.target.value;
    setTipoProduto(value);
    setFormData({ ...formData, tipoProduto: value });
  };

  const handleCategoriaBebidaChange = (e) => {
    const value = e.target.value.toUpperCase();
    setCategoriaBebida(value);
    setFormData({ ...formData, categoria: value });
  };

  const handleCategoriaArtesanatoChange = (e) => {
    const value = e.target.value.toUpperCase();
    setCategoriaArtesanato(value);
    setFormData({ ...formData, tipoArtesanato: value });
  };

  const handleFotosChange = (e) => {
    const value = e.target.value;
    setFormData({ ...formData, fotosUrls: value.split(",").map((s) => s.trim()) });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

        let urlsArray = [];

        if (typeof formData.fotosUrls === 'string' && formData.fotosUrls.trim().length > 0) {
        // Ação: Converte a string em um array, dividindo por vírgula ou nova linha
          urlsArray = formData.fotosUrls
              .split(/[\n,]+/) // Divide por nova linha ou vírgula
              .map(url => url.trim()) // Remove espaços em branco
              .filter(url => url.length > 0); // Remove strings vazias
        } else if (Array.isArray(formData.fotosUrls)) {
              urlsArray = formData.fotosUrls;
          }

    const payload = {
      ...formData,
      preco: parseFloat(formData.preco) || 0,
      quantidadeEmEstoque: parseInt(formData.quantidadeEmEstoque) || 0,
      teorAlcoolico: formData.teorAlcoolico ? parseFloat(formData.teorAlcoolico) : null,
      envelhecimento: formData.envelhecimento ? parseInt(formData.envelhecimento) : null,
      pesoKg: formData.pesoKg ? parseFloat(formData.pesoKg) : null,
      fotosUrls: urlsArray,
    };

    console.log("Enviando payload:", payload);

    fetch("https://projeto-poo-1-tn4v.onrender.com/produtos/", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (res.ok) {
          alert("Produto cadastrado com sucesso!");
          setFormData({
            nome: "",
            preco: "",
            fotosUrls: [],
            quantidadeEmEstoque: "",
            avaliacaoGeral: null,
            tipoProduto,
            categoria: tipoProduto === "bebida" ? "CACHACA" : "",
            origem: "",
            teorAlcoolico: "",
            envelhecimento: "",
            madeiraEnvelhecimento: "",
            avaliacaoSommelier: "",
            tipoArtesanato: "CERAMICA",
            materialPrincipal: "",
            dimensoes: "",
            pesoKg: "",
            avaliacaoArtesao: "",
          });
        } else {
          res.text().then((text) => console.error("Erro ao cadastrar produto:", text));
          alert("Erro ao cadastrar produto");
        }
      })
      .catch((err) => console.error("Erro:", err));
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.titulo}>Cadastrar Produto</h1>
      <form onSubmit={handleSubmit} className={styles.formulario}>
        {/* Campos comuns */}
        <input
          type="text"
          name="nome"
          placeholder="Nome do produto"
          value={formData.nome}
          onChange={handleChange}
          required
          className={styles.input}
        />

        <input
          type="number"
          name="preco"
          placeholder="Preço"
          value={formData.preco}
          onChange={handleChange}
          required
          className={styles.input}
        />

       <input
          type="text"
          name="fotosUrls"
          placeholder="URLs das fotos (separadas por vírgula)"
          value={Array.isArray(formData.fotosUrls) ? formData.fotosUrls.join(", ") : formData.fotosUrls}
          onChange={handleFotosChange}
          className={styles.input}
        />

        <input
          type="number"
          name="quantidadeEmEstoque"
          placeholder="Quantidade em estoque"
          value={formData.quantidadeEmEstoque}
          onChange={handleChange}
          required
          className={styles.input}
        />

        <select
          name="tipoProduto"
          value={tipoProduto}
          onChange={handleTipoProdutoChange}
          className={styles.select}
        >
          <option value="bebida">Bebida</option>
          <option value="artesanato">Artesanato</option>
        </select>

        {/* Bebida */}
        {tipoProduto === "bebida" && (
          <>
            <select
              name="categoria"
              value={categoriaBebida}
              onChange={handleCategoriaBebidaChange}
              className={styles.select}
            >
              <option value="CACHACA">Cachaça</option>
              <option value="LICOR">Licor</option>
            </select>

            <input
              type="text"
              name="origem"
              placeholder="Origem (cidade/estado)"
              value={formData.origem}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="number"
              name="teorAlcoolico"
              placeholder="Teor alcoólico (%)"
              value={formData.teorAlcoolico}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="number"
              name="envelhecimento"
              placeholder="Tempo de envelhecimento (anos)"
              value={formData.envelhecimento}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="text"
              name="madeiraEnvelhecimento"
              placeholder="Madeira de envelhecimento"
              value={formData.madeiraEnvelhecimento}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="text"
              name="avaliacaoSommelier"
              placeholder="Avaliação do sommelier"
              value={formData.avaliacaoSommelier}
              onChange={handleChange}
              className={styles.input}
            />
          </>
        )}

        {/* Artesanato */}
        {tipoProduto === "artesanato" && (
          <>
            <select
              name="tipoArtesanato"
              value={categoriaArtesanato}
              onChange={handleCategoriaArtesanatoChange}
              className={styles.select}
            >
              <option value="CERAMICA">Cerâmica</option>
              <option value="MADEIRA">Madeira</option>
              <option value="TECIDO">Tecido</option>
              <option value="OUTROS">Outros</option>
            </select>

            <input
              type="text"
              name="materialPrincipal"
              placeholder="Material principal"
              value={formData.materialPrincipal}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="text"
              name="dimensoes"
              placeholder="Dimensões (AxLxP)"
              value={formData.dimensoes}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="number"
              name="pesoKg"
              placeholder="Peso (kg)"
              value={formData.pesoKg}
              onChange={handleChange}
              className={styles.input}
            />

            <input
              type="text"
              name="avaliacaoArtesao"
              placeholder="Avaliação do artesão"
              value={formData.avaliacaoArtesao}
              onChange={handleChange}
              className={styles.input}
            />
          </>
        )}

        <button
          type="submit"
          className={styles.botao}
        >
          Cadastrar
        </button>
      </form>
    </div>
  );
}

export default CadProdutos;