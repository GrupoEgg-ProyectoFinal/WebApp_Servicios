package grupo_app_servicios.appservicios.servicios;

import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;
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

    public List<ValoracionDTO> obtenerTodasLasValoraciones() {
        return valoracionRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<ValoracionDTO> obtenerValoracionPorId(UUID id) {
        return valoracionRepositorio.findById(id)
                .map(this::convertirADTO);
    }

    public ValoracionDTO crearValoracion(ValoracionDTO valoracionDTO) {
        ValoracionEntidad valoracionEntidad = convertirAEntidad(valoracionDTO);
        valoracionEntidad = valoracionRepositorio.save(valoracionEntidad);
        return convertirADTO(valoracionEntidad);
    }

    public Optional<ValoracionDTO> actualizarValoracion(UUID id, ValoracionDTO valoracionDTO) {
        return valoracionRepositorio.findById(id)
                .map(valoracionExistente -> {
                    valoracionExistente.setPuntaje(valoracionDTO.getPuntaje());
                    valoracionExistente.setComentario(valoracionDTO.getComentario());
                    valoracionRepositorio.save(valoracionExistente);
                    return convertirADTO(valoracionExistente);
                });
    }

    public boolean eliminarValoracion(UUID id) {
        if (valoracionRepositorio.existsById(id)) {
            valoracionRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

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
}
