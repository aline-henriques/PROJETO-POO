import { Link } from "react-router-dom";
import styles from "../sidebar/sidebar.module.css"
import BotaoVoltar from "../BotãoVoltar/BotaoVoltar"

export default function Sidebar() {

  return (
    <aside className={styles.container}>
        <ul>
          <li><Link to="/painelGestao">Dashboard</Link></li>
          <li><Link to="/GestaoClientes">Gestão Clientes</Link></li>
          <li><Link to="/GestaoProdutos">Gestão Produtos</Link></li>
          <li><Link to="/GestaoPedidos">Gestão Pedidos</Link></li>
        </ul>

        <BotaoVoltar />
    </aside>
  );
}
