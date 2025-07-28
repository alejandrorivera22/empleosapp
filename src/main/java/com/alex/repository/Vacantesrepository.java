package com.alex.repository;


import com.alex.models.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface Vacantesrepository extends JpaRepository<Vacante, Integer> {
    public List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado, String estatus);
}
