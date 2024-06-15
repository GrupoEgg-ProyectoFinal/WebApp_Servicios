package grupo_app_servicios.appservicios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.servicios.ServicioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/servicio")
public class ServicioControlador {
    @Autowired
    ServicioServicio sServicio;

    @GetMapping("/listar")
    public String listarServiciosVista(ModelMap map) {
        map.put("servicios", sServicio.listarServicios());

        return "(ruta de la vista de listado)";
    }
    
    @GetMapping("/registrar")
    public String registrarServicioVista(Model map) {
        map.addAttribute("servicioDTO", new ServicioDTO());
        return "(ruta de la vista de creacion)";
    }
    
    @PostMapping("/registrarAccion")
    public String registrarServicioAccion(@ModelAttribute ServicioDTO servicioDTO, Model model) {
        try {
            sServicio.crearServicio(servicioDTO);
            model.addAttribute("mensaje", "Servicio " + servicioDTO.getNombre() + " creado exitosamente.");
            return "(ruta del panel de admin)";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al intentar crear el servicio.");
            return "redirect:/registrar";
        }
    }
}
