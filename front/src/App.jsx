import Navbar from "./components/navbar/navbar";
import Sidebar from "./components/sidebar/sidebar"
import AppRoutes from "./routes/AppRoutes";
import styles from "./App.module.css"

function App() {
  return (
    <div className={styles.App}>
      <Navbar />
      <div className={styles.container}>
        <Sidebar/>
          <main >
            <AppRoutes />
          </main>
      </div>

    </div>
  );
}

export default App;