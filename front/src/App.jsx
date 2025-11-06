import React from 'react';
import { AuthProvider } from "./context/AuthContext";

import Navbar from "./components/navbar/navbar";
import Sidebar from "./components/sidebar/sidebar";
import AppRoutes from "./routes/AppRoutes";
import styles from "./App.module.css";

function App() {
  return (
    <AuthProvider> 
      
        
        
          <main>
            <AppRoutes /> 
          </main>
      
      
    </AuthProvider>
  );
}

export default App;