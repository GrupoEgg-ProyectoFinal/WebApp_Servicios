package grupo_app_servicios.appservicios.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ImagenProveedorRepositorio;

public class ImagenServicio {
    @Autowired
    private ImagenProveedorRepositorio imgRepositorio;


    //CARGAR FOTO DE PERFIL
    @Transactional
    public ImagenProveedor guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                ImagenProveedor imagen = new ImagenProveedor();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imgRepositorio.save(imagen);
            } catch (IOException e) {
                throw new MiExcepcion("Error al obtener los bytes de la imagen: " + e.getMessage());
            }
        } else {
            throw new MiExcepcion("El archivo proporcionado es nulo o está vacío");
        }
    }



    //ACTUALIZAR FOTO DE PERFIL
    public ImagenProveedor actualizarImg(MultipartFile archivo, UUID idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                ImagenProveedor imagen = new ImagenProveedor();
                if (idImagen != null) {
                    Optional<ImagenProveedor> respuesta = imgRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imgRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    //LISTAR TODAS LAS FOTOS DE LA BASE DE DATOS.
    @Transactional(readOnly = true)
    public List<ImagenProveedor> listarTodos() {
        return imgRepositorio.findAll();
    }

}
