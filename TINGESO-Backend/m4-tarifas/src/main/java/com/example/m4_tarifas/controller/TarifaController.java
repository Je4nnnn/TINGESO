package com.example.m4_tarifas.controller;

import com.example.m4_tarifas.entity.Tarifa;
import com.example.m4_tarifas.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService service;

    @GetMapping
    public ResponseEntity<List<Tarifa>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<Tarifa> getByTipo(@PathVariable String tipo) {
        Tarifa t = service.getByTipo(tipo);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Tarifa> saveOrUpdate(@RequestBody Tarifa tarifa) {
        return ResponseEntity.ok(service.saveOrUpdate(tarifa));
    }
}
