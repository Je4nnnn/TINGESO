package com.example.m2_prestamos.service;

import com.example.m2_prestamos.dto.*;
import com.example.m2_prestamos.entity.Prestamo;
import com.example.m2_prestamos.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository repository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private final String M1_URL = "http://m1-inventario/api/herramientas/";
    private final String M3_URL = "http://m3-clientes/api/clientes/";
    private final String M4_URL = "http://m4-tarifas/api/tarifas/";
    private final String M5_URL = "http://m5-kardex/api/kardex";

    public Prestamo registrarPrestamo(Prestamo p) {
        // Validar Cliente
        ClienteDTO cliente = restTemplate.getForObject(M3_URL + p.getClienteId(), ClienteDTO.class);
        if (cliente == null) throw new IllegalArgumentException("Cliente no existe");
        if (!"ACTIVO".equals(cliente.getEstado())) throw new IllegalArgumentException("Cliente restringido");

        List<Prestamo> activos = repository.findByClienteIdAndEstado(p.getClienteId(), "VIGENTE");
        if (activos.size() >= 5) throw new IllegalArgumentException("Límite de 5 préstamos alcanzado");
        
        List<Prestamo> atrasados = repository.findByClienteIdAndEstado(p.getClienteId(), "ATRASADO");
        if (!atrasados.isEmpty()) throw new IllegalArgumentException("Cliente con préstamos atrasados");

        List<Prestamo> mismoArticulo = repository.findByClienteIdAndHerramientaIdAndEstado(p.getClienteId(), p.getHerramientaId(), "VIGENTE");
        if (!mismoArticulo.isEmpty()) throw new IllegalArgumentException("No puede arrendar 2 veces la misma herramienta simultáneamente");

        // Validar Herramienta
        HerramientaDTO herramienta = restTemplate.getForObject(M1_URL + p.getHerramientaId(), HerramientaDTO.class);
        if (herramienta == null) throw new IllegalArgumentException("Herramienta no existe");
        if (!"DISPONIBLE".equals(herramienta.getEstado())) throw new IllegalArgumentException("Herramienta no disponible");

        if (p.getFechaPactada() == null || p.getFechaPactada().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Fecha pactada inválida");
        }

        p.setFechaEntrega(LocalDateTime.now());
        p.setEstado("VIGENTE");
        p.setMultaAtraso(0.0);
        p.setCargoPorDano(0.0);
        Prestamo saved = repository.save(p);

        // Actualizar Herramienta en M1
        restTemplate.put(M1_URL + p.getHerramientaId() + "/estado?estado=PRESTADA", null);

        // Kardex M5
        MovimientoDTO mov = new MovimientoDTO();
        mov.setTipo("PRESTAMO");
        mov.setHerramientaId(p.getHerramientaId());
        mov.setFecha(LocalDateTime.now());
        mov.setUsuario("SISTEMA_M2");
        mov.setCantidadAfectada(-1);
        restTemplate.postForObject(M5_URL, mov, MovimientoDTO.class);

        return saved;
    }

    public Prestamo registrarDevolucion(DevolucionRequestDTO req) {
        Prestamo p = repository.findById(req.getPrestamoId()).orElseThrow(() -> new IllegalArgumentException("El préstamo no existe"));
        if (!"VIGENTE".equals(p.getEstado()) && !"ATRASADO".equals(p.getEstado())) {
            throw new IllegalArgumentException("El préstamo ya fue devuelto");
        }

        p.setFechaDevolucion(LocalDateTime.now());
        p.setEstado("DEVUELTO");

        // Calculo multas
        long diasAtraso = ChronoUnit.DAYS.between(p.getFechaPactada(), p.getFechaDevolucion());
        if (diasAtraso > 0) {
            TarifaDTO multaConfig = restTemplate.getForObject(M4_URL + "MULTA_ATRASO", TarifaDTO.class);
            double multaDiaria = multaConfig != null ? multaConfig.getMonto() : 0.0;
            p.setMultaAtraso(diasAtraso * multaDiaria);
            // Restringir cliente en M3
            restTemplate.put(M3_URL + p.getClienteId() + "/estado?estado=RESTRINGIDO", null);
        }

        // Estado Devolución (OK, DANO_LEVE, DANO_IRREPARABLE)
        String estadoHerramienta = "DISPONIBLE";
        if ("DANO_LEVE".equals(req.getEstadoDevolucion())) {
            estadoHerramienta = "EN_REPARACION";
            TarifaDTO repConfig = restTemplate.getForObject(M4_URL + "REPARACION", TarifaDTO.class);
            p.setCargoPorDano(repConfig != null ? repConfig.getMonto() : 10000.0); // Valor dummy
        } else if ("DANO_IRREPARABLE".equals(req.getEstadoDevolucion())) {
            estadoHerramienta = "DADA_DE_BAJA";
            HerramientaDTO h = restTemplate.getForObject(M1_URL + p.getHerramientaId(), HerramientaDTO.class);
            if (h != null) p.setCargoPorDano(h.getValorReposicion());
        }

        repository.save(p);

        // Actualizar M1
        restTemplate.put(M1_URL + p.getHerramientaId() + "/estado?estado=" + estadoHerramienta, null);

        // Actualizar Kardex
        MovimientoDTO mov = new MovimientoDTO();
        mov.setTipo("DADA_DE_BAJA".equals(estadoHerramienta) ? "BAJA" : "DEVOLUCION");
        mov.setHerramientaId(p.getHerramientaId());
        mov.setFecha(LocalDateTime.now());
        mov.setUsuario("SISTEMA_M2");
        mov.setCantidadAfectada("BAJA".equals(mov.getTipo()) ? 0 : 1);
        restTemplate.postForObject(M5_URL, mov, MovimientoDTO.class);

        return p;
    }
    
    public List<Prestamo> getAll() { return repository.findAll(); }
    public List<Prestamo> getActivos() { return repository.findByEstado("VIGENTE"); }
    public List<Prestamo> getAtrasados() { return repository.findByEstado("ATRASADO"); }
    
    // Scheduled job omitted for marking "ATRASADO" based on clock
}
