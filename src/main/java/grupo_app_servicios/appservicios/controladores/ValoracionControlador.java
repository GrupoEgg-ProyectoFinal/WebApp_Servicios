package grupo_app_servicios.appservicios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo_app_servicios.appservicios.servicios.ValoracionServicio;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionControlador {

    @Autowired
    private ValoracionServicio valoracionServicio;

}
