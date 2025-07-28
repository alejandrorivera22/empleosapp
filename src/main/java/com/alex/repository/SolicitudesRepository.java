package com.alex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.models.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer>{
    
}
