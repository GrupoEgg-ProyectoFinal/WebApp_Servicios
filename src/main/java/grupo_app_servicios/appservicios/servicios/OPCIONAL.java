package grupo_app_servicios.appservicios.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.entidades.Servicio;

import grupo_app_servicios.appservicios.repositorios.ImagenProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
//Metodos
//CREAR PROVEEDOR
//BUSCAR PROVEEDOR POR ID
//ACTUALIZAR PROVEEDOR
//ELIMINAR PROVEEDOR
@Service
public class OPCIONAL {
    @Autowired
    ProveedorRepositorio pRepositorio;
    @Autowired
    ImagenProveedorRepositorio imgRepositorio;
    @Autowired
    ServicioRepositorio sRepositorio;
    @Autowired
    SolicitudRepositorio solicitudRepositorio;

    // CREAR PROVEEDOR
    public void crearProveedor(ProveedorDTO proveedorDTO) {
        Proveedor newProveedor = new Proveedor();

        newProveedor.setNombre(proveedorDTO.getNombre());
        newProveedor.setApellido(proveedorDTO.getApellido());
        newProveedor.setDescripcion(proveedorDTO.getDescripcion());
        newProveedor.setTelefono(proveedorDTO.getTelefono());
        newProveedor.setMatricula(proveedorDTO.getMatricula());
        newProveedor.setEmail(proveedorDTO.getEmail());
        newProveedor.setContrasena(proveedorDTO.getContraseña());

        // Asignar foto si está presente en el DTO
        if (proveedorDTO.getFoto() != null) {
            ImagenProveedor imagen = imgRepositorio.findById(proveedorDTO.getFoto().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Imagen no encontrada con ID: " + proveedorDTO.getFoto().getId()));
            newProveedor.setFoto(imagen);
        }

        // Asignar servicio si está presente en el DTO
        if (proveedorDTO.getServicio() != null) {
            Servicio servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
            newProveedor.setServicio(servicio);
        }

        // NO TIENE SOLICITUDES PORQUE ES NUEVO
        // Asignar solicitudes si están presentes en el DTO
        // if (proveedorDTO.getSolicitudes() != null &&
        // !proveedorDTO.getSolicitudes().isEmpty()) {
        // List<Solicitud> solicitudes = proveedorDTO.getSolicitudes().stream()
        // .map(solicitudDTO -> solicitudRepositorio.findById(solicitudDTO.getId())
        // .orElseThrow(() -> new RuntimeException(
        // "Solicitud no encontrada con ID: " + solicitudDTO.getId())))
        // .collect(Collectors.toList());
        // newProveedor.setSolicitudes(solicitudes);
        // }

        pRepositorio.save(newProveedor);
    }

    // BUSCAR PROVEEDOR POR ID
    @Transactional(readOnly = true)
    public Proveedor buscaProveedorId(UUID id) {
        return pRepositorio.findById(id).orElse(null);
    }

    // MODIFICAR PROVEEDOR
    @Transactional
    public void modificarProveedor(ProveedorDTO proveedorDTO) {
        // Se buscar por id y se guarda en un optional
        Optional<Proveedor> respuesta = pRepositorio.findById(proveedorDTO.getId());

        // Si el opctional tiene presente un resultado
        if (respuesta.isPresent()) {
            // Guardamos el resultado de la solicitud existente
            Proveedor proveedorExistente = respuesta.get();

            proveedorExistente.setNombre(proveedorDTO.getNombre());
            proveedorExistente.setApellido(proveedorDTO.getApellido());
            proveedorExistente.setDescripcion(proveedorDTO.getDescripcion());
            proveedorExistente.setTelefono(proveedorDTO.getTelefono());
            proveedorExistente.setMatricula(proveedorDTO.getMatricula());
            proveedorExistente.setEmail(proveedorDTO.getEmail());
            proveedorExistente.setContrasena(proveedorDTO.getContraseña());

            // Asignar foto si está presente en el DTO
            if (proveedorDTO.getFoto() != null) {
                ImagenProveedor imagen = imgRepositorio.findById(proveedorDTO.getFoto().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Imagen no encontrada con ID: " + proveedorDTO.getFoto().getId()));
                proveedorExistente.setFoto(imagen);
            }

            // Asignar servicio si está presente en el DTO
            if (proveedorDTO.getServicio() != null) {
                Servicio servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
                proveedorExistente.setServicio(servicio);
            }

        }

    }


    //ELIMINAR PROVEEDOR
    

}
