package com.alex.controller;

import com.alex.models.Categoria;
import com.alex.models.Solicitud;
import com.alex.models.Usuario;
import com.alex.models.Vacante;
import com.alex.service.ISolicitudesService;
import com.alex.service.IUsuarioService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudesController.class)
class SolicitudesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVacanteService vacanteService;

    @MockBean
    private IUsuarioService usuarioService;

    @MockBean
    private ISolicitudesService solicitudesService;

    private static final String RESOURCE_PATH = "/solicitudes";
    final static String ADMINISTRADIOR = "ADMINISTRADIOR";

    Vacante vacante;
    Usuario usuario;
    Categoria categoria;
    Solicitud solicitud;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setUsername("usuario");

        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Categoria1");

        vacante = new Vacante();
        vacante.setCategoria(categoria);

        solicitud = new Solicitud();
        solicitud.setId(1);
        solicitud.setArchivo("archivoCV");
        solicitud.setUsuario(usuario);
        solicitud.setComentarios("Comentarios");
        solicitud.setVacante(vacante);
        solicitud.setFecha(LocalDate.now());

    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("Deberia retornar vista con solicitudes")
    void mostrarIndexPaginado_DeberiaRetornarVistaConSolicitudes() throws Exception {
        String uri = RESOURCE_PATH + "/" + "indexPaginate";
        Page<Solicitud> page = new PageImpl<>(List.of(solicitud));

        when(solicitudesService.buscarTodas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("solicitudes/listSolicitudes"))
                .andExpect(model().attributeExists("solicitudes"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("deberia mostrar el formulario vacío")
    void crear_deberiaRetornarVistaFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create/" + 1;

        when(vacanteService.buscarPorId(1)).thenReturn(vacante);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("solicitudes/formSolicitud"));
    }

    @Test
    @WithMockUser(roles = {"USUARIO"})
    @DisplayName("deberia redirigir con datos válidos")
    void guardar_deberiaGuardarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        MockMultipartFile archivoCV = new MockMultipartFile(
                "archivoCV",
                "soicitud.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "contenido de imagen falso".getBytes()
        );
        when(usuarioService.buscarPorUsernam("usuario")).thenReturn(usuario);

        mockMvc.perform(multipart(uri)
                        .file(archivoCV)
                        .param("id", "1")
                        .param("usuario.nombre", "usuario")
                        .param("comentarios", "Comentarios")
                        .param("vacante.id", "1")
                        .param("fecha", "2024-07-27")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(roles = {ADMINISTRADIOR})
    @DisplayName("debería eliminar a solicitud")
    void eliminar() throws Exception {
        String uri = RESOURCE_PATH + "/delete/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/solicitudes/indexPaginate"))
                .andExpect(flash().attribute("msg", "La solicitud fue eliminada"));

        verify(solicitudesService).eliminar(1);
    }
}