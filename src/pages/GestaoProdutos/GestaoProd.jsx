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
                <Plus size={50} weight="fill" />
              </div>
              <h3>Adicionar</h3>
            </div>
          </Link>
        </nav>
        
        <div className={styles.categoria}>
          <div className={styles.circles}>
            <ListBullets size={50} weight="fill" />
          </div>
          <h3>Listar</h3>
        </div>
        <div className={styles.categoria}>
          <div className={styles.circles}>
            <NotePencil size={50} weight="fill" />
          </div>
          <h3>Editar</h3>
        </div>
      </div>
      <div className={styles.arrayCircles}>
        <div className={styles.categoria}>
          <div className={styles.circles}>
            <Trash size={50} weight="fill" />
          </div>
          <h3>Remover</h3>
        </div>
      </div>
        
      
    </div>
  );
}

export default GestaoProd;