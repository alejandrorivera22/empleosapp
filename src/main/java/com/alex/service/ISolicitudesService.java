package com.alex.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alex.models.Solicitud;

public interface ISolicitudesService {
    
    void guardar(Solicitud soli);
    void eliminar(Integer id);
    List<Solicitud> buscarTodas();
    Solicitud buscarPorId(Integer id);
    Page<Solicitud> buscarTodas(Pageable pageable);
}
