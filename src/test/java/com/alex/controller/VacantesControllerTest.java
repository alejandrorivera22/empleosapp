package com.alex.controller;

import com.alex.models.Categoria;
import com.alex.models.Vacante;
import com.alex.service.ICategoriasService;
import com.alex.service.IVacanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VacantesController.class)
class VacantesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IVacanteService vacanteService;

    @MockBean
    private ICategoriasService service;

    private static final String RESOURCE_PATH = "/vacantes";
    final static String ADMINISTRADIOR = "ADMINISTRADIOR";

    Categoria categoria;
    Vacante vacante;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);

        vacante = new Vacante();
        vacante.setId(1);
        vacante.setNombre("vacante");
        vacante.setDescripcion("Descripcion vacante");
        vacante.setCategoria(categoria);
        vacante.setFecha(new Date());
        vacante.setDetalles("Detalles");
    }


    @Test
    @WithMockUser(roles = ADMINISTRADIOR)
    void mostrarIndexPaginado() throws Exception {
        String uri = RESOURCE_PATH + "/" + "indexPaginate";
        Page<Vacante> page = new PageImpl<>(List.of(vacante));

        when(vacanteService.buscarTodas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("vacantes/listVacantes"))
                .andExpect(model().attributeExists("vacantes"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("deberia mostrar el formulario vacío")
    void crear_deberiaRetornarVistaFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create";

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("vacantes/formVacante"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    void guardar_deberiaGuardarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        MockMultipartFile archivo  = new MockMultipartFile(
                "archivoImagen",
                "vacante.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "contenido de imagen falso".getBytes()
        );
        mockMvc.perform(multipart(uri)
                        .file(archivo)
                        .param("id", "1")
                        .param("nombre", "vacante")
                        .param("descripcion", "Descripcion")
                        .param("categoria.id", "1")
                        .param("fecha", "27-07-2024")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vacantes/index"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("deberia mostrar datos de la vacante")
    void editar_deberiaRetornarFormularioConDatos() throws Exception {
        String uri = RESOURCE_PATH + "/" + "edit" + "/" + 1;
        when(vacanteService.buscarPorId(1)).thenReturn(vacante);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("vacantes/formVacante"))
                .andExpect(model().attributeExists("vacante"));
    }

    @Test
    @WithMockUser(roles = ADMINISTRADIOR)
    @DisplayName("deberia eliminar y redirigir")
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "delete" + "/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vacantes/index"));
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    @DisplayName("deberia mopstar detalle")
    void verDetalle() throws Exception {
        String uri = RESOURCE_PATH + "/" + "view" + "/" + 1;
        when(vacanteService.buscarPorId(1)).thenReturn(vacante);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("detalle"))
                .andExpect(model().attributeExists("vacante"));

    }

}