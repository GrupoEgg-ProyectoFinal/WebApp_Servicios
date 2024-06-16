package grupo_app_servicios.appservicios.controladores;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.servicios.ImagenProvServicio;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;

@Controller
@RequestMapping("/imagen")
public class ImagenProvControlador {

    @Autowired
    private ImagenProvServicio imagenServicio;

    @PostMapping("/subir")
    public String subirImagen(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            imagenServicio.guardar(archivo);
            model.addAttribute("mensaje", "Imagen subida exitosamente");
        } catch (Exception e) {
            model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
        }
        return "resultadoImagen";
    }

    @Autowired
    ProveedorServicio2 pServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        ProveedorDTO proveedor = pServicio.buscaProveedorId(UUID.fromString(id));
        // para mostrarlo hay que guardarlo en un arreglo de bytes
        byte[] imagen = proveedor.getFoto().getContenido();

        //Cabezera del pedido le dice al navegador que lo que estamos devolviendo es una imagen.
        HttpHeaders headers = new HttpHeaders();
        //Hay que setearle el contenido y el tipo con el MediaType
        headers.setContentType(MediaType.IMAGE_PNG);
        //Estado en el que termina el proceso (404,200,500) para devolverlo
        //HttpStatus.OK = 200
        //Respondemos el REntity con esos tres parametros.
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
}