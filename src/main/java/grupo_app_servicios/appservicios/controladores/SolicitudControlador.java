package grupo_app_servicios.appservicios.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Estados;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.SolicitudServicio;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contratar")
public class SolicitudControlador {
    @Autowired
    SolicitudServicio solServicio;
    @Autowired
    ProveedorServicio2 pServicio;
    @Autowired
    UsuarioServicio uServicio;
    @Autowired
    ValoracionServicio vServicio;

    @GetMapping("/crearSolicitud/{id}")
    public String crearSolicitud(@PathVariable String id, Model modelo) throws MiExcepcion {
        // modelo.addAttribute("SolicitudDTO", new SolicitudDTO());
        // modelo.addAttribute("proveedores", pServicio.listarProveedores());
        // modelo.addAttribute("usuarios", uServicio.listarUsuarios());
        ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id)); // Convertir String a UUID
        // Suponiendo que tienes un método getNombreCompleto() en ProveedorDTO
        modelo.addAttribute("proveedor", proveedorDTO);
        modelo.addAttribute("proveedorId", id); // Agregar el ID del proveedor para usarlo en el formulario
        modelo.addAttribute("SolicitudDTO", new SolicitudDTO());

        return "contratarFormulario.html";
    }

    @PostMapping("/guardarSolicitud/{proveedorId}")
    public String guardarSolicitud(@PathVariable("proveedorId") String proveedorId,
            @ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO, @AuthenticationPrincipal UserDetails userDetails,
            Model modelo) throws MiExcepcion {
        try {
            // if (userDetails != null) {
            // Verificar que userDetails no sea nulo antes de acceder a su propiedad
            UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());
            solicitudDTO.setIdUsuario(usuarioDTO);

            ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(proveedorId));
            solicitudDTO.setIdProveedor(proveedorDTO);
            System.out.println("--------------------------------");
            System.out.println(usuarioDTO.toString());

            solServicio.crearSolicitud(solicitudDTO);

            return "redirect:../../perfil";
        } catch (MiExcepcion ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "redirect:/crearSolicitud/{id}";
        }

        }

    // MODIFICAR ESTADO
    @GetMapping("/modificarEstado/{id}/{estado}")
    public String cambiareEstado(@PathVariable String id, @PathVariable String estado, HttpSession session)throws MiExcepcion {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        String role = loguedUser.getRol().toString();

        solServicio.modificarEstadoSolicitud(UUID.fromString(id), Estados.valueOf(estado));

        if (role.equals("USER")) {
            return "redirect:/contratar/lista";
        }

        return "redirect:/perfil";
    }

    // LISTA SOLICITUDES
    @GetMapping("/lista")
    public String listarSolicitudes(HttpSession session, Model modelo) throws MiExcepcion {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        UsuarioDTO usuarioDTO = uServicio.encontrarPorId(loguedUser.getId());
        List<SolicitudDTO> pendientes = solServicio.listarPorEstadoUsuario(Estados.PENDIENTE, usuarioDTO.getId());
        List<SolicitudDTO> aceptados = solServicio.listarPorEstadoUsuario(Estados.ACEPTADO, usuarioDTO.getId());
        List<SolicitudDTO> finalizados = solServicio.listarPorEstadoUsuario(Estados.FINALIZADO, usuarioDTO.getId());
        List<SolicitudDTO> cancelados = solServicio.listarPorEstadoUsuario(Estados.CANCELADO, usuarioDTO.getId());
        modelo.addAttribute("pendientes", pendientes);
        modelo.addAttribute("aceptados", aceptados);
        modelo.addAttribute("finalizados", finalizados);
        modelo.addAttribute("cancelados", cancelados);

        return "solicitudesList.html";
    }

    // CALIFICAR
    @GetMapping("/calificar")
    public String calificar(HttpSession session, Model modelo) {
        return "calificar.html";
    }

    // @GetMapping("/modificarSolicitud/{id}")
    // public String modificarSolicitud_Vista(@PathVariable String id, Model modelo)
    // {
    // ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id));
    // // Convertir String a UUID
    // // Suponiendo que tienes un método getNombreCompleto() en ProveedorDTO
    // modelo.addAttribute("proveedorNombre",
    // proveedorDTO.getUsuario().getNombre());
    // modelo.addAttribute("proveedorId", id); // Agregar el ID del proveedor para
    // usarlo en el formulario
    // modelo.addAttribute("SolicitudDTO", new SolicitudDTO());

    // return "vista de modificación";
    // }

    // @PutMapping("/modificarSolicitud/{id}")
    // public String modificarSolicitud_Accion(@PathVariable String id,
    // @ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO,
    // @AuthenticationPrincipal UserDetails userDetails,
    // Model modelo) {
    // try {
    // if (userDetails != null) {
    // // Verificar que userDetails no sea nulo antes de acceder a su propiedad
    // UsuarioDTO usuarioDTO = uServicio.buscarPorEmail(userDetails.getUsername());
    // solicitudDTO.setIdUsuario(usuarioDTO);

    // ProveedorDTO proveedorDTO = pServicio.buscaProveedorId(UUID.fromString(id));
    // solicitudDTO.setIdProveedor(proveedorDTO);

    // sServicio.modificarComentarioSolicitud(solicitudDTO);

    // return "redirect:../perfil";
    // } else {
    // // Manejar el caso cuando userDetails es nulo
    // modelo.addAttribute("error", "Error: usuario no identificado.");
    // return "solicitudformulario";
    // }
    // } catch (Exception e) {
    // modelo.addAttribute("error", "Ocurrió un error al intentar crear la
    // solicitud.");
    // return "solicitudformulario";
    // }
    // }

    // @DeleteMapping("/borrarSolicitud")
    // public String eliminarSolicitud(@RequestParam String id) {
    // try {
    // sServicio.eliminarSolicitud(UUID.fromString(id));
    // } catch (Exception e) {
    // System.out.println("error: " + e.getMessage());
    // }
    // return "redirect:../perfil";
    // }
}
