package com.alex.controller;

import com.alex.models.Categoria;
import com.alex.security.DataBaseWebSecurity;
import com.alex.service.ICategoriasService;
import com.alex.service.db.CategoriaServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriasController.class)
class CategoriasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ICategoriasService categoriasService;

    private static final String RESOURCE_PATH = "/categorias";

    final static String ADMINISTRADIOR = "ADMINISTRADIOR";


    Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Categoria");
        categoria.setDescripcion("Descripcion");
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("Deberia retornar vista con categorias")
    void mostrarIndex_DeberiaRetornarVistaConCategorias() throws Exception {
        String uri = RESOURCE_PATH + "/" + "index";
        Page<Categoria> page = new PageImpl<>(List.of(categoria));

        when(categoriasService.buscarTodas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/listCategorias"))
                .andExpect(model().attributeExists("categorias"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("Deberia retornar vista con categorias")
    void mostrarIndexPaginado_DeberiaRetornarVistaConCategorias() throws Exception {
        String uri = RESOURCE_PATH + "/" + "indexPaginate";
        Page<Categoria> page = new PageImpl<>(List.of(categoria));

        when(categoriasService.buscarTodas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/listCategorias"))
                .andExpect(model().attributeExists("categorias"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("deberia mostrar el formulario vacío")
    void crear_deberiaRetornarVistaFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create";

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/formCategoria"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("deberia redirigir con datos válidos")
    void guardar_deberiaGuardarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        mockMvc.perform(post(uri)
                        .param("id", "1")
                        .param("nombre", "Categoria")
                        .param("descripcion", "Descripcion")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias/index"));
    }

    @Test
    @WithMockUser(roles = ADMINISTRADIOR)
    @DisplayName("deberia mostrar datos del horario")
    void editar_deberiaRetornarFormularioConDatos() throws Exception {
        String uri = RESOURCE_PATH + "/" + "edit" + "/" + 1;
        when(categoriasService.buscarPorId(1)).thenReturn(categoria);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/formCategoria"))
                .andExpect(model().attributeExists("categoria"));
    }

    @Test
    @WithMockUser(roles = ADMINISTRADIOR)
    @DisplayName("deberia eliminar y redirigir")
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "delete" + "/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias/index"));
    }
}