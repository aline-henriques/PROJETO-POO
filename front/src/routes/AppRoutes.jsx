import { Routes, Route } from "react-router-dom";
import PrivateRoute from './PrivateRoute';
import PainelGestao from "../pages/painel de gestão/PainelGestao";
import CadProdutos from "../pages/CadastrarProdutos/CadProdutos";
import GestaoProd from "../pages/GestaoProdutos/GestaoProd";

import PainelClientes from "../pages/PainelClientes/PainelClientes"; 
import GestaoClientes from "../pages/GestaoClientes/GestaoClientes";
import GestaoPedidos from "../pages/GestaoPedidos/GestaoPedidos";
import DetalhePedido from "../pages/DetalhePedido/DetalhePedido";

import PainelProdutos from "../pages/PainelProdutos/PainelProdutos"
import CadastroCliente from "../pages/CadastrarCliente/CadCliente"; 
import Login from "../pages/Login/Login";
import HomePage from "../pages/HomePage/HomePage";



function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/PainelProdutos" element={<PainelProdutos />} />
      <Route path="/cadastro" element={<CadastroCliente />} />
      <Route path="/login" element={<Login />} /> 
      <Route path="/pedidos/:id" element={<DetalhePedido />} /> 


      <Route element={<PrivateRoute requiredRole="ADMIN" />}>
          <Route path="/painelGestao" element={<PainelGestao />} /> 
          
          {/* CLIENTES */}
          <Route path="/PainelClientes" element={<PainelClientes />} /> 
          <Route path="/GestaoClientes" element={<GestaoClientes />} />

          {/* PRODUTOS */}
          <Route path="/GestãoProdutos" element={<GestaoProd />} />
          <Route path="/products" element={<CadProdutos />} />
          
          {/* PEDIDOS */}
          <Route path="/GestaoPedidos" element={<GestaoPedidos />} />
      </Route>

    </Routes>
  );
}

export default AppRoutes;