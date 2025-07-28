package com.alex.service;

import com.alex.models.Vacante;

import java.util.List;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IVacanteService {

    List<Vacante> buscarTodas();

    Vacante buscarPorId(Integer idVacante);

    void guardar(Vacante vacante);

    List<Vacante> buscarDestacadas();

    void eliminar(Integer id);

    List<Vacante> buscarByExample(Example<Vacante> example);

    Page<Vacante> buscarTodas(Pageable page);

}
