import React, { useEffect, useState } from 'react';
import { reportesService } from '../services/api';

const Dashboard = () => {
    const [stats, setStats] = useState({ activos: 0, atrasados: 0, clientesAtrasados: 0 });

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const prestamosRes = await reportesService.getPrestamosActivos();
                const activos = prestamosRes.data.filter(p => p.estado === 'VIGENTE').length;
                const atrasados = prestamosRes.data.filter(p => p.estado === 'ATRASADO').length;

                const clientesRes = await reportesService.getClientesConAtrasos();
                
                setStats({ activos, atrasados, clientesAtrasados: clientesRes.data.length });
            } catch (error) {
                console.error("Error fetching stats", error);
            }
        };
        fetchStats();
    }, []);

    return (
        <div className="glass-card">
            <h1 style={{ background: 'linear-gradient(90deg, #60efff, #00ff87)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
                Dashboard - ToolRent
            </h1>
            <p style={{ color: '#94a3b8' }}>Sistema Integral de Gestión de Préstamos de Herramientas</p>
            
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem', marginTop: '2rem' }}>
                <div style={{ padding: '1.5rem', background: 'rgba(255,255,255,0.02)', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.05)' }}>
                    <h3 style={{ margin: '0 0 0.5rem 0', color: '#60efff' }}>Préstamos Activos</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', margin: 0 }}>{stats.activos}</p>
                </div>
                <div style={{ padding: '1.5rem', background: 'rgba(255,65,108,0.1)', borderRadius: '8px', border: '1px solid rgba(255,65,108,0.2)' }}>
                    <h3 style={{ margin: '0 0 0.5rem 0', color: '#ff416c' }}>Préstamos Atrasados</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', margin: 0 }}>{stats.atrasados}</p>
                </div>
                <div style={{ padding: '1.5rem', background: 'rgba(255,193,7,0.1)', borderRadius: '8px', border: '1px solid rgba(255,193,7,0.2)' }}>
                    <h3 style={{ margin: '0 0 0.5rem 0', color: '#ffc107' }}>Clientes Restringidos</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', margin: 0 }}>{stats.clientesAtrasados}</p>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
