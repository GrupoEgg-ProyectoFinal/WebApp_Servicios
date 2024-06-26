package grupo_app_servicios.appservicios.servicios;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorDtoAEntidad;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;

@Service
public class ServicioServicio {
    @Autowired
    ServicioRepositorio sRepositorio;
    @Autowired
    ImagenProvServicio imgServicio;

    private List<ServicioDTO> mapearListaEntidadesDTO(List<ServicioEntidad> listaAMapear) {
        List<ServicioDTO> listaMapeada = listaAMapear.stream().map(
            servicio -> MapeadorEntidadADto.mapearServicio(servicio)
        ).collect(Collectors.toList());

        return listaMapeada;
    }

    // MÉTODOS DE OBTENCIÓN
    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServicios() {
        return mapearListaEntidadesDTO(sRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServiciosPorNombre(String nombre) {
        return mapearListaEntidadesDTO(sRepositorio.buscarPorNombre(nombre));
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServiciosPorEstadoDeAlta(Boolean valor) {
        return mapearListaEntidadesDTO(sRepositorio.buscarPorEstadoDeAlta(valor));
    }

    @Transactional(readOnly = true)
    public ServicioDTO buscarServicioPorId(UUID id) {
        ServicioEntidad servicio = sRepositorio.findById(id).orElseThrow(
            () -> new RuntimeException("No se encontró un servicio con la id " + id.toString())
        );

        return MapeadorEntidadADto.mapearServicio(servicio);
    }

    // CREACIÓN
    @Transactional
    public void crearServicio(ServicioDTO servicio, MultipartFile imagen) throws MiExcepcion {
        servicio.setEstado(true);
        if (imagen != null) {
            ImagenProvEntidad imagenEntidad = imgServicio.guardar(imagen);
            servicio.setImagen(MapeadorEntidadADto.mapearImagenProveedor(imagenEntidad));
        }
        sRepositorio.save(MapeadorDtoAEntidad.mapearServicio(servicio));
    }
    
    // MODIFICACIÓN
    @Transactional
    public void modificarServicio(ServicioDTO servicio) {
        sRepositorio.findById(servicio.getId()).orElseThrow(
            () -> new RuntimeException("No se encontró un servicio con la id " + servicio.getId().toString())
        );

        sRepositorio.save(MapeadorDtoAEntidad.mapearServicio(servicio));
    }

    // DAR DE BAJA Y ELIMINAR
    @Transactional
    public ServicioDTO cambiarEstadoDeAlta(Boolean valor, UUID id) {
        ServicioEntidad servicio = sRepositorio.findById(id).orElseThrow(
            () -> new RuntimeException("No se encontró un servicio con la id " + id.toString())
        );
        servicio.setEstado(valor);

        sRepositorio.save(servicio);

        return MapeadorEntidadADto.mapearServicio(servicio);
    }

    @Transactional
    public void eliminarServicio(UUID id) {
        sRepositorio.findById(id).orElseThrow(
            () -> new RuntimeException("No se encontró un servicio con la id " + id.toString())
        );

        sRepositorio.deleteById(id);
    }
}
