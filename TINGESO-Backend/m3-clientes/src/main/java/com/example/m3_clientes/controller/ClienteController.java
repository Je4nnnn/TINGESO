package com.example.m3_clientes.controller;

import com.example.m3_clientes.entity.Cliente;
import com.example.m3_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        Cliente c = service.getById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Cliente> getByRut(@PathVariable String rut) {
        Cliente c = service.getByRut(rut);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.save(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Cliente> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        Cliente c = service.cambiarEstado(id, estado);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }
}
