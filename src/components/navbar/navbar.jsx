import { Link } from "react-router-dom";
import styles from "../navbar/navbar.module.css"

function Navbar() {
  return (
    <div className={styles.container}>
      <div className={styles.titlle}>
        <h1>Gomes Artesanato e Cachaçaria</h1>
      </div>
      <div className={styles.userInfo}>
        <h3>Bem Vindo, </h3>
        <h3>USER</h3>
        <div className={styles.circles}></div>
      </div>
      
    </div>
    
  );
}

export default Navbar;