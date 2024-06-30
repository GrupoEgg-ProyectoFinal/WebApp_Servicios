package grupo_app_servicios.appservicios.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.SolicitudEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Estados;

@Repository
public interface SolicitudRepositorio extends JpaRepository<SolicitudEntidad, UUID> {
  //Busqueda para proveedor
    @Query("select p from SolicitudEntidad p where p.estado = ?1 and p.idProveedor.id = ?2")
    public List<SolicitudEntidad> buscarSolicitudPorEstado(Estados estado,UUID idProveedor);

    //Busqueda para usuario
    @Query("select p from SolicitudEntidad p where p.estado = ?1 and p.idUsuario.id = ?2")
    public List<SolicitudEntidad> buscarSolicitudPorEstadoUsuario(Estados estado,UUID idUsuario);

    @Query("select p from SolicitudEntidad p where p.idValoracion.puntaje = ?1")
    public List<SolicitudEntidad> buscarSolicitudPorPuntaje(Integer puntaje);

}
