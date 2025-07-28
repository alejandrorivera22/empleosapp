package com.alex.controller;

import com.alex.service.IUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuariosController.class)
class UsuariosControllerTest {

    @Autowired
    MockMvc mockMvc;

    static final String RESOURCE_PATH = "/usuarios";
    final static String ADMINISTRADIOR = "ADMINISTRADIOR";

    @MockBean
    IUsuarioService usuarioService;

    @Test
    @DisplayName("debería mostrar lista de usuarios")
    @WithMockUser(roles = ADMINISTRADIOR)
    void mostrarIndex_deberiaMostrarLista() throws Exception {
        String uri = RESOURCE_PATH + "/index";
        when(usuarioService.buscarTodos()).thenReturn(List.of());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/listUsuarios"))
                .andExpect(model().attributeExists("usuarios"));
    }

    @Test
    @DisplayName("debería eliminar usuario y redirigir")
    @WithMockUser(roles = ADMINISTRADIOR)
    void eliminar_deberiaEliminarUsuario() throws Exception {
        String uri = RESOURCE_PATH + "/delete/" + 1;
        mockMvc.perform(post(uri).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/index"))
                .andExpect(flash().attributeExists("msg"));

        verify(usuarioService).eliminar(1);
    }
}