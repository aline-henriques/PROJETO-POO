import { useAuth } from "../../context/AuthContext";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import LogoCachacaria from "../../assets/Fotos/LogoCachacaria.png";
import styles from "./navbar.module.css"; 

function Navbar() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  const Header = () => (
    <div className={styles.header}>
      <div className={styles.logo}>
        <img src={LogoCachacaria} alt="logo" />
      </div>
      <div className={styles.headerCenter}>
        <Link to="/" className={styles.titlle}>
          Gomes Artesanato e Cachaçaria
        </Link>
        <div className={styles.searchBarContainer}>
          <input
            type="text"
            placeholder="Pesquise algo aqui..."
            className={styles.searchBar}
          />
          <button className={styles.searchButton}>🔍</button>
        </div>
      </div>
      <div className={styles.upperSection}>
  {!isLoggedIn ? (
  <>

    {/* BLOCO GRANDE IGUAL AO FIGMA */}
    <div className={styles.loginBox}>
      <div className={styles.loginIcon}>👤</div>

      <div className={styles.loginTexts}>
        <p className={styles.loginTitle}>Minha Conta</p>

        <div className={styles.loginActions}>
          <Link to="/login" className={styles.loginLink}>Entre</Link>
          <span className={styles.loginDivider}> ou </span>
          <Link to="/cadastro" className={styles.loginLink}>Cadastre-se</Link>
        </div>
      </div>
    </div>

    {/* LINHA INFERIOR — Favoritos + Clube */}
    <div className={styles.subActions}>
      <button className={styles.secondaryBtn}>❤️ Favoritos</button>
      <button className={styles.secondaryBtn}>🎁 Clube</button>
    </div>

  </>
  ) : (
    <>
      {/* LINHA SUPERIOR — Minha Conta + Carrinho */}
      <div className={styles.accountRow}>
        <div className={styles.dropdown}>
          <button className={styles.minhaConta}>
  <span className={styles.userIcon}>👤</span>
  Minha Conta ⌄
</button>


          <div className={styles.dropdownMenu}>
            <Link to="/perfil">Editar Perfil</Link>
            <Link to="/historico">Histórico de Pedidos</Link>
            <Link to="/favoritos">Favoritados</Link>

            <button
              className={styles.logoutBtn}
              onClick={() => {
                logout();
                navigate("/");
              }}
            >
              Sair
            </button>
          </div>
        </div>

        <button className={styles.carrinho}>
          🛒<span className={styles.badge}>0</span>
        </button>
      </div>

      {/* LINHA INFERIOR — Favoritos + Clube */}
      <div className={styles.subActions}>
        <button className={styles.secondaryBtn}>
          ❤️ Favoritos
        </button>

        <button className={styles.secondaryBtn}>
          🎁 Clube
        </button>
      </div>
    </>
  )}
</div>
    </div>
  );

  const CategoryNav = () => (
    <nav className={styles.categoryNav}>
      <div className={styles.categoryItem}>
        <a className={styles.categoryLink}>Cachaças ⌄</a>

        <div className={styles.categoryDropdown}>
          <div className={styles.column}>
            <h4>Por Tipo</h4>
            <Link to="/cachacas/branca">Cachaça Branca</Link>
            <Link to="/cachacas/ouro">Cachaça Ouro</Link>
            <Link to="/cachacas/envelhecida">Envelhecida</Link>
            <Link to="/cachacas/premium">Premium</Link>
            <Link to="/cachacas/artesanal">Artesanal</Link>
          </div>

          <div className={styles.column}>
            <h4>Por Madeira</h4>
            <Link to="/cachacas/amburana">Amburana</Link>
            <Link to="/cachacas/carvalho">Carvalho</Link>
            <Link to="/cachacas/balsamo">Bálsamo</Link>
            <Link to="/cachacas/jequitiba">Jequitibá</Link>
            <Link to="/cachacas/blend">Blend de Madeiras</Link>
          </div>

          <div className={styles.column}>
            <h4>Teor Alcoólico</h4>
            <Link to="/cachacas/38">38%</Link>
            <Link to="/cachacas/40">40%</Link>
            <Link to="/cachacas/42">42%</Link>
            <Link to="/cachacas/44">44%</Link>
            <Link to="/cachacas/48+">48% ou Mais</Link>
          </div>

          <div className={styles.column}>
            <h4>Ocasiões</h4>
            <Link to="/cachacas/presentes">Presentes</Link>
            <Link to="/cachacas/drinks">Para Drinks</Link>
            <Link to="/cachacas/degustacao">Degustação</Link>
            <Link to="/cachacas/colecionadores">Colecionadores</Link>
            <Link to="/cachacas/exclusivas">Edições Limitadas</Link>
          </div>
        </div>
      </div>

      <a href="#" className={styles.categoryLink}>
        Kits ⌄
      </a>
      <a href="#" className={styles.categoryLink}>
        Licores ⌄
      </a>
      <a href="#" className={styles.categoryLink}>
        Artesanatos ⌄
      </a>
      <a href="#" className={styles.categoryLink}>
        Marcas ⌄
      </a>
      <a href="#" className={styles.categoryLink}>
        Clube ⌄
      </a>
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
