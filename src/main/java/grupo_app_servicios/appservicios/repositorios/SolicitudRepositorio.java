package grupo_app_servicios.appservicios.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.SolicitudEntidad;

@Repository
public interface SolicitudRepositorio extends JpaRepository<SolicitudEntidad, UUID> {

    @Query("select p from SolicitudEntidad p where p.estado = ?1")
    public List<SolicitudEntidad> buscarSolicitudPorEstado(String estado);

    @Query("select p from SolicitudEntidad p where p.idValoracion.puntaje = ?1")
    public List<SolicitudEntidad> buscarSolicitudPorPuntaje(Integer puntaje);

}
