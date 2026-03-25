package com.example.m5_kardex.service;

import com.example.m5_kardex.entity.Movimiento;
import com.example.m5_kardex.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository repository;

    public Movimiento registrar(Movimiento m) {
        if (m.getFecha() == null) {
            m.setFecha(LocalDateTime.now());
        }
        return repository.save(m);
    }

    public List<Movimiento> getByHerramienta(Long herramientaId) {
        return repository.findByHerramientaId(herramientaId);
    }

    public List<Movimiento> getByRangoFechas(LocalDateTime start, LocalDateTime end) {
        return repository.findByFechaBetween(start, end);
    }
}
