package grupo_app_servicios.appservicios.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.ProveedorServicio2;
import grupo_app_servicios.appservicios.servicios.ServicioServicio;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    ProveedorServicio2 pServicio;
    @Autowired
    UsuarioServicio uServicio;
    @Autowired
    ServicioServicio sServicio;

    // @Autowired
    // ProveedorServicio pServicio;

    @GetMapping("/") // Acá es donde realizamos el mapeo
    public String index(Model modelo) {
        modelo.addAttribute("servicioDTO", new ServicioDTO());
        return "index.html"; // Acá es que retornamos con el método.
    }

    // REGISTRO DE USUARIO
    @GetMapping("/registrarUsuario")
    public String registrar(Model model) {
        // Inicializa un nuevo objeto UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        // Agrega el objeto usuarioDTO al modelo
        model.addAttribute("usuarioDTO", usuarioDTO);
        model.addAttribute("contrasena2", ""); // verificar que funcione
        return "registroUsuario.html";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, String contrasena2) {
        uServicio.crearUsuario(usuarioDTO, contrasena2);
        return "redirect:/";
    }

    // REGISTRO DE PROVEEDOR
    @GetMapping("/registrarProveedor")
    public String registrarProveedor(Model model) {
        // Inicializa un nuevo objeto ProveedorDTO y le asigno un UsuarioDTO vacío
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        proveedorDTO.setUsuario(usuarioDTO);

        List<ServicioDTO> listaDeServicios = sServicio.listarServicios();
        // Agrega el objeto proveedorDTO al modelo,
        // junto con la lista de servicios disponibles para que se pueda asignar uno desde el front
        model.addAttribute("proveedorDTO", proveedorDTO);
        model.addAttribute("servicios", listaDeServicios);
        return "registroProveedor.html";
    }

    @PostMapping("/guardarProveedor")
    public String registrarProveedor(@ModelAttribute ProveedorDTO proveedorDTO, MultipartFile imagenFile, Model model,
            String idServicio) {
        // System.out.println(proveedorDTO.toString());
        try {
            // busca el servicio seleccionado y se asigno al proveedor DTO
            proveedorDTO.setServicio(sServicio.buscarServicioPorId(UUID.fromString(idServicio)));

            pServicio.crearProveedor(proveedorDTO, imagenFile);
            model.addAttribute("mensaje", "Proveedor registrado exitosamente");
        } catch (MiExcepcion e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/registrarProveedor";
        }
        return "index.html";
    }

    // LOGIN DE USUARIO
    @GetMapping("/login")
    public String inicioSesion() {
        return "sesionUsuario.html";
    }

    // LOGIN DE PROVEEDOR
    @GetMapping("/loginProveedor")
    public String inicioSesionProveedor() {
        return "sesionProveedor.html";
    }

    // PERFIL USUARIO
    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRol('ROL_USER', 'ROL_PROVEEDOR','ROL_ADMIN')")
    public String inicio(HttpSession session, Model modelo) {
        UsuarioEntidad loguedUser = (UsuarioEntidad) session.getAttribute("usuarioEnSesion");
        // Ver cómo hacer para que tambien se aplique en el proveedor tambien
        // ej: con el rol de proveedor en usuario. (Habria que modificar la entidad de
        // proveedor y reveer el tema de inicio de sesion)

        String role = loguedUser.getRol().toString();
        List<ProveedorDTO> plomeros = pServicio.listarProveedoresSegunServicio("Plomeria");
        List<ProveedorDTO> electricistas = pServicio.listarProveedoresSegunServicio("Electricista");
        List<ProveedorDTO> gasistas = pServicio.listarProveedoresSegunServicio("Gasista");
        modelo.addAttribute("plomeros", plomeros);
        modelo.addAttribute("electricistas", electricistas);
        modelo.addAttribute("gasistas", gasistas);

        if (role.equals("ADMIN")) {
            return "redirect:/dashboard";
        }
        
        if (role.equals("PROVEEDOR")) {

            
            return "vistaProveedor.html";
        }

        return "vistaUsuario.html"; // después cambiarlo por la vista de perfil de usuario
    }


    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRol('ROL_ADMIN')")
    public String panelAdministrativo(HttpSession session,Model modelo) {
        List<ProveedorDTO> proveedores2 = pServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores2);

        List<UsuarioDTO> usuarios = uServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "vistaAdmin.html";
    }

    // CONOCENOS
    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos.html";
    }

    //AYUDA
    @GetMapping("/ayuda")
    public String ayuda(){
        return "ayuda.html";
    }
}
