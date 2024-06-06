package grupo_app_servicios.appservicios.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.Proveedor;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, UUID>{
  
}
