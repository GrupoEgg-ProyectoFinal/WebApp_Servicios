package grupo_app_servicios.appservicios.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.entidades.SolicitudEntidad;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Estados;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import grupo_app_servicios.appservicios.repositorios.ValoracionRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorDtoAEntidad;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import grupo_app_servicios.appservicios.utilidades.Validaciones;

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
    @Autowired
    ValoracionServicio valoracionServicio;

    // CREAR SOLICITUD
    public void crearSolicitud(SolicitudDTO solicitudDTO) throws MiExcepcion {
        Validaciones.validarVariosCampos(
            new String[]{"proveedor asociado", "usuario asociado"}, 
            solicitudDTO.getIdProveedor(), solicitudDTO.getIdUsuario()
        );

        SolicitudEntidad newSolicitud = new SolicitudEntidad();

        newSolicitud.setComentario(solicitudDTO.getComentario());
        newSolicitud.setEstado(Estados.PENDIENTE);

        if (solicitudDTO.getIdProveedor() != null) {
            ProveedorEntidad proveedor = pRepositorio.findById(solicitudDTO.getIdProveedor().getId())
                    .orElseThrow(() -> new MiExcepcion(
                            "Proveedor no encontrado con ID: " + solicitudDTO.getIdProveedor()));
            newSolicitud.setIdProveedor(proveedor);
        }
        if (solicitudDTO.getIdUsuario() != null) {
            UsuarioEntidad usuario = uRepositorio.findById(solicitudDTO.getIdUsuario().getId()).orElseThrow(
                    () -> new MiExcepcion("Usuario no encontrado con ID: " + solicitudDTO.getIdUsuario().getId()));
            newSolicitud.setIdUsuario(usuario);
        }

        sRepositorio.save(newSolicitud);
    }

    // CARGAR VALORACION A LA SOLICITUD
    @Transactional
    public void guardarValoracionEnSolicitud(UUID id, ValoracionDTO valoracionDTO) throws MiExcepcion {
        Validaciones.validarVariosCampos(new String[]{"id de la solicitud", "valoración a asociar"}, id, valoracionDTO);

        ValoracionDTO valoracion = valoracionServicio.crearValoracion(valoracionDTO);
        SolicitudEntidad solicitud = buscarSolicitud(id);
        solicitud.setIdValoracion(MapeadorDtoAEntidad.mapearValoracion(valoracion));
        sRepositorio.save(solicitud);
    }

    // LISTAR SOLICITUDES
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarTodas() {
        List<SolicitudEntidad> list = sRepositorio.findAll();
        return list.stream().map(
                solicitudEntidad -> MapeadorEntidadADto.mapearSolicitud(solicitudEntidad)).toList();
    }

    // LISTAR SOLICITUD POR ESTADO (para proveedor)
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorEstado(Estados estado, UUID idProveedor) throws MiExcepcion {
        Validaciones.validarVariosCampos(new String[]{"estado de solicitud", "id del proveedor asociado"}, estado, idProveedor);

        List<SolicitudEntidad> list = sRepositorio.buscarSolicitudPorEstado(estado, idProveedor);
        return list.stream().map(
                solicitudEntidad -> MapeadorEntidadADto.mapearSolicitud(solicitudEntidad)).toList();
    }

    // LISTAR SOLICITUD POR ESTADO (para usuario)
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorEstadoUsuario(Estados estado, UUID idUsuario) throws MiExcepcion {
        Validaciones.validarVariosCampos(new String[]{"estado de solicitud", "id del usuario asociado"}, estado, idUsuario);

        List<SolicitudEntidad> list = sRepositorio.buscarSolicitudPorEstadoUsuario(estado, idUsuario);
        return list.stream().map(
                solicitudEntidad -> MapeadorEntidadADto.mapearSolicitud(solicitudEntidad)).toList();
    }

    // LISTAR POR VALORACION
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorValoracion() {
        List<SolicitudDTO> lista = sRepositorio.listarPorValoracion().stream()
                .map(solicitudEntidad -> MapeadorEntidadADto.mapearSolicitud(solicitudEntidad)).toList();
        ;
        return lista;
    }

    // BUSCAR SOLICITUD POR ID
    @Transactional(readOnly = true)
    public SolicitudEntidad buscarSolicitud(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id de la solicitud");

        return sRepositorio.findById(id).orElseThrow(
            () -> new MiExcepcion("No se encontró la solicitud con el id envíado")
        );
    }

    // MODIFICAR COMENTARIO DE SOLICITUD
    @Transactional
    public void modificarComentarioSolicitud(SolicitudDTO solicitudDTO) {
        // Se buscar por id y se guarda en un optional
        Optional<SolicitudEntidad> respuesta = sRepositorio.findById(solicitudDTO.getId());

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            SolicitudEntidad solicitudExistente = respuesta.get();
            // Se settea el nuevo comentario enviado por dto
            solicitudExistente.setComentario(solicitudDTO.getComentario());
            // Se persiste en la bdd
            sRepositorio.save(solicitudExistente);
        }
    }

    // Modificar ESTADO solicitud (ACEPTADO/RECHAZADO/PENDIENTE/FINALIZADO)
    @Transactional
    public void modificarEstadoSolicitud(UUID id, Estados estado) throws MiExcepcion {
        Validaciones.validarVariosCampos(new String[]{"id de la valoración", "estado de la valoración"}, id, estado);

        // Se buscar por id y se guarda en un optional
        Optional<SolicitudEntidad> respuesta = sRepositorio.findById(id);

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            SolicitudEntidad solicitudExistente = respuesta.get();
            // Se settea el nuevo estado enviado por dto
            solicitudExistente.setEstado(estado);
            // Se persiste en la bdd
            sRepositorio.save(solicitudExistente);
        } else {
            throw new MiExcepcion("No se ha encontrado una solicitud con el id enviado.");
        }
    }

    @Transactional
    public void eliminarSolicitud(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id de la solicitud");

        sRepositorio.deleteById(id);
    }

    // LISTAR SOLICITUDES CON VALORACION
    @Transactional(readOnly = true)
    public List<SolicitudDTO> listarSoliConValoracion(UUID idProveedor) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(idProveedor, "id del proveedor asociado");

        List<SolicitudDTO> solicitudes = sRepositorio.buscarSolicitudesCalificadasPorProveedor(idProveedor).stream()
                .map(solicitud -> MapeadorEntidadADto.mapearSolicitud(solicitud)).collect(Collectors.toList());

        return solicitudes;
    }

}
