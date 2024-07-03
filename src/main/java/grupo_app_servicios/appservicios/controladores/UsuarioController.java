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
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.ServicioServicio;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import grupo_app_servicios.appservicios.utilidades.MapeadorDtoAEntidad;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorServicio2 pServicio;
    @Autowired
    private ServicioServicio sServicio;

    // MODIFICAR USUARIO GET
    @GetMapping("/modificarUsuarioS/{id}")
    public String modificarUsuario(@PathVariable String id, Model modelo) {
        /*
         * UsuarioDTO usuarioDTO = new UsuarioDTO();
         * usuarioDTO.setId(UUID.fromString(id));
         */
        UsuarioDTO usuarioDTO = usuarioServicio.encontrarPorId(UUID.fromString(id));
        modelo.addAttribute("usuarioDTO", usuarioDTO);
        return "modificarUsuario.html";
    }

    // MODIFICAR USUARIO POST
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

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id) {
        usuarioServicio.eliminarUsuario(UUID.fromString(id));
        return "redirect:/dashboard";
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

    // CAMBIAR USUARIO A PROVEEDOR
    @GetMapping("/cambio")
    public String cambio(HttpSession session, Model modelo) {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        UsuarioDTO usuario = usuarioServicio.encontrarPorId(loguedUser.getId());
        proveedorDTO.setUsuario(usuario); // Set user data in ProveedorDTO

        List<ServicioDTO> servicios = sServicio.listarServicios();

        modelo.addAttribute("usuarioSession", usuario);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("proveedorDTO", proveedorDTO);
        return "usuarioAproveedor.html";
    }

    @PostMapping("/guardarUproveedor/{id}")
    public String guardarUproveedor(@PathVariable String id, @ModelAttribute ProveedorDTO proveedorDTO,
            MultipartFile imagenFile, String idServicio,HttpSession session) throws MiExcepcion {
        proveedorDTO.setServicio(sServicio.buscarServicioPorId(UUID.fromString(idServicio)));
        pServicio.crearProveedorPorUsuario(UUID.fromString(id), proveedorDTO, imagenFile);
        // Actualizar la sesión del usuario después de cambiar el rol
        // Método para convertir DTO a entidad
        UsuarioEntidad usuarioActualizado = MapeadorDtoAEntidad.mapearUsuario(usuarioServicio.encontrarPorId(UUID.fromString(id)));
        session.setAttribute("usuarioEnSesion", usuarioActualizado);

        return "redirect:/perfil";

    }

}
