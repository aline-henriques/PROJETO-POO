// /src/pages/GestaoEstoque/GestaoEstoque.jsx
import { useEffect, useState } from "react";
import { estoqueService } from "../../Services/estoqueService";
import EstoqueTable from "../../components/EstoqueTable/EstoqueTable";
import MovimentacaoEntradaModal from "../../components/EstoqueModal/MovimentacaoEntradaModal";
import MovimentacaoSaidaModal from "../../components/EstoqueModal/MovimentacaoSaidaModal";
import styles from "./GestaoEstoque.module.css";
import Navbar from "../../components/navbar/navbar";
import NavbarAdmin from "../../components/NavbarAdmin/NavbarAdmin";
import Sidebar from "../../components/sidebar/sidebar";

export default function GestaoEstoque() {
  const [estoque, setEstoque] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [filtroCategoria, setFiltroCategoria] = useState("TODOS");

  const [abrirEntrada, setAbrirEntrada] = useState(false);
  const [abrirSaida, setAbrirSaida] = useState(false);

  async function carregarEstoque() {
    try {
      setCarregando(true);
      const dados = await estoqueService.listar();
      setEstoque(dados);
    } catch (e) {
      console.error(e);
    } finally {
      setCarregando(false);
    }
  }

  useEffect(() => {
    carregarEstoque();
  }, []);

  const estoqueFiltrado = estoque.filter((prod) => {
    if (filtroCategoria === "TODOS") return true;
    return prod.categoria === filtroCategoria;
  });

  return (
    <div >
      <NavbarAdmin />
      <div className={styles.container}>
        <Sidebar />
        <div className={styles.content}>
          <h1 className={styles.titulo}>Gestão de Estoque</h1>

          <div className={styles.topBar}>
            <select
              className={styles.selectFiltro}
              value={filtroCategoria}
              onChange={(e) => setFiltroCategoria(e.target.value)}
            >
              <option value="TODOS">Todos</option>
              <option value="BEBIDA">Bebidas</option>
              <option value="ARTESANATO">Artesanato</option>
            </select>

            <button
              className={styles.botaoEntrada}
              onClick={() => setAbrirEntrada(true)}
            >
              + Entrada
            </button>

            <button
              className={styles.botaoSaida}
              onClick={() => setAbrirSaida(true)}
            >
              - Saída
            </button>
          </div>

          {carregando ? (
            <p>Carregando estoque...</p>
          ) : (
            <EstoqueTable produtos={estoqueFiltrado} />
          )}

          {abrirEntrada && (
            <MovimentacaoEntradaModal
              fechar={() => setAbrirEntrada(false)}
              atualizar={carregarEstoque}
              produtos={estoque}
            />
          )}

          {abrirSaida && (
            <MovimentacaoSaidaModal
              fechar={() => setAbrirSaida(false)}
              atualizar={carregarEstoque}
              produtos={estoque}
            />
          )}
        </div>
      </div>
    </div>
  );
}
