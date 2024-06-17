package grupo_app_servicios.appservicios.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;

@Repository
public interface ProveedorRepositorio extends JpaRepository<ProveedorEntidad, UUID> {

    @Query("select p from ProveedorEntidad p where p.servicio.nombre = ?1")
    public List<ProveedorEntidad> obtenerProveedoresPorServicio(String servicio);

    /* Se repite la comparación que usa el OR ya que el valor de busqueda puede contener:
    solo el nombre, solo el apellido, (por eso los OR)
    o podría contener ambos (por eso el AND entre medio de los OR) */
    @Query(value = "select p from ProveedorEntidad p where " +
            "(p.nombre like %:busqueda% or p.apellido like %:busqueda%)" +
            " and " +
            "(p.nombre like %:busqueda% or p.apellido like %:busqueda%)"
    )
    public List<ProveedorEntidad> obtenerProveedoresPorNombreYOApellido(@Param("busqueda") String busqueda);

    //busqueda por email
    @Query("SELECT u FROM ProveedorEntidad u WHERE u.email = :email")
    public ProveedorEntidad buscarPorEmail(@Param("email") String email);
}
