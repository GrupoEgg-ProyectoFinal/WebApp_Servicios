package grupo_app_servicios.appservicios.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    // agregar metodo de obtencion por servicio / o agregarlo a una vista a traves de modelmap
    // prueba de metodo para llamar a traves de un fetch y javascript
    @GetMapping("/servicio/{servicio}")
    public List<ProveedorDTO> obtenerProveedoresSegunServicio(@PathVariable String nombreServicio) {
        return pServicio.listarProveedoresSegunServicio(nombreServicio);
    }
}
