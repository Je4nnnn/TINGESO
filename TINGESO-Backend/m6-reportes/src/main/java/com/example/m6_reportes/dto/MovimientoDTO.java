package com.example.m6_reportes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoDTO {
    private Long id;
    private String tipo;
    private Long herramientaId;
    private LocalDateTime fecha;
}
