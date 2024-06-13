package grupo_app_servicios.appservicios.controladores;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

//     @GetMapping("/formulario")
//     public String mostrarFormulario(Model model) {
//         model.addAttribute("usuarioDTO", new UsuarioDTO());
//         return "registro";
//     }

//     @PostMapping("/guardarUsuario")
//     public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO) {
//         usuarioServicio.crearUsuario(usuarioDTO);
//         return "redirect:/registro";
//     }


    @PostMapping("/modificarUsuario/{id}")
    public String modificarUsuario(@PathVariable String id, @ModelAttribute UsuarioDTO usuarioDTO) {
        try {
            usuarioDTO.setId(UUID.fromString(id));
            usuarioServicio.modificarUsuario(usuarioDTO);
            return "redirect:/formulario";
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

//     //prueba lista de usiarios Postman OK
// //    @GetMapping("/listar")
// //    @ResponseBody
// //    public List<UsuarioDTO> listarUsuarios() {
// //        return usuarioServicio.listarUsuarios();
// //    }

    //HTML
    @GetMapping("/listar")
    public String listarUsuariosVista(Model model) {
        List<UsuarioDTO> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios.html";
    }

}
