package com.alex.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alex.models.Categoria;
import com.alex.repository.CategoriasRepository;
import com.alex.service.ICategoriasService;

@Service
@Primary
public class CategoriaServiceJpa implements ICategoriasService{

    @Autowired
    CategoriasRepository categoriasRepo;

    @Override
    public void guardar(Categoria categoria) {
       categoriasRepo.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return categoriasRepo.findAll(); 
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {

       Optional<Categoria> categorOptional = categoriasRepo.findById(idCategoria);
       if (categorOptional.isPresent()) {
           return categorOptional.get();
       }
       return null;
    }

    @Override
    public void eliminar(Integer id) {
      categoriasRepo.deleteById(id);
    }


    @Override
    public Page<Categoria> buscarTodas(Pageable page) {
      return categoriasRepo.findAll(page);
    }

   
    
}
