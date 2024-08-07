package grupo_app_servicios.appservicios.servicios;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.utilidades.Validaciones;

@Service
public class ServicioServicio {
    @Autowired
    ServicioRepositorio sRepositorio;

    // UTILIDADES DE LA CLASE
    private ServicioEntidad mapearDTOEntidad(ServicioDTO dtoAMapear) {
        ServicioEntidad entidadMapeada = new ServicioEntidad();
        entidadMapeada.setId(dtoAMapear.getId());
        entidadMapeada.setNombre(dtoAMapear.getNombre());
        entidadMapeada.setEstado(dtoAMapear.getEstado());

        return entidadMapeada;
    }

    private ServicioDTO mapearEntidadDTO(ServicioEntidad entidadAMapear) {
        ServicioDTO dtoMapeado = new ServicioDTO();
        dtoMapeado.setId(entidadAMapear.getId());
        dtoMapeado.setNombre(entidadAMapear.getNombre());
        dtoMapeado.setEstado(entidadAMapear.getEstado());

        return dtoMapeado;
    }

    private List<ServicioDTO> mapearListaEntidadesDTO(List<ServicioEntidad> listaAMapear) {
        List<ServicioDTO> listaMapeada = listaAMapear.stream().map(
            this::mapearEntidadDTO
        ).collect(Collectors.toList());

        return listaMapeada;
    }

    // MÉTODOS DE OBTENCIÓN
    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServicios() {
        return mapearListaEntidadesDTO(sRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServiciosPorNombre(String nombre) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(nombre, "nombre del servicio");

        return mapearListaEntidadesDTO(sRepositorio.buscarPorNombre(nombre));
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServiciosPorEstadoDeAlta(Boolean valor) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(valor, "estado de alta");

        return mapearListaEntidadesDTO(sRepositorio.buscarPorEstadoDeAlta(valor));
    }

    @Transactional(readOnly = true)
    public ServicioDTO buscarServicioPorId(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id del servicio");

        ServicioEntidad servicio = sRepositorio.findById(id).orElseThrow(
            () -> new MiExcepcion("No se encontró un servicio con la id " + id.toString())
        );

        return mapearEntidadDTO(servicio);
    }

    // CREACIÓN
    @Transactional
    public void crearServicio(ServicioDTO servicio) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(servicio.getNombre(), "nombre");

        servicio.setEstado(true);
        sRepositorio.save(mapearDTOEntidad(servicio));
    }
    
    // MODIFICACIÓN
    @Transactional
    public void modificarServicio(ServicioDTO servicio) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(servicio.getNombre(), "nombre");
        Validaciones.validarSiCampoEsNulo(servicio.getId(), "id");

        sRepositorio.findById(servicio.getId()).orElseThrow(
            () -> new MiExcepcion("No se encontró un servicio con la id " + servicio.getId().toString())
        );

        sRepositorio.save(mapearDTOEntidad(servicio));
    }

    // DAR DE BAJA Y ELIMINAR
    @Transactional
    public ServicioDTO cambiarEstadoDeAlta(Boolean valor, UUID id) throws MiExcepcion {
        Validaciones.validarVariosCampos(new String[]{"estado de alta", "id del servicio"}, valor, id);

        ServicioEntidad servicio = sRepositorio.findById(id).orElseThrow(
            () -> new MiExcepcion("No se encontró un servicio con la id " + id.toString())
        );
        servicio.setEstado(valor);

        sRepositorio.save(servicio);

        return mapearEntidadDTO(servicio);
    }

    @Transactional
    public void eliminarServicio(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id del servicio");

        sRepositorio.findById(id).orElseThrow(
            () -> new MiExcepcion("No se encontró un servicio con la id " + id.toString())
        );

        sRepositorio.deleteById(id);
    }
}
