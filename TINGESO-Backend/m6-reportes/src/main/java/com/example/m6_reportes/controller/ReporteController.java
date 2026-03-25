package com.example.m6_reportes.controller;

import com.example.m6_reportes.dto.*;
import com.example.m6_reportes.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping("/prestamos-activos")
    public ResponseEntity<List<PrestamoDTO>> getPrestamosActivos() {
        return ResponseEntity.ok(service.getPrestamosActivos());
    }

    @GetMapping("/clientes-atrasados")
    public ResponseEntity<List<ClienteDTO>> getClientesConAtrasos() {
        return ResponseEntity.ok(service.getClientesConAtrasos());
    }

    @GetMapping("/ranking-herramientas")
    public ResponseEntity<List<RankingDTO>> getRankingHerramientas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(service.getRankingHerramientas(start, end));
    }
}
