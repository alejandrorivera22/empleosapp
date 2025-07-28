package com.alex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alex.models.Solicitud;
import com.alex.models.Usuario;
import com.alex.models.Vacante;
import com.alex.service.ISolicitudesService;
import com.alex.service.IUsuarioService;
import com.alex.service.IVacanteService;
import com.alex.util.Utileria;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

    @Value("${empleos.ruta.cv}")
    private String rutaCv;

    @Autowired
    private IVacanteService vacanteService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ISolicitudesService solicitudesService;

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable pageable){
        Page<Solicitud> lista = solicitudesService.buscarTodas(pageable);
        model.addAttribute("solicitudes", lista);

        return "solicitudes/listSolicitudes";
    }

    @GetMapping("/create/{id}")
    public String crear(Solicitud solicitud, @PathVariable Integer id, Model model) {
        Vacante vacante = vacanteService.buscarPorId(id);
        System.out.println("Id vacante ================================ " + id);
        model.addAttribute("vacante", vacante);
        return "solicitudes/formSolicitud";
    }

    @PostMapping("/save")
    public String guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multipart, Authentication  authentication, RedirectAttributes attributes) {

        String username = authentication.getName();

        if (result.hasErrors()) {
            return "/solicitudes/formSolicitud";
        }

        if (! multipart.isEmpty()) {
            String nombreArchivo = Utileria.guardarArchivo(multipart, rutaCv);
            if (nombreArchivo != null) {
                solicitud.setArchivo(nombreArchivo);
            }
        }

        Usuario usuario = usuarioService.buscarPorUsernam(username);
        solicitud.setUsuario(usuario);

        solicitudesService.guardar(solicitud);
        attributes.addFlashAttribute("msg", "Gracias por enviar tu solicitud CV!");

        System.out.println("solicitud: " + solicitud);
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attributes){
        solicitudesService.eliminar(id);
        attributes.addFlashAttribute("msg", "La solicitud fue eliminada");
        return "redirect:/solicitudes/indexPaginate";
    }

}
