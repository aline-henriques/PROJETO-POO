import { useEffect, useState } from "react";
import styles from "./NovoPedido.module.css";
import { produtoService } from "../../Services/produtoService";

export default function NovoPedido({ isOpen, onClose, onPedidoCriado }) {
  const [produtos, setProdutos] = useState([]);
  const [itens, setItens] = useState([]);

  const [form, setForm] = useState({
    clienteId: "",
    valorFrete: "",
    produtoId: "",
    quantidade: 1,
    precoUnitario: "",
  });

  useEffect(() => {
    if (isOpen) carregarProdutos();
  }, [isOpen]);

  const carregarProdutos = async () => {
    try {
      const data = await produtoService.listarTodos();
      setProdutos(data);
    } catch (err) {
      console.error("Erro ao carregar produtos:", err);
      alert("Erro ao carregar produtos!");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Quando o usuário seleciona um produto, preencher automaticamente o preço
  const handleProdutoSelect = (e) => {
    const id = e.target.value;
    const produto = produtos.find((p) => p.id === id);

    setForm((prev) => ({
      ...prev,
      produtoId: id,
      precoUnitario: produto ? produto.preco : "",
    }));
  };

  const adicionarItem = () => {
    if (!form.produtoId) return alert("Selecione um produto");
    if (!form.quantidade || form.quantidade <= 0) return alert("Quantidade inválida");

    const produto = produtos.find((p) => p.id === form.produtoId);

    const novoItem = {
      produtoId: form.produtoId,
      quantidade: parseInt(form.quantidade),
      precoUnitario: parseFloat(form.precoUnitario),
      nomeProduto: produto?.nome,
    };

    setItens((prev) => [...prev, novoItem]);

    // limpar seleção do item
    setForm((prev) => ({
      ...prev,
      produtoId: "",
      quantidade: 1,
      precoUnitario: "",
    }));
  };

  const removerItem = (index) => {
    setItens((prev) => prev.filter((_, i) => i !== index));
  };

  const criarPedido = () => {
    if (!form.clienteId) return alert("Informe o CPF do cliente");
    if (itens.length === 0) return alert("Adicione ao menos 1 item");

    const payload = {
      clienteId: form.clienteId,
      valorFrete: parseFloat(form.valorFrete || 0),
      itens: itens.map(({ nomeProduto, ...rest }) => rest),
    };

    fetch("http://localhost:8080/pedidos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => res.ok ? res.json() : Promise.reject("Erro ao criar pedido"))
      .then((data) => {
        alert("Pedido criado com sucesso!");
        onPedidoCriado?.(data);
        onClose();
      })
      .catch((err) => {
        console.error(err);
        alert("Erro ao criar pedido");
      });
  };

  if (!isOpen) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>

        <button className={styles.btnFechar} onClick={onClose}>X</button>

        <h1 className={styles.titulo}>Criar Pedido Manual</h1>

        <label>CPF do Cliente</label>
        <input
          type="text"
          name="clienteId"
          value={form.clienteId}
          onChange={handleChange}
          className={styles.input}
        />

        <label>Valor do Frete</label>
        <input
          type="number"
          name="valorFrete"
          value={form.valorFrete}
          onChange={handleChange}
          className={styles.input}
        />

        <h3 className={styles.subtitulo}>Adicionar Item</h3>

        <label>Produto</label>
        <select
          name="produtoId"
          value={form.produtoId}
          onChange={handleProdutoSelect}
          className={styles.select}
        >
          <option value="">Selecione...</option>
          {produtos.map((p) => (
            <option key={p.id} value={p.id}>
              {p.nome}
            </option>
          ))}
        </select>

        <label>Quantidade</label>
        <input
          type="number"
          name="quantidade"
          value={form.quantidade}
          onChange={handleChange}
          className={styles.input}
        />

        <label>Preço Unitário</label>
        <input
          type="number"
          name="precoUnitario"
          value={form.precoUnitario}
          onChange={handleChange}
          className={styles.input}
          readOnly
        />

        <button className={styles.btnAdicionar} onClick={adicionarItem}>
          + Adicionar Item
        </button>

        <h3 className={styles.subtitulo}>Itens do Pedido</h3>

        <ul className={styles.listaItens}>
          {itens.map((item, i) => (
            <li key={i} className={styles.item}>
              <span>{item.nomeProduto} — {item.quantidade}x — R$ {item.precoUnitario}</span>
              <button onClick={() => removerItem(i)} className={styles.btnRemover}>Remover</button>
            </li>
          ))}
        </ul>

        <button className={styles.btnCriar} onClick={criarPedido}>
          Criar Pedido
        </button>

      </div>
    </div>
  );
}
