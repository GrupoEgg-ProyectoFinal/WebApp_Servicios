package grupo_app_servicios.appservicios.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.servicios.ImagenServicio;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private ImagenServicio imagenServicio;

    @PostMapping("/subir")
    public String subirImagen(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            ImagenProveedor imagen = imagenServicio.guardar(archivo);
            model.addAttribute("mensaje", "Imagen subida exitosamente");
        } catch (Exception e) {
            model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
        }
        return "resultadoImagen";
    }

    // Otros métodos del controlador para actualizar, listar, etc., según sea necesario
}
