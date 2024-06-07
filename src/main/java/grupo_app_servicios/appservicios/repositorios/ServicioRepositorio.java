package grupo_app_servicios.appservicios.repositorios;

import grupo_app_servicios.appservicios.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, UUID> {

    // Búsqueda por nombre
    @Query("select s from Servicio s where s.nombre like %:nombre%")
    public List<Servicio> buscarPorNombre(@Param("nombre") String nombre);
}
