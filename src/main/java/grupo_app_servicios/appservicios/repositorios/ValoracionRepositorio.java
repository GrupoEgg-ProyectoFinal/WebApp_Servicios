package grupo_app_servicios.appservicios.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;

@Repository
public interface ValoracionRepositorio extends JpaRepository<ValoracionEntidad, UUID>  {
List<ValoracionEntidad> buscarPorPuntaje(Integer puntaje);

}
