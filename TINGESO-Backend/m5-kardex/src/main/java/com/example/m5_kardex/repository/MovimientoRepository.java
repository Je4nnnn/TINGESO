package com.example.m5_kardex.repository;

import com.example.m5_kardex.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByHerramientaId(Long herramientaId);
    List<Movimiento> findByFechaBetween(LocalDateTime start, LocalDateTime end);
}
