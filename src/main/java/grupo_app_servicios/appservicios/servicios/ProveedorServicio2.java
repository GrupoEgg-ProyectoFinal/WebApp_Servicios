package grupo_app_servicios.appservicios.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ImagenProvDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ImagenProvRepositorio;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;

//Metodos
//CREAR PROVEEDOR
//BUSCAR PROVEEDOR POR ID
//ACTUALIZAR PROVEEDOR
//ELIMINAR PROVEEDOR
@Service
public class ProveedorServicio2 {
    @Autowired
    ProveedorRepositorio pRepositorio;
    @Autowired
    ImagenProvRepositorio imgRepositorio;
    @Autowired
    ServicioRepositorio sRepositorio;
    @Autowired
    SolicitudRepositorio solicitudRepositorio;
    @Autowired
    ImagenProvServicio imgServicio;

    // CREAR PROVEEDOR

    @Transactional
    public void crearProveedor(ProveedorDTO proveedorDTO, MultipartFile imagenFile) throws MiExcepcion {
        try {
            // Crear una nueva instancia de Proveedor a partir de ProveedorDTO
            ProveedorEntidad proveedor = new ProveedorEntidad();
            proveedor.setNombre(proveedorDTO.getNombre());
            proveedor.setApellido(proveedorDTO.getApellido());
            proveedor.setTelefono(proveedorDTO.getTelefono());
            proveedor.setMatricula(proveedorDTO.getMatricula());
            proveedor.setEmail(proveedorDTO.getEmail());
            proveedor.setContrasena(proveedorDTO.getContrasena());
            proveedor.setDescripcion(proveedorDTO.getDescripcion());
            // Asignar la imagen al proveedor si se proporcionó una en el formulario
            if (imagenFile != null && !imagenFile.isEmpty()) {
                ImagenProvEntidad imagen = imgServicio.guardar(imagenFile);
                proveedor.setFoto(imagen);
            }
            //IMPORTANTE: falta setearle el servicio y las solicitudes cuando las tenga.
            // Asignar servicio si está presente en el DTO
            if (proveedorDTO.getServicio() != null) {
                ServicioEntidad servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
                proveedor.setServicio(servicio);
            }

            // Guardar el proveedor en la base de datos
            pRepositorio.save(proveedor);
        } catch (MiExcepcion e) {
            throw new MiExcepcion("Error al guardar la imagen: " + e.getMessage());
        }
    }

    // MÉTODOS DE BÚSQUEDA (READ)
    // LISTAR TODOS
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarProveedores() {
        List<ProveedorEntidad> proveedores = new ArrayList<>();

        proveedores = pRepositorio.findAll();

        return proveedores.stream().map(
                proveedor -> MapeadorEntidadADto.mapearProveedor(proveedor)).toList();
    }
    // BUSCAR PROVEEDOR POR ID
    @Transactional(readOnly = true)
    public ProveedorDTO buscaProveedorId(UUID id) {
        return MapeadorEntidadADto.mapearProveedor(pRepositorio.findById(id).orElse(null));
    }

    //
    // MODIFICAR PROVEEDOR
    @Transactional
    public void modificarProveedor(ProveedorDTO proveedorDTO) {
        // Se buscar por id y se guarda en un optional
        Optional<ProveedorEntidad> respuesta = pRepositorio.findById(proveedorDTO.getId());

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            ProveedorEntidad proveedorExistente = respuesta.get();

            proveedorExistente.setNombre(proveedorDTO.getNombre());
            proveedorExistente.setApellido(proveedorDTO.getApellido());
            proveedorExistente.setDescripcion(proveedorDTO.getDescripcion());
            proveedorExistente.setTelefono(proveedorDTO.getTelefono());
            proveedorExistente.setMatricula(proveedorDTO.getMatricula());
            proveedorExistente.setEmail(proveedorDTO.getEmail());
            proveedorExistente.setContrasena(proveedorDTO.getContrasena());

            // Asignar foto si está presente en el DTO
            if (proveedorDTO.getFoto() != null) {
                ImagenProvEntidad imagen = imgRepositorio.findById(proveedorDTO.getFoto().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Imagen no encontrada con ID: " + proveedorDTO.getFoto().getId()));
                proveedorExistente.setFoto(imagen);
            }

            // Asignar servicio si está presente en el DTO
            if (proveedorDTO.getServicio() != null) {
                ServicioEntidad servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
                proveedorExistente.setServicio(servicio);
            }

        }

    }

    // ELIMINAR PROVEEDOR



    public ImagenProvDTO obtenerImagenPorId(UUID id) {
        Optional<ProveedorEntidad> proveedorOptional = pRepositorio.findById(id);
        if (proveedorOptional.isPresent()) {
            ProveedorEntidad proveedor = proveedorOptional.get();
            ImagenProvEntidad imagenProv = proveedor.getFoto();
            ImagenProvDTO imagenProvDTO = new ImagenProvDTO();
            imagenProvDTO.setId(imagenProv.getId());
            imagenProvDTO.setContenido(imagenProv.getContenido());
            imagenProvDTO.setFormato(imagenProv.getMime());
            imagenProvDTO.setNombre(imagenProv.getNombre());
            return imagenProvDTO;
        } else {
            throw new RuntimeException("Imagen no encontrada para el proveedor con id: " + id);
        }
    }

}
