import { Routes, Route } from "react-router-dom";
import PainelGestao from "../pages/painel de gestão/PainelGestao";
import CadProdutos from "../pages/CadProdutos";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<PainelGestao />} />
      <Route path="/products" element={<CadProdutos />} />
    </Routes>
  );
}

export default AppRoutes;