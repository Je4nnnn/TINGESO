package com.example.m3_clientes.service;

import com.example.m3_clientes.entity.Cliente;
import com.example.m3_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    public Cliente getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public Cliente getByRut(String rut) {
        return repository.findByRut(rut).orElse(null);
    }

    public Cliente save(Cliente c) {
        if (c.getNombre() == null || c.getRut() == null || c.getCorreo() == null || c.getTelefono() == null) {
            throw new IllegalArgumentException("Nombre, RUT, correo y teléfono son obligatorios");
        }
        if (c.getEstado() == null) {
            c.setEstado("ACTIVO");
        }
        return repository.save(c);
    }

    public Cliente cambiarEstado(Long id, String estado) {
        Optional<Cliente> opt = repository.findById(id);
        if (opt.isPresent()) {
            Cliente c = opt.get();
            c.setEstado(estado);
            return repository.save(c);
        }
        return null;
    }
}
