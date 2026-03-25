package com.example.m1_inventario.controller;

import com.example.m1_inventario.entity.Herramienta;
import com.example.m1_inventario.service.HerramientaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/herramientas")
public class HerramientaController {

    @Autowired
    private HerramientaService service;

    @GetMapping
    public ResponseEntity<List<Herramienta>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Herramienta> getById(@PathVariable Long id) {
        Herramienta h = service.getById(id);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Herramienta> save(@RequestBody Herramienta herramienta) {
        try {
            return ResponseEntity.ok(service.save(herramienta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/baja")
    public ResponseEntity<Herramienta> darDeBaja(@PathVariable Long id) {
        Herramienta h = service.darDeBaja(id);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<Herramienta> updateEstado(@PathVariable Long id, @RequestParam String estado) {
        Herramienta h = service.updateEstado(id, estado);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }
}
