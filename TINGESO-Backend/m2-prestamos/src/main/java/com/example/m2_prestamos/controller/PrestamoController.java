package com.example.m2_prestamos.controller;

import com.example.m2_prestamos.dto.DevolucionRequestDTO;
import com.example.m2_prestamos.entity.Prestamo;
import com.example.m2_prestamos.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService service;

    @GetMapping
    public ResponseEntity<List<Prestamo>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<Prestamo>> getVigentes() {
        return ResponseEntity.ok(service.getActivos());
    }
    
    @GetMapping("/atrasados")
    public ResponseEntity<List<Prestamo>> getAtrasados() {
        return ResponseEntity.ok(service.getAtrasados());
    }

    @PostMapping
    public ResponseEntity<?> registrarPrestamo(@RequestBody Prestamo prestamo) {
        try {
            return ResponseEntity.ok(service.registrarPrestamo(prestamo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/devolucion")
    public ResponseEntity<?> registrarDevolucion(@RequestBody DevolucionRequestDTO req) {
        try {
            return ResponseEntity.ok(service.registrarDevolucion(req));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
