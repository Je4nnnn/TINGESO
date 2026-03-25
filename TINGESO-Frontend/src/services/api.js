import axios from 'axios';

const API_GATEWAY = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_GATEWAY,
});

export const inventarioService = {
    getAll: () => api.get('/m1-inventario/api/herramientas'),
    save: (data) => api.post('/m1-inventario/api/herramientas', data),
    darDeBaja: (id) => api.put(`/m1-inventario/api/herramientas/${id}/baja`),
};

export const prestamosService = {
    getAll: () => api.get('/m2-prestamos/api/prestamos'),
    getVigentes: () => api.get('/m2-prestamos/api/prestamos/vigentes'),
    getAtrasados: () => api.get('/m2-prestamos/api/prestamos/atrasados'),
    registrarPrestamo: (data) => api.post('/m2-prestamos/api/prestamos', data),
    registrarDevolucion: (data) => api.post('/m2-prestamos/api/prestamos/devolucion', data),
};

export const clientesService = {
    getAll: () => api.get('/m3-clientes/api/clientes'),
    save: (data) => api.post('/m3-clientes/api/clientes', data),
};

export const tarifasService = {
    getAll: () => api.get('/m4-tarifas/api/tarifas'),
    saveOrUpdate: (data) => api.post('/m4-tarifas/api/tarifas', data),
};

export const reportesService = {
    getPrestamosActivos: () => api.get('/m6-reportes/api/reportes/prestamos-activos'),
    getClientesConAtrasos: () => api.get('/m6-reportes/api/reportes/clientes-atrasados'),
    getRankingHerramientas: (start, end) => api.get(`/m6-reportes/api/reportes/ranking-herramientas?start=${start}&end=${end}`),
};

export default api;
