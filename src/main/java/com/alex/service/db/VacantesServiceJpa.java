package com.alex.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alex.models.Vacante;
import com.alex.repository.Vacantesrepository;
import com.alex.service.IVacanteService;

@Service
@Primary
public class VacantesServiceJpa implements IVacanteService{

    @Autowired
    private Vacantesrepository vacantesRepo;

    @Override
    public List<Vacante> buscarTodas() {
       return vacantesRepo.findAll();
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {
       Optional<Vacante> vacanteOptional = vacantesRepo.findById(idVacante);
       if (vacanteOptional.isPresent()) {
        return vacanteOptional.get();
       }
       return null;
    }

    @Override
    public void guardar(Vacante vacante) {
       vacantesRepo.save(vacante);
    }

   @Override
   public List<Vacante> buscarDestacadas() {
     return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
   }

   @Override
   public void eliminar(Integer id) {
     vacantesRepo.deleteById(id);
   }

   @Override
   public List<Vacante> buscarByExample(Example<Vacante> example) {
      return vacantesRepo.findAll(example);
   }

   @Override
   public Page<Vacante> buscarTodas(Pageable page) {
      return vacantesRepo.findAll(page);
   }

  

    
}
