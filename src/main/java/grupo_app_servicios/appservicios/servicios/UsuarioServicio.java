package grupo_app_servicios.appservicios.servicios;


import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    // UTILIDADES DE LA CLASE 
    private void validar(String nombre, String email) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede estar vacío");
        }

        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío");
        }
    }

    private UsuarioDTO convertirADTO(UsuarioEntidad usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setContrasena(usuario.getContrasena());
        dto.setBarrios(usuario.getBarrios());
        dto.setTelefono(usuario.getTelefono());
        dto.setEstado(usuario.getEstado());
        dto.setRol(usuario.getRol());
        return dto;
    }

    // MÉTODO CREAR
    @Transactional
    public void  crearUsuario(UsuarioDTO usuarioDTO, String contrasena2){
        //validar que las contraseñas sean iguales 
        
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(new BCryptPasswordEncoder().encode(usuarioDTO.getContrasena()));
        usuario.setBarrios(usuarioDTO.getBarrios());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEstado(true);
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    // MÉTODOS DE BÚSQUEDA
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioEntidad> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // MÉTODO MODIFICAR
    @Transactional
    public void modificarUsuario(UsuarioDTO usuarioDTO) throws MiExcepcion {
        validar(usuarioDTO.getNombre(), usuarioDTO.getEmail());

        UsuarioEntidad usuario = usuarioRepositorio.findById(usuarioDTO.getId()).orElseThrow(
            () -> new MiExcepcion("No se encontró el usuario con el id ingresado.")
        );

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setBarrios(usuarioDTO.getBarrios());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setRol(usuarioDTO.getRol());

        usuarioRepositorio.save(usuario);
    }

    // MÉTODO ELIMINAR
    @Transactional
    public void eliminarUsuario(UUID id) {
        usuarioRepositorio.deleteById(id);
    }

    // MÉTODO PARA CARGAR USUARIO EN SESIÓN
    @Override
    public UserDetails loadUserByUsername(String emailUsuario) throws UsernameNotFoundException {
        UsuarioEntidad user = usuarioRepositorio.buscarPorEmail(emailUsuario);

        if (user == null) return null;

        List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
        GrantedAuthority perms = new SimpleGrantedAuthority("ROL_" + user.getRol().toString());

        permisos.add(perms);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        session.setAttribute("usuarioEnSesion", user);

        return new User(user.getEmail(), user.getContrasena(), permisos);
    }
}