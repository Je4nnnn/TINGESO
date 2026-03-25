package com.example.m1_inventario.repository;

import com.example.m1_inventario.entity.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {
    List<Herramienta> findByEstado(String estado);
}
