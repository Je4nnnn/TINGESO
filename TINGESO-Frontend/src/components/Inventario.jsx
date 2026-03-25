import React, { useState, useEffect } from 'react';
import { inventarioService } from '../services/api';

const Inventario = () => {
    const [herramientas, setHerramientas] = useState([]);
    const [form, setForm] = useState({ nombre: '', categoria: '', valorReposicion: '' });

    const fetchHerramientas = async () => {
        try {
            const res = await inventarioService.getAll();
            setHerramientas(res.data);
        } catch (error) {
            console.error("Error", error);
        }
    };

    useEffect(() => {
        fetchHerramientas();
    }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await inventarioService.save(form);
            setForm({ nombre: '', categoria: '', valorReposicion: '' });
            fetchHerramientas();
        } catch (error) {
            alert('Error al registrar');
        }
    };

    const darBaja = async (id) => {
        try {
            await inventarioService.darDeBaja(id);
            fetchHerramientas();
        } catch (error) {
            alert('Error');
        }
    };

    return (
        <div>
            <div className="glass-card">
                <h2>Registrar Herramienta</h2>
                <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr auto', gap: '1rem', alignItems: 'end' }}>
                    <div className="form-group">
                        <label>Nombre</label>
                        <input className="form-control" name="nombre" value={form.nombre} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Categoría</label>
                        <input className="form-control" name="categoria" value={form.categoria} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Valor de Reposición ($)</label>
                        <input type="number" className="form-control" name="valorReposicion" value={form.valorReposicion} onChange={handleChange} required />
                    </div>
                    <button className="btn btn-primary" style={{ marginBottom: '1rem' }} type="submit">Guardar</button>
                </form>
            </div>

            <div className="glass-card">
                <h2>Catálogo de Herramientas</h2>
                <table className="glass-table">
                    <thead>
                        <tr>
                            <th>ID</th><th>Nombre</th><th>Categoría</th><th>Estado</th><th>Valor ($)</th><th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {herramientas.map(h => (
                            <tr key={h.id}>
                                <td>{h.id}</td>
                                <td>{h.nombre}</td>
                                <td>{h.categoria}</td>
                                <td><span className={`badge badge-${h.estado === 'DISPONIBLE' ? 'success' : h.estado === 'PRESTADA' ? 'warning' : 'danger'}`}>{h.estado}</span></td>
                                <td>{h.valorReposicion}</td>
                                <td>
                                    {h.estado !== 'DADA_DE_BAJA' && 
                                     <button className="btn btn-danger" style={{ padding: '0.2rem 0.5rem', fontSize: '0.8rem' }} onClick={() => darBaja(h.id)}>Baja</button>}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Inventario;
