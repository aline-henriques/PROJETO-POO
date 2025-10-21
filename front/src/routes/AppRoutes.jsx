import { Routes, Route } from "react-router-dom";
import PainelGestao from "../pages/painel de gestão/PainelGestao";
import CadProdutos from "../pages/CadastrarProdutos/CadProdutos";
import GestaoProd from "../pages/GestaoProdutos/GestaoProd";
import PainelProdutos from "../pages/PainelProdutos/PainelProdutos"

import PainelClientes from "../pages/PainelClientes/PainelClientes";
import CadastroCliente from "../pages/CadastrarCliente/CadCliente"; 
import GestaoClientes from "../pages/GestaoClientes/GestaoClientes";
import GestaoPedidos from "../pages/GestaoPedidos/GestaoPedidos";

import DetalhePedido from "../pages/DetalhePedido/DetalhePedido";
import Login from "../pages/Login/Login";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<PainelGestao />} />
      <Route path="/GestãoProdutos" element={<GestaoProd />} />
      <Route path="/products" element={<CadProdutos />} />
      <Route path="/PainelProdutos" element = {<PainelProdutos />} />

      <Route path="/GestaoClientes" element={<GestaoClientes />} />
      <Route path="/GestaoPedidos" element={<GestaoPedidos />} />
      <Route path="/PainelClientes" element={<PainelClientes />} />

      <Route path="/cadastro" element={<CadastroCliente />} />
      <Route path="/login" element={<Login />} /> 
      <Route path="/pedidos/:id" element={<DetalhePedido />} />
    </Routes>
  );
}

export default AppRoutes;