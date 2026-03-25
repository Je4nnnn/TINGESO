import React, { useState, useEffect } from 'react';
import { reportesService } from '../services/api';

const Reportes = () => {
    const [ranking, setRanking] = useState([]);
    
    useEffect(() => {
        const fetchRanking = async () => {
            try {
                // Fetch default last 30 days
                const end = new Date().toISOString();
                let start = new Date();
                start.setDate(start.getDate() - 30);
                const res = await reportesService.getRankingHerramientas(start.toISOString(), end);
                setRanking(res.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchRanking();
    }, []);

    return (
        <div>
            <div className="glass-card">
                <h2>Top Herramientas (Últimos 30 días)</h2>
                <table className="glass-table">
                    <thead>
                        <tr>
                            <th>ID Herramienta</th>
                            <th>Nombre</th>
                            <th>Categoría</th>
                            <th>Total Préstamos</th>
                        </tr>
                    </thead>
                    <tbody>
                        {ranking.map((r, i) => (
                            <tr key={i}>
                                <td>{r.herramienta?.id}</td>
                                <td>{r.herramienta?.nombre}</td>
                                <td>{r.herramienta?.categoria}</td>
                                <td style={{ fontWeight: 'bold', color: '#60efff' }}>{r.cantidadPrestamos}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Reportes;
