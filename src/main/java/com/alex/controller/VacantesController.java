package com.alex.controller;

import com.alex.models.Vacante;
import com.alex.service.ICategoriasService;
import com.alex.service.IVacanteService;
import com.alex.util.Utileria;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    @Value("${empleos.ruta.images}")
    private String ruta;

    @Autowired
    private IVacanteService vacanteService;

    @Autowired
    private ICategoriasService service;

    @GetMapping("/index")
    public String mostrarIndex(Model model){
        List<Vacante> vacantes = vacanteService.buscarTodas();
        model.addAttribute("vacantes", vacantes);
        return "vacantes/listVacantes";
    }

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable pageable) {
        Page<Vacante> lista = vacanteService.buscarTodas(pageable);
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }
    

    @GetMapping("/create")
    private String crear(Vacante vacante, Model model){
       
        return "vacantes/formVacante";
    }

    @PostMapping("/save")
    public String guardar(@Valid Vacante vacante, BindingResult result, RedirectAttributes attributes,
    @RequestParam("archivoImagen") MultipartFile multipart){
       if(result.hasErrors()){
           return "vacantes/formVacante";
       }

       if (!multipart.isEmpty()) {
            // String ruta = "C:\\empleos\\img-vacantes";
            String nombreImagen = Utileria.guardarArchivo(multipart, ruta);
            if (nombreImagen != null) {//la imagen si se subio
                //procesamos la variable de la imagen
                vacante.setImagen(nombreImagen);
            }
       }

        vacanteService.guardar(vacante);
        attributes.addFlashAttribute("msg", "registro guardado");
        return "redirect:/vacantes/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int id, Model model){
        Vacante vacante = vacanteService.buscarPorId(id);
        model.addAttribute("vacante", vacante);
        return "vacantes/formVacante";

    }


    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante, Model model, RedirectAttributes attributes){
       
        vacanteService.eliminar(idVacante);
        attributes.addFlashAttribute("msg", "Vacante eliminada");

        return "redirect:/vacantes/index";
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model){

        Vacante vacante = vacanteService.buscarPorId(idVacante);

        System.out.println("vacante: " + vacante);
        model.addAttribute("vacante", vacante);
        return "detalle";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("categorias", service.buscarTodas());
    }

    

}
