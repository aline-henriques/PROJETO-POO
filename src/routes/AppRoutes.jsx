import { Routes, Route } from "react-router-dom";
import PainelGestao from "../pages/painel de gestão/PainelGestao";
import CadProdutos from "../pages/CadastrarProdutos/CadProdutos";
import GestaoProd from "../pages/GestaoProdutos/GestaoProd";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<PainelGestao />} />
      <Route path="/GestãoProdutos" element={<GestaoProd />} />
      <Route path="/products" element={<CadProdutos />} />
      
    </Routes>
  );
}

export default AppRoutes;