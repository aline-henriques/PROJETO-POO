import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";

import PainelGestao from "../pages/painel de gestão/PainelGestao";
import CadProdutos from "../pages/CadastrarProdutos/CadProdutos";
import GestaoProd from "../pages/GestaoProdutos/GestaoProd";

import PainelClientes from "../pages/PainelClientes/PainelClientes"; 
import GestaoClientes from "../pages/GestaoClientes/GestaoClientes";
import GestaoPedidos from "../pages/GestaoPedidos/GestaoPedidos";


import PainelProdutos from "../pages/PainelProdutos/PainelProdutos";
import CadastroCliente from "../pages/CadastrarCliente/CadCliente"; 
import Login from "../pages/Login/Login";
import HomePage from "../pages/HomePage/HomePage";

function AppRoutes() {
  return (
    <Routes>

      {/* ✅ Rotas públicas */}
      <Route path="/" element={<HomePage />} />
      <Route path="/cadastro" element={<CadastroCliente />} />
      <Route path="/login" element={<Login />} />


      {/* ✅ Rotas só para logados (CLIENTE ou ADMIN) */}
      <Route element={<PrivateRoute />}> 
      </Route>


      {/* ✅ Rotas só ADMIN */}
      <Route element={<PrivateRoute requiredRole="ADMIN" />}>
        
        <Route path="/painelGestao" element={<PainelGestao />} /> 

        {/* CLIENTES */}
        <Route path="/PainelClientes" element={<PainelClientes />} /> 
        <Route path="/GestaoClientes" element={<GestaoClientes />} />

        {/* PRODUTOS */}
        <Route path="/GestãoProdutos" element={<GestaoProd />} />
        <Route path="/products" element={<CadProdutos />} />
        <Route path="/PainelProdutos" element={<PainelProdutos />} />
        

        {/* PEDIDOS */}
        <Route path="/GestaoPedidos" element={<GestaoPedidos />} />
      
      </Route>

    </Routes>
  );
}

export default AppRoutes;
