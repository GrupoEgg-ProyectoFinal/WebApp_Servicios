package grupo_app_servicios.appservicios.servicios;

import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ValoracionServicio {

    @Autowired
    private ValoracionRepositorio valoracionRepositorio;
    
    //LISTAR TODAS LAS VALORACIONES DE LA BDD
    public List<ValoracionDTO> obtenerTodasLasValoraciones() {
        return valoracionRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    //OBTENER VALORACION POR ID
    public Optional<ValoracionDTO> obtenerValoracionPorId(UUID id) {
        return valoracionRepositorio.findById(id)
                .map(this::convertirADTO);
    }
    //CREAR VALORACION Y RETORNARLA
    public ValoracionDTO crearValoracion(ValoracionDTO valoracionDTO) {
        ValoracionEntidad valoracionEntidad = convertirAEntidad(valoracionDTO);
        valoracionEntidad = valoracionRepositorio.save(valoracionEntidad);
        return convertirADTO(valoracionEntidad);
    }
   // ACTUALIZAR VALORACION 
    public Optional<ValoracionDTO> actualizarValoracion(UUID id, ValoracionDTO valoracionDTO) {
        return valoracionRepositorio.findById(id).map(valoracionExistente -> {
                    valoracionExistente.setPuntaje(valoracionDTO.getPuntaje());
                    valoracionExistente.setComentario(valoracionDTO.getComentario());
                    valoracionRepositorio.save(valoracionExistente);
                    return convertirADTO(valoracionExistente);
                });
    }
   // ELIMINAR VALORACION
    public boolean eliminarValoracion(UUID id) {
        if (valoracionRepositorio.existsById(id)) {
            valoracionRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    //CONVERTIDORES
    private ValoracionDTO convertirADTO(ValoracionEntidad valoracionEntidad) {
        return new ValoracionDTO(
                valoracionEntidad.getId(),
                valoracionEntidad.getComentario(),
                valoracionEntidad.getPuntaje()
        );
    }
    private ValoracionEntidad convertirAEntidad(ValoracionDTO valoracionDTO) {
        return new ValoracionEntidad(
                valoracionDTO.getId(),
                valoracionDTO.getPuntaje(),
                valoracionDTO.getComentario()
        );
    }

    // ELIMINAR COMENTARIO
    public ValoracionDTO eliminarComnetario(UUID id){
        ValoracionEntidad entidad = valoracionRepositorio.findById(id).orElse(null);

        entidad.setComentario(null);
        valoracionRepositorio.save(entidad);
        return convertirADTO(entidad);
    }
}
