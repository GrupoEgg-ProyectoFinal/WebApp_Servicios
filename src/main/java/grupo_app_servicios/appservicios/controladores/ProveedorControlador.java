package grupo_app_servicios.appservicios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;


@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {
    @Autowired
    ProveedorServicio2 pServicio;

    @GetMapping("/modificar")
    public String modificarVista(Model model) {
        model.addAttribute("proveedorDTO", new ProveedorDTO());
        return "perfilProveedor";
    }
    
}