package com.example.m5_kardex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo; // INGRESO, PRESTAMO, DEVOLUCION, BAJA, REPARACION
    private Long herramientaId;
    private LocalDateTime fecha;
    private String usuario;
    private Integer cantidadAfectada;
}
