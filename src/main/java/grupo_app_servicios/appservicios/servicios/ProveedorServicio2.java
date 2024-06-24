package grupo_app_servicios.appservicios.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ImagenProvDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ImagenProvRepositorio;
import grupo_app_servicios.appservicios.repositorios.ProveedorRepositorio;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;
import grupo_app_servicios.appservicios.repositorios.SolicitudRepositorio;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
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
    UsuarioRepositorio uRepositorio;
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
    public void crearProveedor(ProveedorDTO proveedorDTO, MultipartFile imagenFile)
            throws MiExcepcion {
        try {
            // Crear una nueva instancia de Proveedor a partir de ProveedorDTO
            ProveedorEntidad proveedor = new ProveedorEntidad();
            
            // Obtiene el usuarioDTO que se le asignó desde el controlador y el front,
            // y después mapea un usuarioEntidad en base a ese
            UsuarioDTO usuarioDTO = proveedorDTO.getUsuario();
            UsuarioEntidad datosDeUsuario = new UsuarioEntidad();
            datosDeUsuario.setNombre(usuarioDTO.getNombre());
            datosDeUsuario.setApellido(usuarioDTO.getApellido());
            datosDeUsuario.setTelefono(usuarioDTO.getTelefono());
            datosDeUsuario.setEmail(usuarioDTO.getEmail());
            datosDeUsuario.setContrasena(new BCryptPasswordEncoder().encode(usuarioDTO.getContrasena()));
            datosDeUsuario.setRol(Rol.PROVEEDOR);
            datosDeUsuario.setBarrios(Barrios.PROVEEDOR);
            datosDeUsuario.setEstado(true);

            //guarda esta entidad de usuario y despues lo setea en la entidad de proveedor que se está haciendo
            uRepositorio.save(datosDeUsuario);
            proveedor.setUsuario(datosDeUsuario);

            proveedor.setMatricula(proveedorDTO.getMatricula());
            proveedor.setDescripcion(proveedorDTO.getDescripcion());
            // Asignar la imagen al proveedor si se proporcionó una en el formulario
            if (imagenFile != null && !imagenFile.isEmpty()) {
                ImagenProvEntidad imagen = imgServicio.guardar(imagenFile);
                proveedor.setFoto(imagen);
                // <img th:src="@{'/imagen/perfil/' + *{id.toString()}}" alt="Foto del proveedor">
            }
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
            proveedor -> MapeadorEntidadADto.mapearProveedor(proveedor)
        ).toList();
    }

    // LISTAR SEGÚN SERVICIO
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarProveedoresSegunServicio(String nombreServicio) {
        List<ProveedorEntidad> proveedores = new ArrayList<>();

        proveedores = pRepositorio.buscarProveedoresPorServicio(nombreServicio);

        return proveedores.stream().map(
            proveedor -> MapeadorEntidadADto.mapearProveedor(proveedor)
        ).toList();
    }

    // BUSCAR PROVEEDOR POR ID
    @Transactional(readOnly = true)
    public ProveedorDTO buscaProveedorId(UUID id) {
        return MapeadorEntidadADto.mapearProveedor(pRepositorio.findById(id).orElse(null));
    }

    //
    // MODIFICAR PROVEEDOR
    @Transactional
    public void modificarProveedor(ProveedorDTO proveedorDTO, UsuarioDTO usuarioDTO, MultipartFile imagenFile) throws MiExcepcion {
        // Se buscar por id y se guarda en un optional
        ProveedorEntidad proveedor = pRepositorio.findById(proveedorDTO.getId()).orElse(null);
        UsuarioEntidad datosDeUsuario = new UsuarioEntidad();

        datosDeUsuario.setNombre(usuarioDTO.getNombre());
        datosDeUsuario.setApellido(usuarioDTO.getApellido());
        datosDeUsuario.setTelefono(usuarioDTO.getTelefono());
        datosDeUsuario.setEmail(usuarioDTO.getEmail());
        datosDeUsuario.setContrasena(new BCryptPasswordEncoder().encode(usuarioDTO.getContrasena()));

        proveedor.setMatricula(proveedorDTO.getMatricula());
        proveedor.setDescripcion(proveedorDTO.getDescripcion());
        datosDeUsuario.setRol(Rol.PROVEEDOR);

        // Asignar la imagen al proveedor si se proporcionó una en el formulario
        if (imagenFile != null && !imagenFile.isEmpty()) {
            ImagenProvEntidad imagen = imgServicio.guardar(imagenFile);
            proveedor.setFoto(imagen);
        }
        // IMPORTANTE: falta setearle el servicio y las solicitudes cuando las tenga.
        // Asignar servicio si está presente en el DTO
        if (proveedorDTO.getServicio() != null) {
            ServicioEntidad servicio = sRepositorio.findById(proveedorDTO.getServicio().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Servicio no encontrado con ID: " + proveedorDTO.getServicio().getId()));
            proveedor.setServicio(servicio);
        }
    }

    // ELIMINAR PROVEEDOR
    @Transactional
    public void eliminarProveedorPorIdDeUsuario(UUID usuarioId) {
        ProveedorEntidad proveedor = pRepositorio.buscarPorIdUsuario(usuarioId);
        pRepositorio.deleteById(proveedor.getId());
    }


    // metodo que por el momento no se utiliza (cuándo se pueda aplicar la visualizacion de imagenes ver si hace falta)
    public ImagenProvDTO obtenerImagenPorId(UUID id) {
        Optional<ProveedorEntidad> proveedorOptional = pRepositorio.findById(id);
        if (proveedorOptional.isPresent()) {
            ProveedorEntidad proveedor = proveedorOptional.get();
            ImagenProvEntidad imagenProv = proveedor.getFoto();
            ImagenProvDTO imagenProvDTO = new ImagenProvDTO();
            imagenProvDTO.setId(imagenProv.getId());
            imagenProvDTO.setContenido(imagenProv.getContenido());
            imagenProvDTO.setMime(imagenProv.getMime());
            imagenProvDTO.setNombre(imagenProv.getNombre());
            return imagenProvDTO;
        } else {
            throw new RuntimeException("Imagen no encontrada para el proveedor con id: " + id);
        }
    }
}
