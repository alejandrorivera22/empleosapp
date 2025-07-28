package com.alex.service.db;

import com.alex.models.Categoria;
import com.alex.repository.CategoriasRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CategoriaServiceJpaTest extends ServiceSpec{

    @Mock
    CategoriasRepository categoriasRepository;

    @InjectMocks
    CategoriaServiceJpa categoriaServiceMock;

    Categoria categoria1;
    Categoria categoria2;

    @BeforeEach
    void setUp() {
        categoria1 = new Categoria();
        categoria1.setId(1);
        categoria1.setDescripcion("Categoria 1");
        categoria1.setNombre("Categoria1");

        categoria2 = new Categoria();
        categoria2.setId(2);
        categoria2.setDescripcion("Categoria 2");
        categoria2.setNombre("Categoria2");
    }

    @Test
    @DisplayName("Debería guardar una categoria")
    void guardar_DeberiaGuardarCategoria() {
        when(categoriasRepository.findAll())
                .thenReturn(List.of(categoria1))     // Primera llamada
                .thenReturn(List.of(categoria1, categoria2)); // Segunda llamada

        assertEquals(1, categoriaServiceMock.buscarTodas().size());
        categoriaServiceMock.guardar(categoria2);
        assertEquals(2, categoriaServiceMock.buscarTodas().size());
    }

    @Test
    @DisplayName("Debería retornar todos las catergorias")
    void buscarTodas_DeberiaRetornarListaCategorias() {
        when(categoriasRepository.findAll()).thenReturn(List.of(categoria1, categoria2));
        List<Categoria> categorias = categoriaServiceMock.buscarTodas();
        assertEquals(2, categorias.size());
    }

    @Test
    @DisplayName("Deberia retornar las categorias paginadas")
    void buscarTodasPaginado_DeberiaRetornarPaginaDeCategorias() {
        Page<Categoria> pagina = new PageImpl<>(List.of(categoria1));
        Pageable pageable = mock(Pageable.class);

        when(categoriasRepository.findAll(pageable)).thenReturn(pagina);

        Page<Categoria> resultado = categoriaServiceMock.buscarTodas(pageable);

        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Debería retornar una categoria por ID")
    void buscarPorId_DeberiaRetornarCategoria() {
        when(categoriasRepository.findById(1)).thenReturn(Optional.of(categoria1));
        Categoria categoria = categoriaServiceMock.buscarPorId(1);
        assertNotNull(categoria);
        assertEquals(categoria1.getId(), categoria.getId());
        assertEquals(categoria1.getNombre(), categoria.getNombre());
    }

    @Test
    @DisplayName("Debería eliminar una categoria por ID")
    void eliminar() {
        when(categoriasRepository.findAll())
                .thenReturn(List.of(categoria1, categoria2))
                .thenReturn(List.of(categoria1));
        List<Categoria> categoriasAntes = categoriaServiceMock.buscarTodas();
        assertEquals(2, categoriasAntes.size());

        int id = categoriasAntes.get(0).getId();

        categoriaServiceMock.eliminar(id);

        List<Categoria> categoriasDespues = categoriaServiceMock.buscarTodas();
        assertEquals(1, categoriasDespues.size());
        verify(categoriasRepository, times(1)).deleteById(anyInt());
    }


}