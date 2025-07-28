package com.alex.service;

import java.util.List;

import com.alex.models.Usuario;

public interface IUsuarioService {
    
    void guardar(Usuario usuario);
    void eliminar(Integer id);
    List<Usuario> buscarTodos();
    Usuario buscarPorUsernam(String username);

}
