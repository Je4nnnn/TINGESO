package com.example.m1_inventario.service;

import com.example.m1_inventario.entity.Herramienta;
import com.example.m1_inventario.repository.HerramientaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class HerramientaService {

    @Autowired
    private HerramientaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Herramienta> getAll() {
        return repository.findAll();
    }

    public Herramienta getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Herramienta save(Herramienta h) {
        if (h.getNombre() == null || h.getCategoria() == null || h.getValorReposicion() == null) {
            throw new IllegalArgumentException("Nombre, categoria y valorReposicion son obligatorios");
        }
        if (h.getEstado() == null) {
            h.setEstado("DISPONIBLE");
        }
        Herramienta saved = repository.save(h);
        
        // Notify Kardex asynchronously or rely on Try-catch
        try {
            // To be implemented when we know exactly M5's API
            // M5 Kardex Movement Type: INGRESO
        } catch (Exception e) {
            // Ignore for now
        }
        
        return saved;
    }

    public Herramienta darDeBaja(Long id) {
        Optional<Herramienta> opt = repository.findById(id);
        if (opt.isPresent()) {
            Herramienta h = opt.get();
            h.setEstado("DADA_DE_BAJA");
            repository.save(h);
            return h;
        }
        return null;
    }
    
    public Herramienta updateEstado(Long id, String estado) {
        Optional<Herramienta> opt = repository.findById(id);
        if (opt.isPresent()) {
            Herramienta h = opt.get();
            h.setEstado(estado);
            return repository.save(h);
        }
        return null;
    }
}
