package grupo_app_servicios.appservicios.repositorios;

import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, UUID> {

    //busqueda por email
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public UsuarioEntidad buscarPorEmail(@Param("email") String email);

    //busqueda por nombre
    @Query("select u from Usuario u where u.nombre like %:nombre%")
    public List<UsuarioEntidad> buscarPorNombre(@Param("nombre") String nombre);

    //busqueda por apellido
    @Query("select u from Usuario u where u.apellido like %:apellido%")
    public List<UsuarioEntidad> buscarPorApellido(@Param("apellido") String apellido);

}

