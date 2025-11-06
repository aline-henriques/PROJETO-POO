import { useAuth } from "../../context/AuthContext";
import { Link } from "react-router-dom";
import styles from "../sidebar/sidebar.module.css"

export default function Sidebar() {
  const { role, isLoggedIn } = useAuth();

  return (
    <aside className={styles.container}>
      
      {/* Sidebar padrão → deslogado OU cliente */}
      {(!isLoggedIn || role === "CLIENTE") && (
        <ul>
          <li><Link to="/PainelProdutos">Produtos</Link></li>
          <li><Link to="/pedidos">Meus pedidos</Link></li>
        </ul>
      )}

      {/* Sidebar admin */}
      {role === "ADMIN" && (
        <ul>
          <li><Link to="/painelGestao">Dashboard</Link></li>
          <li><Link to="/GestaoClientes">Gestão Clientes</Link></li>
          <li><Link to="/GestaoProdutos">Gestão Produtos</Link></li>
          <li><Link to="/GestaoPedidos">Gestão Pedidos</Link></li>
        </ul>
      )}
    </aside>
  );
}
