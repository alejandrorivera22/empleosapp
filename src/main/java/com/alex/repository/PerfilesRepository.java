package com.alex.repository;


import com.alex.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilesRepository extends JpaRepository<Perfil, Integer> {
    
}
