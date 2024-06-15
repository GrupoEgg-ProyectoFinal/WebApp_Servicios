package grupo_app_servicios.appservicios.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.ImagenProvRepositorio;

@Service
public class ImagenProvServicio {
    @Autowired
    private ImagenProvRepositorio imgRepositorio;

    //CARGAR FOTO DE PERFIL
    @Transactional
    public ImagenProvEntidad guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                ImagenProvEntidad imagen = new ImagenProvEntidad();
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
    public ImagenProvEntidad actualizarImg(MultipartFile archivo, UUID idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                ImagenProvEntidad imagen = new ImagenProvEntidad();
                if (idImagen != null) {
                    Optional<ImagenProvEntidad> respuesta = imgRepositorio.findById(idImagen);
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
    public List<ImagenProvEntidad> listarTodos() {
        return imgRepositorio.findAll();
    }

}
