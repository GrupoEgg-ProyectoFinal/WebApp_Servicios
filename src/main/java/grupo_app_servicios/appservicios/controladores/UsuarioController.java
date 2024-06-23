package grupo_app_servicios.appservicios.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //MODIFICAR USUARIO GET
    @GetMapping("/modificarUsuarioS/{id}")
    public String modificarUsuario(@PathVariable String id, Model modelo) {
        /* UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(UUID.fromString(id)); */
        UsuarioDTO usuarioDTO = usuarioServicio.encontrarPorId(UUID.fromString(id));
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        return "modificarUsuario.html";
    }
    
   //MODIFICAR USUARIO POST
    @PostMapping("/modificarUsuario/{id}")
    public String modificarUsuario(@PathVariable String id, @ModelAttribute UsuarioDTO usuarioDTO) {
        try {
            usuarioDTO.setId(UUID.fromString(id));
            usuarioServicio.modificarUsuario(usuarioDTO);
            return "redirect:/dashboard";
        } catch (MiExcepcion e) {
            // Manejo de excepción si es necesario
            return "redirect:/error"; // Redirigir a una página de error
        }
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id) {
        usuarioServicio.eliminarUsuario(UUID.fromString(id));
        return "redirect:/formulario";
    }

    @GetMapping("/listar")
    public String listarUsuariosVista(ModelMap model) {
        List<UsuarioDTO> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listar.html";
    }

    // MODIFICAR ROL 
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(UUID.fromString(id));
        return "redirect:/dashboard";
    }




}
