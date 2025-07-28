package com.alex.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.models.Usuario;
import com.alex.repository.UsuariosRepository;
import com.alex.service.IUsuarioService;

@Service
public class UsuarioServicsJpa implements IUsuarioService{

    @Autowired
    private UsuariosRepository usuariosRepo;

    @Override
    public void guardar(Usuario usuario) {
       usuariosRepo.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
       usuariosRepo.deleteById(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
       return usuariosRepo.findAll();
    }

   @Override
   public Usuario buscarPorUsernam(String username) {
      return usuariosRepo.findByUsername(username);
   }

    
}
