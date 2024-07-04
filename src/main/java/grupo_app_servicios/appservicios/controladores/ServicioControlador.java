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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.ServicioServicio;

@Controller
@RequestMapping("/servicio")
public class ServicioControlador {
    @Autowired
    ServicioServicio sServicio;
    @GetMapping("")
    public String vistaServicios(Model modelo){
        List <ServicioDTO> servicios = sServicio.listarServicios();
        modelo.addAttribute("servicios", servicios);
        ServicioDTO servicioDTO = new ServicioDTO();
        modelo.addAttribute("servicioDTO", servicioDTO);
        return "servicioAdmin.html";
    }


    @PostMapping("")
    public String registrarServicioAccion(@ModelAttribute ServicioDTO servicioDTO, Model modelo) throws MiExcepcion  {
        try {
            sServicio.crearServicio(servicioDTO);
            modelo.addAttribute("mensaje", "Servicio " + servicioDTO.getNombre() + " creado exitosamente.");
            return "redirect:/servicio"; 
        } catch (MiExcepcion ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "redirect:/servicio";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable String id, ServicioDTO servicioDTO) throws MiExcepcion{
       sServicio.eliminarServicio(UUID.fromString(id));
       return "redirect:/servicio";

    }

    @GetMapping("/modificar")
    public String modificarServicioVista(Model map) {
        map.addAttribute("servicioDTO", new ServicioDTO());
        return "(ruta de la vista de modificación)";
    }

    @PutMapping("modificarAccion")
    public String modificarServicioAccion(@ModelAttribute ServicioDTO servicioDTO, Model modelo) throws MiExcepcion {
        try {
            sServicio.modificarServicio(servicioDTO);
            modelo.addAttribute("mensaje", "Servicio " + servicioDTO.getNombre() + " creado exitosamente.");
            return "(ruta del panel de admin)";
        } catch(MiExcepcion ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "redirect:/registrar";
        }
    }
}
