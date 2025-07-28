package com.alex.controller;

import java.util.List;

import com.alex.models.Categoria;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alex.service.ICategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private ICategoriasService categoriasService;

    @GetMapping("/index")
    public String mostrarIndex(Model model, Pageable pageable) {
        Page<Categoria> categorias = categoriasService.buscarTodas(pageable);
        model.addAttribute("categorias", categorias);
        return "categorias/listCategorias";
    }

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
        Page<Categoria> lista = categoriasService.buscarTodas(page);
        model.addAttribute("categorias", lista);
        return "categorias/listCategorias";
    }

    @GetMapping("/create")
    public String crear(@ModelAttribute Categoria categoria) {
        return "categorias/formCategoria";
    }

    @PostMapping("/save")
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "categorias/formCategoria";
        }

        categoriasService.guardar(categoria);
        attributes.addFlashAttribute("msg", "Categoria guardada con exito");
        return "redirect:/categorias/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idCategoria, Model model) {
        Categoria categoria = categoriasService.buscarPorId(idCategoria);
        model.addAttribute("categoria", categoria);
        return "categorias/formCategoria";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {

        categoriasService.eliminar(idVacante);
        attributes.addFlashAttribute("msg", "categoria eliminada");

        return "redirect:/categorias/index";
    }

}
