package com.alex.controller;

import com.alex.models.Perfil;
import com.alex.models.Usuario;
import com.alex.models.Vacante;
import com.alex.service.ICategoriasService;
import com.alex.service.IUsuarioService;
import com.alex.service.IVacanteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class HomeController {

    @Autowired
    ICategoriasService categoriasService;

    @Autowired
    IVacanteService vacanteService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String mostrarHome(Model model) {
        return "home";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session) {
        String username = auth.getName();
        System.out.println("Nombre de usuario" + username);
        for (GrantedAuthority rol : auth.getAuthorities()) {
            System.out.println("Rol: " + rol.getAuthority());
        }

        if (session.getAttribute("usuario") == null) {
            Usuario usuario = usuarioService.buscarPorUsernam(username);
            usuario.setPassword(null);
            System.out.println("Usuario " + usuario);
            session.setAttribute("usuario", usuario);
        }

        return "redirect:/";
    }

    @GetMapping("/tabla")
    public String mostrarTabla(Model model) {
        List<Vacante> lista = vacanteService.buscarTodas();
        model.addAttribute("vacantes", lista);

        return "tabla";
    }

    @GetMapping("/detalle")
    public String mostrarDetalle(Model model) {
        Vacante vacante = new Vacante();
        vacante.setNombre("Ingeniero de comunicaciones");
        vacante.setDescripcion("Se solicita ingeniero para dar soporte a internet");
        vacante.setFecha(new Date());
        vacante.setSalario(9600d);
        model.addAttribute("vacante", vacante);

        return "detalle";
    }

    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<String> lista = new LinkedList<>();
        lista.add("Ingeniero de sistemas");
        lista.add("Auxiliar contable");
        lista.add("Vendedor");
        lista.add("Arquitecto");

        model.addAttribute("empleos", lista);

        return "listado";
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable String texto) {
        return texto + " Encriptado en Bcript " + passwordEncoder.encode(texto);
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        return "usuarios/formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {

        String pwdPlano = usuario.getPassword();
        String pwEncriptado = passwordEncoder.encode(pwdPlano);
        usuario.setPassword(pwEncriptado);
        usuario.setEstatus(1);
        usuario.setFechaRegistro(new Date());

        Perfil perfil = new Perfil();
        usuario.agregar(perfil);
        usuarioService.guardar(usuario);
        attributes.addFlashAttribute("msg", "UsuarioGuardado");
        return "redirect:/usuarios/index";
    }

    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
        System.out.println("Buscando por : " + vacante);

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion",
                ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Vacante> example = Example.of(vacante, matcher);
        List<Vacante> lista = vacanteService.buscarByExample(example);
        model.addAttribute("vacantes", lista);
        return "home";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "formLogin";
    }

    @GetMapping("/logout")
    public String getMethodName(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }
    

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute()
    public void setGenericos(Model model) {
        Vacante vacabteSerch = new Vacante();
        vacabteSerch.reset();
        model.addAttribute("vacantes", vacanteService.buscarDestacadas());
        model.addAttribute("categorias", categoriasService.buscarTodas());
        model.addAttribute("search", vacabteSerch);
    }

}
