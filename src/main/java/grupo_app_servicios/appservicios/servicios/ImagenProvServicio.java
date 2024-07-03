package grupo_app_servicios.appservicios.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

    // CARGAR FOTO DE PERFIL
    @Transactional
    public ImagenProvEntidad guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                ImagenProvEntidad imagen = new ImagenProvEntidad();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imgRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;

            }
        } else {
            System.out.println("No se cargo la imagen pana");
            throw new MiExcepcion("El archivo proporcionado es nulo o está vacío");

        }
    }

    // ACTUALIZAR FOTO DE PERFIL
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

    // LISTAR TODAS LAS FOTOS DE LA BASE DE DATOS.
    @Transactional(readOnly = true)
    public List<ImagenProvEntidad> listarTodos() {
        return imgRepositorio.findAll();
    }


    public ImagenProvEntidad guardarImagenPredeterminada() throws MiExcepcion {
          try {
        // Cargar la imagen desde el classpath
        ClassPathResource resource = new ClassPathResource("static/img/usuarioLogo.png");
        
        // Obtener el contenido de la imagen como un array de bytes
        byte[] contenido = Files.readAllBytes(resource.getFile().toPath());

        ImagenProvEntidad imagen = new ImagenProvEntidad();
        imagen.setNombre("imagen_predeterminada.jpg");
        imagen.setMime("image/jpeg"); // Cambia esto según el formato de tu imagen
        imagen.setContenido(contenido);

        return imgRepositorio.save(imagen);
    } catch (IOException e) {
        throw new MiExcepcion("Error al cargar la imagen predeterminada: " + e.getMessage());
    }
        // try {
        
        //     // Ruta de la imagen predeterminada
        //     String rutaImagenPredeterminada = "/img/usuarioLogo.png";

        //     // Cargar la imagen desde el sistema de archivos
        //     Path ruta = Paths.get(rutaImagenPredeterminada);
        //     byte[] contenido = Files.readAllBytes(ruta);

        //     ImagenProvEntidad imagen = new ImagenProvEntidad();
        //     imagen.setNombre("imagen_predeterminada.jpg");
        //     imagen.setMime("image/jpeg"); // Cambia esto según el formato de tu imagen
        //     imagen.setContenido(contenido);

        //     return imgRepositorio.save(imagen);
        // } catch (IOException e) {
        //     throw new MiExcepcion("Error al cargar la imagen predeterminada: " + e.getMessage());
        // }
    }

}
