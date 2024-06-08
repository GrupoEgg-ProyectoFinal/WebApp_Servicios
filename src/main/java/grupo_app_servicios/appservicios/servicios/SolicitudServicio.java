package grupo_app_servicios.appservicios.servicios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.entidades.Solicitud;
import grupo_app_servicios.appservicios.entidades.Usuario;
import grupo_app_servicios.appservicios.entidades.Valoracion;
import grupo_app_servicios.appservicios.enumeraciones.Estados;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;

@Service
public class SolicitudServicio {
    @Autowired
    SolicitudRepositorio sRepositorio;
    @Autowired
    ValoracionRepositorio vRepositorio;
    @Autowired
    ProveedorRepositorio pRepositorio;
    @Autowired
    UsuarioRepositorio uRepositorio;

    // CREAR SOLICITUD
    public void crearSolicitud(SolicitudDTO solicitudDTO) {
        Solicitud newSolicitud = new Solicitud();

        newSolicitud.setComentario(solicitudDTO.getComentario());
        newSolicitud.setEstado(Estados.PENDIENTE);

        // Mapea y asigna las entidades relacionadas al servicio, si no las encuentra
        // arroja error.
        // QUE VALORACION SE LE ASIGNARIA SI NO  TIENE
        // if (solicitudDTO.getIdValoracion() != null) {
        //     Valoracion valoracion = vRepositorio.findById(solicitudDTO.getIdValoracion().getId())
        //             .orElseThrow(() -> new RuntimeException(
        //                     "Valoracion no encontrada con ID: " + solicitudDTO.getIdValoracion().getId()));
        //     newSolicitud.setIdValoracion(valoracion);
        // }

        if (solicitudDTO.getIdProveedor() != null) {
            Proveedor proveedor = pRepositorio.findById(solicitudDTO.getIdProveedor().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Proveedor no encontrado con ID: " + solicitudDTO.getIdProveedor().getId()));
            newSolicitud.setIdProveedor(proveedor);
        }
        if (solicitudDTO.getIdUsuario() != null) {
            Usuario usuario = uRepositorio.findById(solicitudDTO.getIdUsuario().getId()).orElseThrow(
                    () -> new RuntimeException("Usuario no encontrado con ID: " + solicitudDTO.getIdUsuario().getId()));
            newSolicitud.setIdUsuario(usuario);
        }

        sRepositorio.save(newSolicitud);
    }

    // BUSCAR SOLICITUD POR ID
    @Transactional(readOnly = true)
    public Solicitud buscarSolicitud(UUID id) {
        return sRepositorio.findById(id).orElse(null);
    }

    // MODIFICAR COMENTARIO DE SOLICITUD 
    @Transactional
    public void modificarComentarioSolicitud(SolicitudDTO solicitudDTO) {
        // Se buscar por id y se guarda en un optional
        Optional<Solicitud> respuesta = sRepositorio.findById(solicitudDTO.getId());

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            Solicitud solicitudExistente = respuesta.get();
            // Se settea el nuevo comentario enviado por dto
            solicitudExistente.setComentario(solicitudDTO.getComentario());
            // Se persiste en la bdd
            sRepositorio.save(solicitudExistente);

        }
    }

    // Modificar ESTADO solicitud (ACEPTADO/RECHAZADO/PENDIENTE/FINALIZADO)
    @Transactional
    public void modificarEstadoSolicitud(SolicitudDTO solicitudDTO) {
        // Se buscar por id y se guarda en un optional
        Optional<Solicitud> respuesta = sRepositorio.findById(solicitudDTO.getId());

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            Solicitud solicitudExistente = respuesta.get();
            // Se settea el nuevo estado enviado por dto
            solicitudExistente.setEstado(solicitudDTO.getEstado());
            // Se persiste en la bdd
            sRepositorio.save(solicitudExistente);

        }
    }
}





// Buscar por id Utilizando como metodo de retorno un DTO
// @Transactional
// public EditorialCreateDTO buscar(String id) {
// Editorial respuesta = editorialRepositorio.getById(id);
// EditorialCreateDTO editorialDTO = new EditorialCreateDTO();
// editorialDTO.setId(respuesta.getId());
// editorialDTO.setNombre(respuesta.getNombre());
// editorialDTO.setAlta(respuesta.isAlta());

// return editorialDTO;

// }