package com.example.m6_reportes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrestamoDTO {
    private Long id;
    private Long clienteId;
    private Long herramientaId;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaPactada;
    private LocalDateTime fechaDevolucion;
    private String estado;
}
