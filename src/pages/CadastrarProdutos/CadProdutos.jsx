import { useState } from "react";

function CadProdutos() {
  const [categoria, setCategoria] = useState("bebida");
  const [formData, setFormData] = useState({
    nome: "",
    preco: "",
    fotos: "",
    // comuns
    categoria: "bebida",
    // bebidas
    origem: "",
    teorAlcoolico: "",
    envelhecimento: "",
    madeira: "",
    avaliacaoSommelier: "",
    // artesanato
    material: "",
    dimensoes: "",
    peso: "",
    avaliacaoArtesao: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleCategoriaChange = (e) => {
    const value = e.target.value;
    setCategoria(value);
    setFormData({ ...formData, categoria: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Enviando:", formData);

    fetch("http://localhost:8080/api/produtos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    })
      .then((res) => {
        if (res.ok) {
          alert("Produto cadastrado com sucesso!");
        } else {
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
          placeholder="URL da foto"
          value={formData.fotos}
          onChange={handleChange}
          className="border p-2 w-full rounded"
        />

        <select
          name="categoria"
          value={categoria}
          onChange={handleCategoriaChange}
          className="border p-2 w-full rounded"
        >
          <option value="bebida">Bebida</option>
          <option value="artesanato">Artesanato</option>
        </select>

        {/* Campos específicos: bebidas */}
        {categoria === "bebida" && (
          <>
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
              name="madeira"
              placeholder="Madeira de envelhecimento"
              value={formData.madeira}
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

        {/* Campos específicos: artesanato */}
        {categoria === "artesanato" && (
          <>
            <input
              type="text"
              name="material"
              placeholder="Material principal"
              value={formData.material}
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
              name="peso"
              placeholder="Peso (kg)"
              value={formData.peso}
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