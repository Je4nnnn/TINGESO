package com.example.m5_kardex.controller;

import com.example.m5_kardex.entity.Movimiento;
import com.example.m5_kardex.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/kardex")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @PostMapping
    public ResponseEntity<Movimiento> registrar(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(service.registrar(movimiento));
    }

    @GetMapping("/herramienta/{herramientaId}")
    public ResponseEntity<List<Movimiento>> getByHerramienta(@PathVariable Long herramientaId) {
        return ResponseEntity.ok(service.getByHerramienta(herramientaId));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Movimiento>> getByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(service.getByRangoFechas(start, end));
    }
}
