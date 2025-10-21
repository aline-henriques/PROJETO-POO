import styles from "./GestaoProd.module.css"
import {Plus, Trash, ListBullets, NotePencil } from "@phosphor-icons/react"
import { Link } from "react-router-dom";

function GestaoProd() {
  return (
    <div>
      
      <h1>Gestão de Produtos</h1>

      <div className={styles.arrayCircles}>
        
        <nav>
          <Link to="/products">
            <div className={styles.categoria}>
              <div className={styles.circles}>
                <Plus size={75} weight="fill" color="gray" />
              </div>
              <h3>Adicionar</h3>
            </div>
          </Link>
        </nav>
        
        <nav>
          <Link to="/PainelProdutos">
            <div className={styles.categoria}>
              <div className={styles.circles}>
                <ListBullets size={75} weight="fill" color="gray"/>
              </div>
              <h3>Gerir</h3>
            </div>
          </Link>
        </nav>
      </div>
        
      
    </div>
  );
}

export default GestaoProd;