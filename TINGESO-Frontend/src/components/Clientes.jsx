import React, { useState, useEffect } from 'react';
import { clientesService } from '../services/api';

const Clientes = () => {
    const [clientes, setClientes] = useState([]);
    const [form, setForm] = useState({ rut: '', nombre: '', correo: '', telefono: '' });

    const fetchClientes = async () => {
        try {
            const res = await clientesService.getAll();
            setClientes(res.data);
        } catch (error) {
            console.error("Error", error);
        }
    };

    useEffect(() => {
        fetchClientes();
    }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await clientesService.save(form);
            setForm({ rut: '', nombre: '', correo: '', telefono: '' });
            fetchClientes();
        } catch (error) {
            alert('Error al registrar cliente');
        }
    };

    return (
        <div>
            <div className="glass-card">
                <h2>Registrar Cliente</h2>
                <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr auto', gap: '1rem', alignItems: 'end' }}>
                    <div className="form-group">
                        <label>RUT</label>
                        <input className="form-control" name="rut" value={form.rut} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Nombre</label>
                        <input className="form-control" name="nombre" value={form.nombre} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Correo</label>
                        <input type="email" className="form-control" name="correo" value={form.correo} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Teléfono</label>
                        <input className="form-control" name="telefono" value={form.telefono} onChange={handleChange} required />
                    </div>
                    <button className="btn btn-primary" style={{ marginBottom: '1rem' }} type="submit">Guardar</button>
                </form>
            </div>

            <div className="glass-card">
                <h2>Lista de Clientes</h2>
                <table className="glass-table">
                    <thead>
                        <tr>
                            <th>ID</th><th>RUT</th><th>Nombre</th><th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        {clientes.map(c => (
                            <tr key={c.id}>
                                <td>{c.id}</td>
                                <td>{c.rut}</td>
                                <td>{c.nombre}</td>
                                <td><span className={`badge badge-${c.estado === 'ACTIVO' ? 'success' : 'danger'}`}>{c.estado}</span></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Clientes;
