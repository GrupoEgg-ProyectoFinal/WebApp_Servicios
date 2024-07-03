package grupo_app_servicios.appservicios.servicios;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorDtoAEntidad;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import grupo_app_servicios.appservicios.utilidades.Validaciones;

@Service
public class ValoracionServicio {

    @Autowired
    private ValoracionRepositorio valoracionRepositorio;
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    // LISTAR TODAS LAS VALORACIONES DE LA BDD
    public List<ValoracionDTO> obtenerTodasLasValoraciones() {
        return valoracionRepositorio.findAll().stream().map(
                valoracionEntidad -> MapeadorEntidadADto.mapearValoracion(valoracionEntidad)).toList();
    }

    // OBTENER VALORACION POR ID
    public ValoracionDTO obtenerValoracionPorId(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id de la valoración");

        ValoracionEntidad valoracionEntidad = valoracionRepositorio.findById(id).orElse(null);
        return MapeadorEntidadADto.mapearValoracion(valoracionEntidad);
    }

    // CREAR VALORACION Y RETORNARLA
    public ValoracionDTO crearValoracion(ValoracionDTO valoracionDTO) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(valoracionDTO.getPuntaje(), "puntaje");

        ValoracionEntidad valoracionEntidad = MapeadorDtoAEntidad.mapearValoracion(valoracionDTO);
        valoracionEntidad = valoracionRepositorio.save(valoracionEntidad);
        return MapeadorEntidadADto.mapearValoracion(valoracionEntidad);
    }

    // ACTUALIZAR VALORACION
    public ValoracionDTO actualizarValoracion(UUID id, ValoracionDTO valoracionDTO) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id");
        Validaciones.validarSiCampoEsNulo(valoracionDTO.getPuntaje(), "puntaje");

        ValoracionEntidad valoracionEntidad = valoracionRepositorio.findById(id).orElseThrow(
                () -> new MiExcepcion("No se ha encontrado un usuario con la id enviada."));
        return MapeadorEntidadADto.mapearValoracion(valoracionRepositorio.save(valoracionEntidad));
    }

    // ELIMINAR VALORACION
    public void eliminarValoracion(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id de la valoración");
        if (valoracionRepositorio.existsById(id)) {
            valoracionRepositorio.deleteById(id);
        } else
            throw new MiExcepcion("No se ha encontrado un usuario con la id enviada");
    }

    // ELIMINAR COMENTARIO
    public ValoracionDTO eliminarComnetario(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id de la valoración");
        ValoracionEntidad entidad = valoracionRepositorio.findById(id).orElseThrow(
                () -> new MiExcepcion("No se ha encontrado la valoración la id enviada."));

        entidad.setComentario("El comentario ha sido eliminado por infrigir las normas de la plataforma.");
        valoracionRepositorio.save(entidad);
        return MapeadorEntidadADto.mapearValoracion(entidad);
    }

    // LISTAR VALORACIONES POR PROVEEDOR
    public List<ValoracionDTO> listarPorProveedor(UUID idProveedor) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(idProveedor, "id del proveedor");

        List<SolicitudDTO> solicitudes = solicitudRepositorio.buscarSolicitudesCalificadasPorProveedor(idProveedor)
                .stream().map(solicitud -> MapeadorEntidadADto.mapearSolicitud(solicitud)).toList();

        List<ValoracionDTO> valoraciones = solicitudes.stream().map(solicitud -> solicitud.getIdValoracion())
                .toList();

        return valoraciones;
    }

}
