package grupo_app_servicios.appservicios.utilidades;

import java.util.List;

import grupo_app_servicios.appservicios.Dto.ImagenProvDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import grupo_app_servicios.appservicios.entidades.SolicitudEntidad;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.entidades.ValoracionEntidad;

/* 
 * Esta clase contiene métodos que deben ser estáticos y públicos, para poder ser usados solo los que se necesiten
 * y sin necesidad de instanciar la clase. 
 * Sirve para mapear los DTO y retornar la entidad que corresponda.
*/
public class MapeadorDtoAEntidad {

    public static UsuarioEntidad mapearUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntidad usuarioMapeado = new UsuarioEntidad();
        usuarioMapeado.setId(usuarioDTO.getId());
        usuarioMapeado.setNombre(usuarioDTO.getNombre());
        usuarioMapeado.setApellido(usuarioDTO.getApellido());
        usuarioMapeado.setTelefono(usuarioDTO.getTelefono());
        usuarioMapeado.setEmail(usuarioDTO.getEmail());
        usuarioMapeado.setContrasena(usuarioDTO.getContrasena());
        usuarioMapeado.setRol(usuarioDTO.getRol());
        usuarioMapeado.setBarrios(usuarioDTO.getBarrios());
        usuarioMapeado.setEstado(usuarioDTO.getEstado());

        return usuarioMapeado;
    }

    public static ProveedorEntidad mapearProveedor(ProveedorDTO proveedorDTO) {
        ProveedorEntidad proveedorMapeado = new ProveedorEntidad();
        proveedorMapeado.setId(proveedorDTO.getId());
        proveedorMapeado.setMatricula(proveedorDTO.getMatricula());
        proveedorMapeado.setDescripcion(proveedorDTO.getDescripcion());
        if (proveedorDTO.getUsuario() != null) {
            UsuarioEntidad usuario = MapeadorDtoAEntidad.mapearUsuario(proveedorDTO.getUsuario());
            proveedorMapeado.setUsuario(usuario);
        }
        if (proveedorDTO.getFoto() != null) {
            ImagenProvEntidad imagenProveedor = MapeadorDtoAEntidad.mapearImagenProveedor(proveedorDTO.getFoto());
            proveedorMapeado.setFoto(imagenProveedor);
        }
        if (proveedorDTO.getServicio() != null) {
            ServicioEntidad servicio = MapeadorDtoAEntidad.mapearServicio(proveedorDTO.getServicio());
            proveedorMapeado.setServicio(servicio);
        }
        if (proveedorDTO.getSolicitudes() != null) {
            List<SolicitudEntidad> listaSolicitudes = proveedorDTO.getSolicitudes().stream().map(
                solicitudDTO -> mapearSolicitud(solicitudDTO)
            ).toList();
            proveedorMapeado.setSolicitudes(listaSolicitudes);
        }

        return proveedorMapeado;
    }

    public static ImagenProvEntidad mapearImagenProveedor(ImagenProvDTO imagenProveedorDTO) {
        ImagenProvEntidad imagenMapeada = new ImagenProvEntidad();
        imagenMapeada.setId(imagenProveedorDTO.getId());
        imagenMapeada.setContenido(imagenProveedorDTO.getContenido());
        imagenMapeada.setMime(imagenProveedorDTO.getMime());
        imagenMapeada.setNombre(imagenProveedorDTO.getNombre());

        return imagenMapeada;
    }

    public static ServicioEntidad mapearServicio(ServicioDTO servicioDTO) {
        ServicioEntidad servicioMapeado = new ServicioEntidad();
        servicioMapeado.setId(servicioDTO.getId());
        servicioMapeado.setNombre(servicioDTO.getNombre());
        servicioMapeado.setDescripcion(servicioDTO.getDescripcion());
        servicioMapeado.setEstado(servicioDTO.getEstado());
        servicioMapeado.setImagen(mapearImagenProveedor(servicioDTO.getImagen()));

        return servicioMapeado;
    }

    public static ValoracionEntidad mapearValoracion(ValoracionDTO valoracionDTO) {
        ValoracionEntidad valoracionMapeada = new ValoracionEntidad();
        valoracionMapeada.setId(valoracionDTO.getId());
        valoracionMapeada.setComentario(valoracionDTO.getComentario());
        valoracionMapeada.setPuntaje(valoracionDTO.getPuntaje());

        return valoracionMapeada;
    }

    public static SolicitudEntidad mapearSolicitud(SolicitudDTO solicitudDTO) {
        SolicitudEntidad solicitudMapeada = new SolicitudEntidad();
        solicitudMapeada.setId(solicitudDTO.getId());
        solicitudMapeada.setComentario(solicitudDTO.getComentario());
        solicitudMapeada.setEstado(solicitudDTO.getEstado());
        if (solicitudDTO.getIdValoracion() != null) {
            ValoracionEntidad valoracion = mapearValoracion(solicitudDTO.getIdValoracion());
            solicitudMapeada.setIdValoracion(valoracion);
        }
        if (solicitudDTO.getIdUsuario() != null) {
            UsuarioEntidad usuario = mapearUsuario(solicitudDTO.getIdUsuario());
            solicitudMapeada.setIdUsuario(usuario);
        }
        if (solicitudDTO.getIdProveedor() != null) {
            ProveedorEntidad proveedor = mapearProveedor(solicitudDTO.getIdProveedor());
            solicitudMapeada.setIdProveedor(proveedor);
        }

        return solicitudMapeada;
    }
}
