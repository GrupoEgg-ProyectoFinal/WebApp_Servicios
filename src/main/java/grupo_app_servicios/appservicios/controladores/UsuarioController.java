package grupo_app_servicios.appservicios.controladores;
import org.springframework.stereotype.Controller;

import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UsuarioController {
 
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "formulario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioServicio.crearUsuario(usuarioDTO);
        return "redirect:/formulario";
    }
    @PostMapping("/modificarUsuario/{id}")
    public String modificarUsuario(@PathVariable UUID id, @ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioServicio.modificarUsuario(id, usuarioDTO);
        return "redirect:/formulario";
    }
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable UUID id) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/formulario";
    }
}
