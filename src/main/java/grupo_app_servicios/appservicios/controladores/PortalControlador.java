package grupo_app_servicios.appservicios.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.Usuario;
import grupo_app_servicios.appservicios.servicios.OPCIONAL;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    OPCIONAL opcional;

    @Autowired
    UsuarioServicio uServicio;

    @GetMapping("/") // Acá es donde realizamos el mapeo
    public String index(ModelMap modelo) {
        List<ProveedorDTO> proveedores = opcional.listarProveedores();

        modelo.addAttribute("proveedores", proveedores);
        return "index.html"; // Acá es que retornamos con el método.
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        // Inicializa un nuevo objeto UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        // Agrega el objeto usuarioDTO al modelo
        
        model.addAttribute("usuarioDTO", usuarioDTO);
        model.addAttribute("contrasena2", ""); // verificar que funcione
        return "registro.html";
    }

    @GetMapping("/login")
    public String inicioSesion() {
        return "iniciarSesion.html";
    }

    @GetMapping("/loginProveedor")
    public String inicioSesionProveedor() {
        return "sesionProveedor.html";
    }
    
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, String contrasena2) {
        uServicio.crearUsuario(usuarioDTO, contrasena2);
        return "redirect:/";
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRol('ROL_USER', 'ROL_ADMIN')")
    public String inicio(HttpSession session) {
        Usuario loguedUser = (Usuario) session.getAttribute("usuarioEnSesion");
        String role = loguedUser.getRol().toString();

        if (role.equals("ADMIN")) return "redirect:/dashboard";
        return "index.html"; // después cambiarlo por la vista de perfil de usuario
    }
}
