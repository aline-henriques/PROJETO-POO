import React from 'react';
import { Link } from 'react-router-dom';
import styles from './PainelClientes.module.css';
// IMPORTANTE: Adiciona a importação dos ícones


export default function PainelClientes() {
  return (
    <div className={styles.container}>
      <h1 className={styles.titulo}>Gestão de Clientes</h1>

      <nav>
        <Link to="/cadastro">
          <div className={styles.categoria}>
            <div className={styles.circles}>
            </div>
            <h3>Adicionar</h3>
          </div>
        </Link>
      </nav>

      <nav>
        <Link to="/GestaoClientes">
          <div className={styles.categoria}>
            <div className={styles.circles}>


            </div>
            <h3>Gerir</h3>
          </div>
        </Link>
      </nav>

    </div>
  );
}