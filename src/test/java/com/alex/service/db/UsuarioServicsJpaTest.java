package com.alex.service.db;

import com.alex.models.Solicitud;
import com.alex.models.Usuario;
import com.alex.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UsuarioServicsJpaTest extends ServiceSpec{

    @Mock
    UsuariosRepository usuariosRepository;

    @InjectMocks
    UsuarioServicsJpa usuarioServicsMock;

    Usuario usuario1;
    Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setNombre("Usuario 1");
        usuario1.setPassword("Password1");
        usuario1.setUsername("username1");

        usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setNombre("Usuario 2");
        usuario2.setPassword("Password2");
        usuario2.setUsername("username2");

    }

    @Test
    @DisplayName("Debería guardar un usuario")
    void guardar_DeberiaGuardarUsusario() {
        when(usuariosRepository.findAll())
                .thenReturn(List.of(usuario1))     // Primera llamada
                .thenReturn(List.of(usuario1, usuario2)); // Segunda llamada

        assertEquals(1, usuarioServicsMock.buscarTodos().size());
        usuarioServicsMock.guardar(usuario2);
        assertEquals(2, usuarioServicsMock.buscarTodos().size());
    }

    @Test
    @DisplayName("Debería retornar todos los usuarios")
    void buscarTodos_DeberiaRetornarListaUsuarios() {
        when(usuariosRepository.findAll()).thenReturn(List.of(usuario1, usuario2));
        List<Usuario> usuarios = usuarioServicsMock.buscarTodos();
        assertEquals(2, usuarios.size());
    }

    @Test
    @DisplayName("Debería retornar un usuario por username")
    void buscarPorUsernam_DeberiaRetornarUsuario() {
        when(usuariosRepository.findByUsername("username1")).thenReturn(usuario1);
        Usuario usuario = usuarioServicsMock.buscarPorUsernam("username1");
        assertNotNull(usuario);
        assertEquals(usuario1.getId(), usuario.getId());
        assertEquals(usuario1.getUsername(), usuario.getUsername());

    }

    @Test
    void eliminar() {
        when(usuariosRepository.findAll())
                .thenReturn(List.of(usuario1, usuario2))
                .thenReturn(List.of(usuario1));
        List<Usuario> usuariosAntes = usuarioServicsMock.buscarTodos();
        assertEquals(2, usuariosAntes.size());

        int id = usuariosAntes.get(0).getId();

        usuarioServicsMock.eliminar(id);

        List<Usuario> usuariosDespues = usuarioServicsMock.buscarTodos();
        assertEquals(1, usuariosDespues.size());
        verify(usuariosRepository, times(1)).deleteById(anyInt());
    }

}