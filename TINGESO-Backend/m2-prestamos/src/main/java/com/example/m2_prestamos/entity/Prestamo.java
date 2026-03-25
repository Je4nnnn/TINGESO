package com.example.m2_prestamos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private Long herramientaId;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaPactada;
    private LocalDateTime fechaDevolucion;
    private String estado; // VIGENTE, DEVUELTO
    private Double multaAtraso;
    private Double cargoPorDano;
}
