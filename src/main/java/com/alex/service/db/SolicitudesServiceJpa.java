package com.alex.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alex.models.Solicitud;
import com.alex.repository.SolicitudesRepository;
import com.alex.service.ISolicitudesService;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService{

    @Autowired
    private SolicitudesRepository repository;

    @Override
    public void guardar(Solicitud soli) {
        repository.save(soli);
    }

    @Override
    public void eliminar(Integer id) {
      repository.deleteById(id);
    }

    @Override
    public List<Solicitud> buscarTodas() {
     return repository.findAll();
    }

    @Override
    public Solicitud buscarPorId(Integer id) {
       Optional<Solicitud> solicitud = repository.findById(id);
       if (solicitud.isPresent()) {
            return solicitud.get();
       }
       return null;
    }

    @Override
    public Page<Solicitud> buscarTodas(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
}
