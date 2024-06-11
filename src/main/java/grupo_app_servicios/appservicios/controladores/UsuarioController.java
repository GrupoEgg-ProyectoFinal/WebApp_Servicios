package grupo_app_servicios.appservicios.controladores;

import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
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


// @Controller
// @RequestMapping("/usuario")
// // Accedo al metodo
// public class UsuarioControlador {
//     @Autowired
//     private UsuarioServicio usuarioServicio;
//      @GetMapping("/crear")
//     public String mostrarFormularioCreacion(Model model) {
//         model.addAttribute("usuario", new UsuarioDTO());
//         return "cargarUsuario.html";
//     }

//     // mapeo motodo
//     @PostMapping("/crear")
//     public String crearUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
//         try {
//             usuarioServicio.crearUsuario(usuarioDTO);
//             // model.addAttribute("successMessage", "Usuario creado exitosamente");
//             return "cargarUsuario.html"; // Redirige a una lista de usuarios o a una página de éxito
//         } catch (Exception e) {
//             model.addAttribute("errorMessage", "Error al crear el usuario: " + e.getMessage());
//             return "cargarUsuario.html"; // Vuelve a mostrar el formulario con un mensaje de error
//         }
//     }
// }
// @PostMapping("/registro")
// public String registro(@RequestParam("nombre") String nombre, ModelMap
// modelo) {
// try {
// editorialServicio.crearEditorial(nombre);
// modelo.put("exito", "La ediotrial fue cargado con exito");
// } catch (MiExcepcion ex) {
// Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null,
// ex);
// modelo.put("error", ex.getMessage());
// return "editorial_form.html";
// }

// return "index.html";
// }
