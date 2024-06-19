package grupo_app_servicios.appservicios.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;

@Repository
public interface ValoracionRepositorio extends JpaRepository<ValoracionEntidad, UUID> {
    @Query("SELECT v FROM ValoracionEntidad v WHERE v.puntaje = :puntaje")
    List<ValoracionEntidad> buscarPorPuntaje(@Param("puntaje") Integer puntaje);

}
