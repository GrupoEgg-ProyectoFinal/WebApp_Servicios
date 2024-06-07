package grupo_app_servicios.appservicios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;

@Service
public class ProveedorServicio {
    @Autowired
    ProveedorRepositorio proveedorRepositorio;
}
