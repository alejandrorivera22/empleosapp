package com.alex.service.db;

import com.alex.models.Solicitud;
import com.alex.models.Usuario;
import com.alex.models.Vacante;
import com.alex.repository.Vacantesrepository;
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
import static org.mockito.Mockito.times;

class VacantesServiceJpaTest extends ServiceSpec{

    @Mock
    Vacantesrepository vacantesrepository;

    @InjectMocks
    VacantesServiceJpa vacantesServiceMock;

    Vacante vacante1;
    Vacante vacante2;

    @BeforeEach
    void setUp() {
        vacante1 = new Vacante();
        vacante1.setId(1);
        vacante1.setNombre("Vacante 1");
        vacante1.setDetalles("Detalles 1");
        vacante1.setDescripcion("Descripcion 1");
        vacante1.setEstatus("Aprobada");
        vacante1.setDestacado(1);

        vacante2 = new Vacante();
        vacante2.setId(2);
        vacante2.setNombre("Vacante 2");
        vacante2.setDetalles("Detalles 2");
        vacante2.setDescripcion("Descripcion 2");
    }

    @Test
    @DisplayName("Debería guardar una vacante")
    void guardar_DeberiaGuardarVacante() {
        when(vacantesrepository.findAll())
                .thenReturn(List.of(vacante1))     // Primera llamada
                .thenReturn(List.of(vacante1, vacante2)); // Segunda llamada

        assertEquals(1, vacantesServiceMock.buscarTodas().size());
        vacantesServiceMock.guardar(vacante2);
        assertEquals(2, vacantesServiceMock.buscarTodas().size());
    }

    @Test
    @DisplayName("Debería retornar todos las vacantes")
    void buscarTodas_DeberiaRetornarListaVacantes() {
        when(vacantesrepository.findAll()).thenReturn(List.of(vacante1, vacante2));
        List<Vacante> vacantes = vacantesServiceMock.buscarTodas();
        assertEquals(2, vacantes.size());
    }

    @Test
    @DisplayName("Deberia retornar las vacantes paginadas")
    void buscarTodasPaginado_DeberiaRetornarPaginaDeVacantes() {
        Page<Vacante> pagina = new PageImpl<>(List.of(vacante1));
        Pageable pageable = mock(Pageable.class);

        when(vacantesrepository.findAll(pageable)).thenReturn(pagina);

        Page<Vacante> resultado = vacantesServiceMock.buscarTodas(pageable);

        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Debería retornar una vacante por ID")
    void buscarPorId_DeberiaRetornarVacante() {
        when(vacantesrepository.findById(1)).thenReturn(Optional.of(vacante1));
        Vacante vacante = vacantesServiceMock.buscarPorId(1);
        assertNotNull(vacante);
        assertEquals(vacante1.getId(), vacante.getId());
        assertEquals(vacante1.getDescripcion(), vacante.getDescripcion());
    }

    @Test
    @DisplayName("Debería retornar lista de vacantes destacada")
    void buscarDestacadas_DeberiaRetornarVacanteDestacada() {
        when(vacantesrepository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada"))
                .thenReturn(List.of(vacante1));
        List<Vacante> resultado = vacantesServiceMock.buscarDestacadas();
        assertNotNull(resultado);
        assertEquals(vacante1.getId(), resultado.get(0).getId());
        assertEquals(vacante1.getDestacado(), resultado.get(0).getDestacado());
        assertEquals(vacante1.getEstatus(), resultado.get(0).getEstatus());
    }

    @Test
    void eliminar() {
        when(vacantesrepository.findAll())
                .thenReturn(List.of(vacante1, vacante2))
                .thenReturn(List.of(vacante1));
        List<Vacante> vacantesAntes = vacantesServiceMock.buscarTodas();
        assertEquals(2, vacantesAntes.size());

        int id = vacantesAntes.get(0).getId();

        vacantesServiceMock.eliminar(id);

        List<Vacante> vacantesDespues = vacantesServiceMock.buscarTodas();
        assertEquals(1, vacantesDespues.size());
        verify(vacantesrepository, times(1)).deleteById(anyInt());
    }

}