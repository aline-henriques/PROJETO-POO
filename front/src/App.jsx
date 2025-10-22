import React from 'react';
import { AuthProvider } from "./context/AuthContext";

import Navbar from "./components/navbar/navbar";
import Sidebar from "./components/sidebar/sidebar";
import AppRoutes from "./routes/AppRoutes";
import styles from "./App.module.css";

function App() {
  return (
    <AuthProvider> 
      <div className={styles.App}>
        <Navbar /> 
        <div className={styles.container}>
          <Sidebar />
          <main>
            <AppRoutes /> 
          </main>
        </div>
      </div>
    </AuthProvider>
  );
}

export default App;