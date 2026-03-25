package com.example.m4_tarifas.repository;

import com.example.m4_tarifas.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByTipo(String tipo);
}
