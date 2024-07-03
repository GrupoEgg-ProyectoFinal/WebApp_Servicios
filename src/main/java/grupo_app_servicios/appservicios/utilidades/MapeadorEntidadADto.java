package grupo_app_servicios.appservicios.utilidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;

/* 
 * Esta clase contiene métodos que deben ser estáticos y públicos, para poder ser usados solo los que se necesiten
 * y sin necesidad de instanciar la clase. 
 * Sirve para mapear las entidades y retornar el DTO que corresponda.
*/
public class MapeadorEntidadADto {
    @Autowired
    static ValoracionServicio vServicio;

    public static UsuarioDTO mapearUsuario(UsuarioEntidad usuarioEntidad) {
        UsuarioDTO usuarioMapeado = new UsuarioDTO();
        usuarioMapeado.setId(usuarioEntidad.getId());
        usuarioMapeado.setNombre(usuarioEntidad.getNombre());
        usuarioMapeado.setApellido(usuarioEntidad.getApellido());
        usuarioMapeado.setTelefono(usuarioEntidad.getTelefono());
        usuarioMapeado.setEmail(usuarioEntidad.getEmail());
        usuarioMapeado.setContrasena(usuarioEntidad.getContrasena());
        usuarioMapeado.setRol(usuarioEntidad.getRol());
        usuarioMapeado.setBarrios(usuarioEntidad.getBarrios());
        usuarioMapeado.setEstado(usuarioEntidad.getEstado());

        return usuarioMapeado;
    }

    public static ProveedorDTO mapearProveedor(ProveedorEntidad proveedorEntidad) {
        ProveedorDTO proveedorMapeado = new ProveedorDTO();
        proveedorMapeado.setId(proveedorEntidad.getId());
        proveedorMapeado.setDescripcion(proveedorEntidad.getDescripcion());
        proveedorMapeado.setMatricula(proveedorEntidad.getMatricula());
        if (proveedorEntidad.getUsuario() != null) {
            UsuarioDTO usuarioDTO = mapearUsuario(proveedorEntidad.getUsuario());
            proveedorMapeado.setUsuario(usuarioDTO);
        }
        if (proveedorEntidad.getFoto() != null) {
            ImagenProvDTO imagenProveedorDTO = mapearImagenProveedor(proveedorEntidad.getFoto());
            proveedorMapeado.setFoto(imagenProveedorDTO);
        }
        if (proveedorEntidad.getServicio() != null) {
            ServicioDTO servicio = mapearServicio(proveedorEntidad.getServicio());
            proveedorMapeado.setServicio(servicio);
        }
        if (proveedorEntidad.getSolicitudes() != null) {
            List<SolicitudDTO> listaSolicitudes = proveedorEntidad.getSolicitudes().stream().map(
                    solicitudEntidad -> mapearSolicitud(solicitudEntidad)).toList();

            proveedorMapeado.setSolicitudes(listaSolicitudes);

            // List<ValoracionDTO> valoraciones =
            // vServicio.listarPorProveedor(proveedorEntidad.getId());
            // System.out.println(valoraciones.toString());
            // PROMEDIO
            double promedio;
            double valor = 0;
            Integer i = 0;
            for (SolicitudDTO solicitudes : listaSolicitudes) {
                if (solicitudes.getIdValoracion() != null) {
                    valor = valor + solicitudes.getIdValoracion().getPuntaje();
                    i++;
                }
            }

            if (valor == 0) {
                promedio = 1;
            } else {
                promedio = valor / i;
            }
            proveedorMapeado.setPromedio(promedio);
        }

        return proveedorMapeado;
    }

    public static ImagenProvDTO mapearImagenProveedor(ImagenProvEntidad imagenProveedorEntidad) {
        ImagenProvDTO imagenMapeada = new ImagenProvDTO();
        imagenMapeada.setId(imagenProveedorEntidad.getId());
        imagenMapeada.setContenido(imagenProveedorEntidad.getContenido());
        imagenMapeada.setMime(imagenProveedorEntidad.getMime());
        imagenMapeada.setNombre(imagenProveedorEntidad.getNombre());

        return imagenMapeada;
    }

    public static ServicioDTO mapearServicio(ServicioEntidad servicioEntidad) {
        ServicioDTO servicioMapeado = new ServicioDTO();
        servicioMapeado.setId(servicioEntidad.getId());
        servicioMapeado.setNombre(servicioEntidad.getNombre());
        servicioMapeado.setEstado(servicioEntidad.getEstado());

        return servicioMapeado;
    }

    public static ValoracionDTO mapearValoracion(ValoracionEntidad valoracionEntidad) {
        ValoracionDTO valoracionMapeada = new ValoracionDTO();
        valoracionMapeada.setId(valoracionEntidad.getId());
        valoracionMapeada.setComentario(valoracionEntidad.getComentario());
        valoracionMapeada.setPuntaje(valoracionEntidad.getPuntaje());

        return valoracionMapeada;
    }

    public static SolicitudDTO mapearSolicitud(SolicitudEntidad solicitudEntidad) {
        SolicitudDTO solicitudMapeada = new SolicitudDTO();
        solicitudMapeada.setId(solicitudEntidad.getId());
        solicitudMapeada.setComentario(solicitudEntidad.getComentario());
        solicitudMapeada.setEstado(solicitudEntidad.getEstado());
        if (solicitudEntidad.getIdValoracion() != null) {
            ValoracionDTO valoracion = mapearValoracion(solicitudEntidad.getIdValoracion());
            solicitudMapeada.setIdValoracion(valoracion);
        }
        if (solicitudEntidad.getIdUsuario() != null) {
            UsuarioDTO usuario = mapearUsuario(solicitudEntidad.getIdUsuario());
            solicitudMapeada.setIdUsuario(usuario);
        }
        if (solicitudEntidad.getIdProveedor() != null) {
            solicitudMapeada.setIdProveedor(mapearSolicitudParaProveedor(solicitudEntidad.getIdProveedor()));
        }

        return solicitudMapeada;
    }

    public static ProveedorDTO mapearSolicitudParaProveedor(ProveedorEntidad proveedorEntidad) {
        ProveedorDTO proveedorMapeado = new ProveedorDTO();
        proveedorMapeado.setId(proveedorEntidad.getId());
        proveedorMapeado.setDescripcion(proveedorEntidad.getDescripcion());
        proveedorMapeado.setMatricula(proveedorEntidad.getMatricula());

        if (proveedorEntidad.getUsuario() != null) {
            UsuarioDTO usuarioDTO = mapearUsuario(proveedorEntidad.getUsuario());
            proveedorMapeado.setUsuario(usuarioDTO);
        }
        if (proveedorEntidad.getFoto() != null) {
            ImagenProvDTO imagenProveedorDTO = mapearImagenProveedor(proveedorEntidad.getFoto());
            proveedorMapeado.setFoto(imagenProveedorDTO);
        }
        if (proveedorEntidad.getServicio() != null) {
            ServicioDTO servicio = mapearServicio(proveedorEntidad.getServicio());
            proveedorMapeado.setServicio(servicio);
        }

        return proveedorMapeado;
    }

}
