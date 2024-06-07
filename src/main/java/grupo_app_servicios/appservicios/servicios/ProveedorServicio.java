package grupo_app_servicios.appservicios.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo_app_servicios.appservicios.Dto.ImagenProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.repositorios.ImagenProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;

@Service
public class ProveedorServicio {
    @Autowired
    ProveedorRepositorio proveedorRepositorio;
    @Autowired
    ImagenProveedorRepositorio imagenProveedorRepositorio;

    private ProveedorDTO mapearEntidadADto(Proveedor proveedor) {
        ProveedorDTO proveedorMapeado = new ProveedorDTO();
        proveedorMapeado.setId(proveedor.getId());
        proveedorMapeado.setNombre(proveedor.getNombre());
        proveedorMapeado.setApellido(proveedor.getApellido());
        proveedorMapeado.setEmail(proveedor.getEmail());
        proveedorMapeado.setContrasena(proveedor.getContrasena());

        proveedorMapeado.setDescripcion(proveedor.getDescripcion());
        proveedorMapeado.setTelefono(proveedor.getTelefono());
        proveedorMapeado.setMatricula(proveedor.getMatricula());

        if (proveedor.getFoto() != null) {
            ImagenProveedor imagenProveedor = proveedor.getFoto();

            ImagenProveedorDTO imagenProveedorDTO = new ImagenProveedorDTO();
            // acá se mapearía un dto de imagenProveedor y se le asignaria al setter
            proveedorMapeado.setFoto(imagenProveedorDTO); 
        }

        return proveedorMapeado;
    }

    private Proveedor mapearDtoAEntidad(ProveedorDTO proveedor) {
        Proveedor proveedorMapeado = new Proveedor();
        proveedorMapeado.setId(proveedor.getId());
        proveedorMapeado.setNombre(proveedor.getNombre());
        proveedorMapeado.setApellido(proveedor.getApellido());
        proveedorMapeado.setEmail(proveedor.getEmail());
        proveedorMapeado.setContrasena(proveedor.getContrasena());

        proveedorMapeado.setDescripcion(proveedor.getDescripcion());
        proveedorMapeado.setTelefono(proveedor.getTelefono());
        proveedorMapeado.setMatricula(proveedor.getMatricula());

        if (proveedor.getFoto() != null) {
            ImagenProveedorDTO imagenProveedorDTO = proveedor.getFoto();

            ImagenProveedor imagenProveedor = new ImagenProveedor();
            // acá se mapearía una entidad de imagenProveedor y se le asignaria al setter
            proveedorMapeado.setFoto(imagenProveedor); 
        }

        return proveedorMapeado;
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerTodos() {
        // Como proveedorRepositorio.findAll retorna las entidades de Proveedor, se crea una nueva lista en base a esa
        // con el .stream().map() y a cada elemento le aplica la funcion que retorna el ProveedorDTO mapeado
        return proveedorRepositorio.findAll().stream().map(
            proveedor -> mapearEntidadADto(proveedor)
        ).toList();
    }

    @Transactional
    public void crear(ProveedorDTO proveedor) {
        proveedorRepositorio.save(mapearDtoAEntidad(proveedor));
    }
}
