package grupo_app_servicios.appservicios.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.SolicitudServicio;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {
    @Autowired
    ProveedorServicio2 pServicio;
    @Autowired
    ValoracionServicio vServicio;
    @Autowired
    SolicitudServicio solicitudServicio;

    @GetMapping("/modificar")
    public String modificarVista(Model model) {
        model.addAttribute("proveedorDTO", new ProveedorDTO());
        return "perfilProveedor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable String id) {
        try {
            pServicio.eliminarProveedorPorIdDeUsuario(UUID.fromString(id));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/misValoraciones")
    public String listarValoraciones(HttpSession session, Model modelo) {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        ProveedorDTO proveedor = pServicio.buscarPorIdUsuario(loguedUser.getId());
        System.out.println(loguedUser.getApellido());
        List<SolicitudDTO> solicitudes = solicitudServicio.listarSoliConValoracion(proveedor.getId());
        System.out.println(solicitudes.toString());
        List<ValoracionDTO> valoraciones = vServicio.listarPorProveedor(proveedor.getId());
        System.out.println(valoraciones.toString());
        modelo.addAttribute("solicitudes", solicitudes);
        modelo.addAttribute("valoraciones", valoraciones);

        double promedio;
        double valor = 0;
        for (ValoracionDTO valoracionDTO : valoraciones) {
            valor = valor + valoracionDTO.getPuntaje();
        }

        promedio = valor / valoraciones.size();
        modelo.addAttribute("promedio", promedio);
        return "valoracionesProveedor.html";
    }

}
