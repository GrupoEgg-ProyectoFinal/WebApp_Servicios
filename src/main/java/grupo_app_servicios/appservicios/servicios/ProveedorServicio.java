package grupo_app_servicios.appservicios.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.entidades.Servicio;
import grupo_app_servicios.appservicios.repositorios.ImagenProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorDtoAEntidad;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;

@Service
public class ProveedorServicio {
    @Autowired
    ProveedorRepositorio pRepositorio;
    @Autowired
    ImagenProveedorRepositorio imgProveedorRepositorio;
    @Autowired
    ServicioRepositorio sRepositorio;

    private List<ProveedorDTO> mapearListaEntidadesADto(List<Proveedor> listaDeEntidades) {
        // Se creó este método ya que se utiliza en otros métodos dentro de esta clase,
        // y así hacer más legible el código a través de la reutilización.

        // Con el .stream().map() y despúes el .toList() se crea una nueva lista y a
        // cada
        // elemento se le aplica la funcion de mapeado que retorna el ProveedorDTO
        return listaDeEntidades.stream().map(
                proveedor -> MapeadorEntidadADto.mapearProveedor(proveedor)).toList();
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerTodos() {
        List<Proveedor> listaDeEntidades = pRepositorio.findAll();
        List<ProveedorDTO> listaDtosMapeados = mapearListaEntidadesADto(listaDeEntidades);

        return listaDtosMapeados;
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerProveedoresSegunServicio(String nombreServicio) {
        List<Proveedor> listaDeEntidades = pRepositorio.obtenerProveedoresPorServicio(nombreServicio);
        List<ProveedorDTO> listaDtosMapeados = mapearListaEntidadesADto(listaDeEntidades);

        return listaDtosMapeados;
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerProveedoresSegunNombreYApellido(String valorBusqueda) {
        List<Proveedor> listaDeEntidades = pRepositorio.obtenerProveedoresPorNombreYOApellido(valorBusqueda);
        List<ProveedorDTO> listaDtosMapeados = mapearListaEntidadesADto(listaDeEntidades);

        return listaDtosMapeados;
    }

    @Transactional
    public void crear(ProveedorDTO proveedorDTO) {
        Proveedor dtoMapeadoAEntidad = MapeadorDtoAEntidad.mapearProveedor(proveedorDTO);
        // Asignar servicio si está presente en el DTO
        if (proveedorDTO.getServicio() != null) {
            Servicio servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
            dtoMapeadoAEntidad.setServicio(servicio);
        }
        pRepositorio.save(dtoMapeadoAEntidad);
    }

    @Transactional
    public void modificar(ProveedorDTO proveedor) {
        // El .findById() retorna un Optional<Proveedor> pero al ponerle el
        // .orElseThrow(), con la excepción
        // que corresponda dentro, retornaría directamente la entidad del optional. En
        // caso de que la búsqueda no
        // dé resultado se arrojaría la excepción

        // Una RuntimeException en pocas palabras es una excepción que puede ser lanzada
        // durante la ejecución solamente, y no necesariamente
        // frenaría la aplicación. La excepción común controla que el código esté bien y
        // en caso de ser lanzada (ya sea en compilación o ejecución),
        // la aplicación se detendría hasta solucionar ese problema.
        Proveedor dtoMapeadoAEntidad = pRepositorio.findById(proveedor.getId()).orElseThrow(
                () -> new RuntimeException("No se ha encontrado un proveedor con la id seleccionada")
        // reemplazar la runtimeException por una excepcion personalizada en el futuro
        );

        // MapeadorDtoAEntidad.mapearProveedor(proveedor);
        pRepositorio.save(dtoMapeadoAEntidad);
    }
}
