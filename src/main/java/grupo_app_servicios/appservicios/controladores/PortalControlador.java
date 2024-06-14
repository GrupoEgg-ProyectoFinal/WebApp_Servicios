package grupo_app_servicios.appservicios.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    ProveedorServicio2 opcional;

    @Autowired
    UsuarioServicio uServicio;

    // @Autowired
    // ProveedorServicio pServicio;

    @GetMapping("/") // Acá es donde realizamos el mapeo
    public String index(ModelMap modelo) {
        List<ProveedorDTO> proveedores = opcional.listarProveedores();

        modelo.addAttribute("proveedores", proveedores);
        return "index.html"; // Acá es que retornamos con el método.
    }

    // IR A REGISTRO DE USUARIO
    @GetMapping("/registrar")
    public String registrar(Model model) {
        // Inicializa un nuevo objeto UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        // Agrega el objeto usuarioDTO al modelo
        model.addAttribute("usuarioDTO", usuarioDTO);
        model.addAttribute("contrasena2", ""); // verificar que funcione
        return "registrousuario.html";
    }

    // IR AL REGISTRO DE PROVEEDOR
    @GetMapping("/registrar")
    public String registrarProveedor(Model model) {
        // Inicializa un nuevo objeto UsuarioDTO
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        // Agrega el objeto usuarioDTO al modelo
        model.addAttribute("proveedorDTO", proveedorDTO);
        model.addAttribute("contrasena2", ""); // verificar que funcione
        return "registoproveedor.html";
    }

    // REGISTRAR PROVEEDOR
    @PostMapping("/registrarProveedor")
    public String registrarProveedor(@ModelAttribute ProveedorDTO proveedorDTO,
            @RequestParam("imagenFile") MultipartFile imagenFile, Model model) {
        try {
            opcional.crearProveedor(proveedorDTO, imagenFile);
            model.addAttribute("mensaje", "Proveedor registrado exitosamente");
        } catch (MiExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index.html";
    }
    // @PostMapping("/registrarProveedor")
    // public String registrarProveedor(@ModelAttribute ProveedorDTO
    // proveedorDTO,@RequestParam("foto") MultipartFile foto,Model model) {
    // try {
    // // Crear ImagenProveedorDTO a partir del MultipartFile
    // ImagenProveedorDTO imagenDTO = new ImagenProveedorDTO();
    // imagenDTO.setFormato(foto.getContentType());
    // imagenDTO.setNombre(foto.getOriginalFilename());
    // imagenDTO.setContenido(foto.getBytes());

    // // Asignar la imagen al ProveedorDTO
    // proveedorDTO.setFoto(imagenDTO);

    // // Llamar al servicio para registrar el proveedor
    // opcional.crearProveedor(proveedorDTO);
    // model.addAttribute("mensaje", "Proveedor registrado exitosamente");
    // } catch (Exception e) {
    // model.addAttribute("error", e.getMessage());
    // }
    // return "resultado"; // Esta es la vista que se mostrará después de registrar
    // al proveedor
    // }

    @GetMapping("/login")
    public String inicioSesion() {
        return "iniciarSesion.html";
    }

    @GetMapping("/loginProveedor")
    public String inicioSesionProveedor() {
        return "sesionProveedor.html";
    }
    
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, String contrasena2) {
        uServicio.crearUsuario(usuarioDTO, contrasena2);
        return "redirect:/";
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRol('ROL_USER', 'ROL_ADMIN')")
    public String inicio(HttpSession session) {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        String role = loguedUser.getRol().toString();

        if (role.equals("ADMIN"))
            return "redirect:/dashboard";
        return "index.html"; // después cambiarlo por la vista de perfil de usuario
    }
}
