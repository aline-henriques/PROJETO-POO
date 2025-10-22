import React from 'react'; // Adicione a importação do React
import { Link } from "react-router-dom";
import styles from "./navbar.module.css" // Ajuste o caminho se necessário

function Navbar() {

  const Header = () => (
    <div className={styles.header}>
      <h1 className={styles.logo}>
        <Link to="/">
          Gomes Artesanato e Cachaçaria
        </Link>
      </h1>
      <div className={styles.searchBarContainer}>
        <input type="text" placeholder="Pesquise algo aqui..." className={styles.searchBar} />
        <button className={styles.searchButton}>🔍</button>
      </div>
      <div className={styles.userSection}>

        <Link to="/login" className={styles.minhaConta}>
          Login
        </Link>
        <button className={styles.loginButton}>
          <Link to="/cadastro">
            Cadastre-se
          </Link></button>
        <button className={styles.carrinho}>🛒<span className={styles.badge}>0</span></button>
      </div>
    </div>
  );


  const CategoryNav = () => (
    <nav className={styles.categoryNav}>
      <a href="#" className={styles.categoryLink}>Cachaças ⌄</a>
      <a href="#" className={styles.categoryLink}>Kits ⌄</a>
      <a href="#" className={styles.categoryLink}>Licores ⌄</a>
      <a href="#" className={styles.categoryLink}>Artesanatos ⌄</a>
      <a href="#" className={styles.categoryLink}>Marcas ⌄</a>
      <a href="#" className={styles.categoryLink}>Clube ⌄</a>
    </nav>
  );

  return (
    <div className={styles.container}>
      <Header />
      <CategoryNav />
    </div>
  );
}

export default Navbar;