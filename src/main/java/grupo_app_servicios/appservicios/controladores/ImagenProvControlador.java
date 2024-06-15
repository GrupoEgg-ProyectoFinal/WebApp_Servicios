package grupo_app_servicios.appservicios.controladores;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import grupo_app_servicios.appservicios.Dto.ImagenProvDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.servicios.ImagenProvServicio;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/imagen")
public class ImagenProvControlador {

    @Autowired
    private ImagenProvServicio imagenServicio;

    @PostMapping("/subir")
    public String subirImagen(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            ImagenProvEntidad imagen = imagenServicio.guardar(archivo);
            model.addAttribute("mensaje", "Imagen subida exitosamente");
        } catch (Exception e) {
            model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
        }
        return "resultadoImagen";
    }

    @Autowired
    ProveedorServicio2 pServicio;

    @GetMapping("/imagenes/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable UUID id) {
        ProveedorEntidad proveedor = pServicio.buscaProveedorId(id);
        if (proveedor != null && proveedor.getFoto() != null) {
            byte[] imagen = proveedor.getFoto().getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
    
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } else {
            // Manejar el caso en que no se encuentre la imagen
            return ResponseEntity.notFound().build();
        }
    }
}