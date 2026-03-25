package com.example.m4_tarifas.service;

import com.example.m4_tarifas.entity.Tarifa;
import com.example.m4_tarifas.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository repository;

    public List<Tarifa> getAll() {
        return repository.findAll();
    }

    public Tarifa getByTipo(String tipo) {
        return repository.findByTipo(tipo).orElse(null);
    }
    
    public Tarifa saveOrUpdate(Tarifa tarifa) {
        Optional<Tarifa> opt = repository.findByTipo(tarifa.getTipo());
        if (opt.isPresent()) {
            Tarifa t = opt.get();
            t.setMonto(tarifa.getMonto());
            return repository.save(t);
        }
        return repository.save(tarifa);
    }
}
