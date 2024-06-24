package grupo_app_servicios.appservicios.controladores;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.SolicitudServicio;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;



@Controller
@RequestMapping("/contratar")
public class SolicitudControlador {
    @Autowired
    SolicitudServicio sServicio;
    @Autowired
    ProveedorServicio2 pServicio;
    @Autowired
    UsuarioServicio uServicio;
    @Autowired
    ValoracionServicio vServicio;

    @GetMapping("/crearSolicitud/{id}")
    public String crearSolicitud(@PathVariable String id, Model modelo) {
        // modelo.addAttribute("SolicitudDTO", new SolicitudDTO());
        // modelo.addAttribute("proveedores", pServicio.listarProveedores());
        // modelo.addAttribute("usuarios", uServicio.listarUsuarios());
        ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id)); // Convertir String a UUID
        // Suponiendo que tienes un método getNombreCompleto() en ProveedorDTO
        modelo.addAttribute("proveedorNombre", proveedorDTO.getUsuario().getNombre()); 
        modelo.addAttribute("proveedorId", id); // Agregar el ID del proveedor para usarlo en el formulario
        modelo.addAttribute("SolicitudDTO", new SolicitudDTO());

        return "solicitudformulario.html";
    }

    @PostMapping("/guardarSolicitud/{proveedorId}")
    public String guardarSolicitud(@PathVariable("proveedorId") String proveedorId,
            @ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO, @AuthenticationPrincipal UserDetails userDetails,
            Model modelo) {
        try {
            if (userDetails != null) {
                // Verificar que userDetails no sea nulo antes de acceder a su propiedad
                UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());
                solicitudDTO.setIdUsuario(usuarioDTO);

                ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(proveedorId));
                solicitudDTO.setIdProveedor(proveedorDTO);

                sServicio.crearSolicitud(solicitudDTO);

                return "redirect:../perfil";
            } else {
                // Manejar el caso cuando userDetails es nulo
                modelo.addAttribute("error", "Error: usuario no identificado.");
                return "solicitudformulario";
            }
        } catch (Exception e) {
            modelo.addAttribute("error", "Ocurrió un error al intentar crear la solicitud.");
            return "solicitudformulario";
        }
    }

    @GetMapping("/modificarSolicitud/{id}")
    public String modificarSolicitud_Vista(@PathVariable String id, Model modelo) {
        ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id)); // Convertir String a UUID
        // Suponiendo que tienes un método getNombreCompleto() en ProveedorDTO
        modelo.addAttribute("proveedorNombre", proveedorDTO.getUsuario().getNombre()); 
        modelo.addAttribute("proveedorId", id); // Agregar el ID del proveedor para usarlo en el formulario
        modelo.addAttribute("SolicitudDTO", new SolicitudDTO());

        return "vista de modificación";
    }
    
    @PutMapping("/modificarSolicitud/{id}")
    public String modificarSolicitud_Accion(@PathVariable String id,
    @ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO, @AuthenticationPrincipal UserDetails userDetails,
    Model modelo) {
        try {
            if (userDetails != null) {
                // Verificar que userDetails no sea nulo antes de acceder a su propiedad
                UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());
                solicitudDTO.setIdUsuario(usuarioDTO);

                ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id));
                solicitudDTO.setIdProveedor(proveedorDTO);

                sServicio.modificarComentarioSolicitud(solicitudDTO);

                return "redirect:../perfil";
            } else {
                // Manejar el caso cuando userDetails es nulo
                modelo.addAttribute("error", "Error: usuario no identificado.");
                return "solicitudformulario";
            }
        } catch (Exception e) {
            modelo.addAttribute("error", "Ocurrió un error al intentar crear la solicitud.");
            return "solicitudformulario";
        }
    }

    @DeleteMapping("/borrarSolicitud")
    public String eliminarSolicitud(@RequestParam String id) {
        try {
            sServicio.eliminarSolicitud(UUID.fromString(id));
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        return "redirect:../perfil";
    }
}

// @PostMapping("/guardarSolicitud/{proveedorId}")
// public String guardarSolicitud(@PathVariable("proveedorId") UUID proveedorId,
// @ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO,
// @AuthenticationPrincipal UserDetails userDetails, Model modelo) {
// try {
// ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(proveedorId);
// UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());

// solicitudDTO.setIdUsuario(usuarioDTO); // Cambio de setIdUsuario(UUID) a
// setIdUsuario(UsuarioDTO)
// solicitudDTO.setIdProveedor(proveedorDTO); // Cambio de setIdProveedor(UUID)
// a setIdProveedor(ProveedorDTO)

// sServicio.crearSolicitud(solicitudDTO);

// return "vistaUsuario.html";
// } catch (Exception e) {
// modelo.addAttribute("error", "Ocurrió un error al intentar crear la
// solicitud.");
// return "solicitudformulario";
// }
// }

// @PostMapping("/guardarSolicitud/{proveedorId}")
// public String guardarSolicitud(@PathVariable UUID proveedorId,
// @ModelAttribute SolicitudDTO solicitudDTO, @AuthenticationPrincipal
// UserDetails userDetails, Model modelo) {
// try {
// // UsuarioDTO usuarioDTO =
// uServicio.buscarPorEmail(userDetails.getUsername());
// // solicitudDTO.setId(usuarioDTO.getId());
// // solicitudDTO.setIdProveedor(pServicio.buscaProveedorId(proveedorId));
// // sServicio.crearSolicitud(solicitudDTO);
// // Buscar y mapear el proveedor a DTO
// ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(proveedorId);

// // Buscar y mapear el usuario a DTO
// UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());

// // Establecer los IDs correspondientes en solicitudDTO
// solicitudDTO.setIdUsuario(usuarioDTO);
// solicitudDTO.setIdProveedor(proveedorDTO);

// // Crear la solicitud
// sServicio.crearSolicitud(solicitudDTO);

// return "redirect:/vistaUsuario";
// } catch (Exception e) {
// modelo.addAttribute("error", "Ocurrió un error al intentar crear la
// solicitud.");
// return "solicitudformulario";
// }
// }
