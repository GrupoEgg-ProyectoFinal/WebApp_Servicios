package grupo_app_servicios.appservicios.servicios;

import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ValoracionServicio {

    @Autowired
    private ValoracionRepositorio valoracionRepositorio;

    // LISTAR TODAS LAS VALORACIONES DE LA BDD
    public List<ValoracionDTO> obtenerTodasLasValoraciones() {
        return valoracionRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // OBTENER VALORACION POR ID
    public ValoracionDTO obtenerValoracionPorId(UUID id) {
        ValoracionEntidad valoracionEntidad = valoracionRepositorio.findById(id).orElse(null);
        return convertirADTO(valoracionEntidad);
    }

    // CREAR VALORACION Y RETORNARLA
    public ValoracionDTO crearValoracion(ValoracionDTO valoracionDTO) {
        ValoracionEntidad valoracionEntidad = convertirAEntidad(valoracionDTO);
        valoracionEntidad = valoracionRepositorio.save(valoracionEntidad);
        return convertirADTO(valoracionEntidad);
    }

    // ACTUALIZAR VALORACION
    public ValoracionDTO actualizarValoracion(UUID id, ValoracionDTO valoracionDTO) {
        ValoracionEntidad valoracionEntidad = valoracionRepositorio.findById(id).orElse(null);
        return convertirADTO(valoracionRepositorio.save(valoracionEntidad));
    }

    // ELIMINAR VALORACION
    public boolean eliminarValoracion(UUID id) {
        if (valoracionRepositorio.existsById(id)) {
            valoracionRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    // CONVERTIDORES
    private ValoracionDTO convertirADTO(ValoracionEntidad valoracionEntidad) {
        return new ValoracionDTO(
                valoracionEntidad.getId(),
                valoracionEntidad.getComentario(),
                valoracionEntidad.getPuntaje());
    }

    private ValoracionEntidad convertirAEntidad(ValoracionDTO valoracionDTO) {
        return new ValoracionEntidad(
                valoracionDTO.getId(),
                valoracionDTO.getPuntaje(),
                valoracionDTO.getComentario());
    }

    // ELIMINAR COMENTARIO
    public ValoracionDTO eliminarComnetario(UUID id) {
        ValoracionEntidad entidad = valoracionRepositorio.findById(id).orElse(null);

        entidad.setComentario(null);
        valoracionRepositorio.save(entidad);
        return convertirADTO(entidad);
    }
}
