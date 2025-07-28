package com.alex.service.db;

import com.alex.models.Solicitud;
import com.alex.repository.SolicitudesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class SolicitudesServiceJpaTest extends ServiceSpec {

    @Mock
    SolicitudesRepository solicitudesRepository;

    @InjectMocks
    SolicitudesServiceJpa solicitudesServiceMock;

    Solicitud solicitud1;
    Solicitud solicitud2;

    @BeforeEach
    void setUp() {
        solicitud1 = new Solicitud();
        solicitud1.setId(1);
        solicitud1.setArchivo("Archivo1");

        solicitud2 = new Solicitud();
        solicitud2.setId(2);
        solicitud2.setArchivo("Archivo2");
    }

    @Test
    @DisplayName("Debería guardar una solicitud")
    void guardar_DeberiaGuardarSolicitud() {
        when(solicitudesRepository.findAll())
                .thenReturn(List.of(solicitud1))     // Primera llamada
                .thenReturn(List.of(solicitud1, solicitud2)); // Segunda llamada

        assertEquals(1, solicitudesServiceMock.buscarTodas().size());
        solicitudesServiceMock.guardar(solicitud2);
        assertEquals(2, solicitudesServiceMock.buscarTodas().size());
    }

    @Test
    @DisplayName("Debería retornar todos las solicitudes")
    void buscarTodas_DeberiaRetornarListaSolicitudes() {
        when(solicitudesRepository.findAll()).thenReturn(List.of(solicitud1, solicitud2));
        List<Solicitud> solicitudes = solicitudesServiceMock.buscarTodas();
        assertEquals(2, solicitudes.size());
    }

    @Test
    @DisplayName("Deberia retornar las solicitudes paginadas")
    void buscarTodasPaginado_DeberiaRetornarPaginaDeSolicitueds() {
        Page<Solicitud> pagina = new PageImpl<>(List.of(solicitud1));
        Pageable pageable = mock(Pageable.class);

        when(solicitudesRepository.findAll(pageable)).thenReturn(pagina);

        Page<Solicitud> resultado = solicitudesServiceMock.buscarTodas(pageable);

        assertEquals(1, resultado.getTotalElements());
    }


    @Test
    @DisplayName("Debería retornar una solicitud por ID")
    void buscarPorId_DeberiaRetornarSolicitud() {
        when(solicitudesRepository.findById(1)).thenReturn(Optional.of(solicitud1));
        Solicitud solicitud = solicitudesServiceMock.buscarPorId(1);
        assertNotNull(solicitud);
        assertEquals(solicitud1.getId(), solicitud.getId());
        assertEquals(solicitud1.getArchivo(), solicitud.getArchivo());
    }


    @Test
    void eliminar() {
        when(solicitudesRepository.findAll())
                .thenReturn(List.of(solicitud1, solicitud2))
                .thenReturn(List.of(solicitud1));
        List<Solicitud> solicitudesAntes = solicitudesServiceMock.buscarTodas();
        assertEquals(2, solicitudesAntes.size());

        int id = solicitudesAntes.get(0).getId();

        solicitudesServiceMock.eliminar(id);

        List<Solicitud> solicitudesDespues = solicitudesServiceMock.buscarTodas();
        assertEquals(1, solicitudesDespues.size());
        verify(solicitudesRepository, times(1)).deleteById(anyInt());
    }

}