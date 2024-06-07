package grupo_app_servicios.appservicios.repositorios;

import grupo_app_servicios.appservicios.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    //busqueda por email
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    //busqueda por nombre
    @Query("select u from Usuario u where u.nombre like %:nombre%")
    public List<Usuario> buscarPorNombre(@Param("nombre") String nombre);

    //busqueda por apellido
    @Query("select u from Usuario u where u.apellido like %:apellido%")
    public List<Usuario> buscarPorApellido(@Param("apellido") String apellido);

}

