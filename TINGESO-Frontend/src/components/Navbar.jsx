import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-brand">ToolRent API</div>
            <ul className="navbar-links">
                <li><Link to="/">Dashboard</Link></li>
                <li><Link to="/inventario">Inventario</Link></li>
                <li><Link to="/clientes">Clientes</Link></li>
                <li><Link to="/prestamos">Préstamos</Link></li>
                <li><Link to="/reportes">Reportes</Link></li>
            </ul>
        </nav>
    );
};
export default Navbar;
