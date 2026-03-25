import React, { useState, useEffect } from 'react';
import { prestamosService } from '../services/api';

const Prestamos = () => {
    const [prestamos, setPrestamos] = useState([]);
    const [form, setForm] = useState({ clienteId: '', herramientaId: '', fechaPactada: '' });

    const fetchPrestamos = async () => {
        try {
            const res = await prestamosService.getAll();
            setPrestamos(res.data);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => { fetchPrestamos(); }, []);

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await prestamosService.registrarPrestamo({
                clienteId: form.clienteId,
                herramientaId: form.herramientaId,
                fechaPactada: new Date(form.fechaPactada).toISOString()
            });
            setForm({ clienteId: '', herramientaId: '', fechaPactada: '' });
            fetchPrestamos();
            alert('Préstamo registrado con éxito!');
        } catch (error) {
            alert('Error al registrar: ' + (error.response?.data || error.message));
        }
    };

    const devolver = async (id, estadoDev) => {
        try {
            await prestamosService.registrarDevolucion({
                prestamoId: id,
                estadoDevolucion: estadoDev
            });
            fetchPrestamos();
        } catch (error) {
            alert('Error');
        }
    };

    return (
        <div>
            <div className="glass-card">
                <h2>Generar Préstamo</h2>
                <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr auto', gap: '1rem', alignItems: 'end' }}>
                    <div className="form-group">
                        <label>ID Cliente</label>
                        <input type="number" className="form-control" name="clienteId" value={form.clienteId} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>ID Herramienta</label>
                        <input type="number" className="form-control" name="herramientaId" value={form.herramientaId} onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Fecha Devolución Pactada</label>
                        <input type="datetime-local" className="form-control" name="fechaPactada" value={form.fechaPactada} onChange={handleChange} required />
                    </div>
                    <button className="btn btn-primary" style={{ marginBottom: '1rem' }} type="submit">Prestar</button>
                </form>
            </div>

            <div className="glass-card">
                <h2>Historial y Activos</h2>
                <table className="glass-table">
                    <thead>
                        <tr>
                            <th>Resumen</th><th>Fechas</th><th>Costos</th><th>Estado</th><th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {prestamos.map(p => (
                            <tr key={p.id}>
                                <td><small>Cli: {p.clienteId}<br/>Herr: {p.herramientaId}</small></td>
                                <td><small>E: {new Date(p.fechaEntrega).toLocaleDateString()}<br/>D: {p.fechaDevolucion ? new Date(p.fechaDevolucion).toLocaleDateString() : '-'}</small></td>
                                <td><small>Atraso: ${p.multaAtraso || 0}<br/>Daño: ${p.cargoPorDano || 0}</small></td>
                                <td><span className={`badge badge-${p.estado === 'VIGENTE' ? 'success' : 'warning'}`}>{p.estado}</span></td>
                                <td>
                                    {p.estado === 'VIGENTE' || p.estado === 'ATRASADO' ? (
                                        <div style={{ display: 'flex', gap: '0.5rem' }}>
                                            <button className="btn btn-primary" style={{ padding: '0.2rem 0.5rem', fontSize: '0.7rem' }} onClick={() => devolver(p.id, 'OK')}>Ok</button>
                                            <button className="btn btn-warning" style={{ padding: '0.2rem 0.5rem', fontSize: '0.7rem' }} onClick={() => devolver(p.id, 'DANO_LEVE')}>Leve</button>
                                            <button className="btn btn-danger" style={{ padding: '0.2rem 0.5rem', fontSize: '0.7rem' }} onClick={() => devolver(p.id, 'DANO_IRREPARABLE')}>Roto</button>
                                        </div>
                                    ) : '-'}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Prestamos;
