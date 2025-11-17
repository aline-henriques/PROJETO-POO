import styles from "../painel de gestão/PainelGestao.module.css";

import {
  Package,
  Receipt,
  ChartBar,
  Users,
  Warehouse,
} from "@phosphor-icons/react";
import { Link } from "react-router-dom";
import NavbarAdmin from "../../components/NavbarAdmin/NavbarAdmin";
import Sidebar from "../../components/sidebar/sidebar";

function PainelGestao() {
  return (
    <div>
      <NavbarAdmin />
      <div className={styles.container}>

        <Sidebar />

        <div className={styles.content}>
          <h1>Atalhos de Gestão</h1>

          <div className={styles.arrayCircles}>
            <nav>
              <Link to="/GestaoProdutos">
                <div className={styles.categoria}>
                  <div className={styles.circles}>
                    <Package size={50} weight="fill" color="gray" />
                  </div>
                  <h3>Produtos</h3>
                </div>
              </Link>
            </nav>

            <nav>
              <Link to="/GestaoPedidos">
                <div className={styles.categoria}>
                  <div className={styles.circles}>
                    <Receipt size={50} weight="fill" color="gray" />
                  </div>
                  <h3>Pedidos</h3>
                </div>
              </Link>
            </nav>

            <nav>
              <Link to="/PainelClientes">
                <div className={styles.categoria}>
                  <div className={styles.circles}>
                    <Users size={50} weight="fill" color="gray" />
                  </div>
                  <h3>Clientes</h3>
                </div>
              </Link>
            </nav>
          </div>
          <div className={styles.arrayCircles}>
            <nav>
              <Link to="/GestaoEstoque">
                <div className={styles.categoria}>
                  <div className={styles.circles}>
                    <Warehouse size={50} weight="fill" color="gray" />
                  </div>
                  <h3>Estoque</h3>
                </div>
              </Link>
            </nav>

            <div className={styles.categoria}>
              <div className={styles.circles}>
                <ChartBar size={50} weight="fill" color="gray" />
              </div>
              <h3>Dashboard</h3>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PainelGestao;
