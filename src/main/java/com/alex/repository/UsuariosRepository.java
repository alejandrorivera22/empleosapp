package com.alex.repository;


import com.alex.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);
    
}
