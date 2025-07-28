package com.alex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alex.models.Usuario;
import com.alex.service.IUsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

     @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {

        List<Usuario> usuarios = usuarioService.buscarTodos();
        model.addAttribute("usuarios", usuarios);

        return "usuarios/listUsuarios";
    }

    @PostMapping("/delete/{id}")
  public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		    	
		// Eliminamos el usuario
    	usuarioService.eliminar(idUsuario);			
		attributes.addFlashAttribute("msg", "El usuario fue eliminado!.");
		return "redirect:/usuarios/index";
	}
    
    
    
}
