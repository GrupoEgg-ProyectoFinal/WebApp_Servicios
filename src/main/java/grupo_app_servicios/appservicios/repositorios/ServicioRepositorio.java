package grupo_app_servicios.appservicios.repositorios;

import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, UUID> {

    // Búsqueda por nombre
    @Query("select s from ServicioEntidad s where s.nombre like %:nombre%")
    public List<ServicioEntidad> buscarPorNombre(@Param("nombre") String nombre);

    // Búsqueda por estado de alta
    @Query("select s from ServicioEntidad s where s.estado = :estado")
    public List<ServicioEntidad> buscarPorEstadoDeAlta(@Param("estado") Boolean estado);
}
