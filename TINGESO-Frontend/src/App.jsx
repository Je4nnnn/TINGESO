import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';
import Inventario from './components/Inventario';
import Clientes from './components/Clientes';
import Prestamos from './components/Prestamos';
import Reportes from './components/Reportes';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Navbar />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/inventario" element={<Inventario />} />
            <Route path="/clientes" element={<Clientes />} />
            <Route path="/prestamos" element={<Prestamos />} />
            <Route path="/reportes" element={<Reportes />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
