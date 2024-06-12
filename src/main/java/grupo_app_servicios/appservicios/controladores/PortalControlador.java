package grupo_app_servicios.appservicios.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.servicios.OPCIONAL;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    OPCIONAL opcional;

    

    @GetMapping("/") // Acá es donde realizamos el mapeo
    public String index(ModelMap modelo) {
        List<ProveedorDTO> proveedores = opcional.listarProveedores();

        modelo.addAttribute("proveedores", proveedores);
        return "index.html"; // Acá es que retornamos con el método.
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "formulario.html";
    }
}
