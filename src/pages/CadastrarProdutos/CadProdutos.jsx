import { useState } from "react";

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

    const payload = {
      ...formData,
      preco: parseFloat(formData.preco) || 0,
      quantidadeEmEstoque: parseInt(formData.quantidadeEmEstoque) || 0,
      teorAlcoolico: formData.teorAlcoolico ? parseFloat(formData.teorAlcoolico) : null,
      envelhecimento: formData.envelhecimento ? parseInt(formData.envelhecimento) : null,
      pesoKg: formData.pesoKg ? parseFloat(formData.pesoKg) : null,
      fotosUrls: formData.fotosUrls.length ? formData.fotosUrls : [],
    };

    console.log("Enviando payload:", payload);

    fetch("http://localhost:8080/produtos/", {
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
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Cadastrar Produto</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Campos comuns */}
        <input
          type="text"
          name="nome"
          placeholder="Nome do produto"
          value={formData.nome}
          onChange={handleChange}
          required
          className="border p-2 w-full rounded"
        />

        <input
          type="number"
          name="preco"
          placeholder="Preço"
          value={formData.preco}
          onChange={handleChange}
          required
          className="border p-2 w-full rounded"
        />

        <input
          type="text"
          name="fotos"
          placeholder="URLs das fotos (separadas por vírgula)"
          value={formData.fotosUrls.join(", ")}
          onChange={handleFotosChange}
          className="border p-2 w-full rounded"
        />

        <input
          type="number"
          name="quantidadeEmEstoque"
          placeholder="Quantidade em estoque"
          value={formData.quantidadeEmEstoque}
          onChange={handleChange}
          required
          className="border p-2 w-full rounded"
        />

        <select
          name="tipoProduto"
          value={tipoProduto}
          onChange={handleTipoProdutoChange}
          className="border p-2 w-full rounded"
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
              className="border p-2 w-full rounded"
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
              className="border p-2 w-full rounded"
            />

            <input
              type="number"
              name="teorAlcoolico"
              placeholder="Teor alcoólico (%)"
              value={formData.teorAlcoolico}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />

            <input
              type="number"
              name="envelhecimento"
              placeholder="Tempo de envelhecimento (anos)"
              value={formData.envelhecimento}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />

            <input
              type="text"
              name="madeiraEnvelhecimento"
              placeholder="Madeira de envelhecimento"
              value={formData.madeiraEnvelhecimento}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />

            <input
              type="text"
              name="avaliacaoSommelier"
              placeholder="Avaliação do sommelier"
              value={formData.avaliacaoSommelier}
              onChange={handleChange}
              className="border p-2 w-full rounded"
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
              className="border p-2 w-full rounded"
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
              className="border p-2 w-full rounded"
            />

            <input
              type="text"
              name="dimensoes"
              placeholder="Dimensões (AxLxP)"
              value={formData.dimensoes}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />

            <input
              type="number"
              name="pesoKg"
              placeholder="Peso (kg)"
              value={formData.pesoKg}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />

            <input
              type="text"
              name="avaliacaoArtesao"
              placeholder="Avaliação do artesão"
              value={formData.avaliacaoArtesao}
              onChange={handleChange}
              className="border p-2 w-full rounded"
            />
          </>
        )}

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Cadastrar
        </button>
      </form>
    </div>
  );
}

export default CadProdutos;