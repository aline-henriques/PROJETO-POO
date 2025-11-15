import { useAuth } from "../../context/AuthContext";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import LogoCachacaria from "../../assets/Fotos/LogoCachacaria.png";
import styles from "./navbarAdmin.module.css";

function NavbarAdmin() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div className={styles.logo}>
        <img src={LogoCachacaria} alt="logo" />
      </div>
      <Link to="/painelGestao" className={styles.titlle}>
        Gomes Artesanato e Cachaçaria
      </Link>
    </div>
  );
}
export default NavbarAdmin;
