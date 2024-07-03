package grupo_app_servicios.appservicios.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.UsuarioEntidad;
import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import grupo_app_servicios.appservicios.utilidades.Validaciones;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    // MÉTODO CREAR
    @Transactional
    public void crearUsuario(UsuarioDTO usuarioDTO, String contrasena2) throws MiExcepcion {
        Validaciones.validarVariosCampos(
            new String[]{"nombre", "apellido", "email", "contraseña", "repetición de contraseña", "teléfono", "barrio"},
            usuarioDTO.getNombre(), usuarioDTO.getApellido(), usuarioDTO.getEmail(), usuarioDTO.getContrasena(), contrasena2, 
            usuarioDTO.getTelefono(), usuarioDTO.getBarrios()
        );
        if (!usuarioDTO.getContrasena().equals(contrasena2)) {
            throw new MiExcepcion("Las contraseñas deben ser iguales");
        }
        UsuarioEntidad usuarioConMismoEmail = usuarioRepositorio.buscarPorEmail(usuarioDTO.getEmail()).orElse(null);
        if (usuarioConMismoEmail != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con este email");
        }

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
        return usuarios.stream().map(
            usuarioEntidad -> MapeadorEntidadADto.mapearUsuario(usuarioEntidad)
        ).toList();
    }

    // MÉTODO MODIFICAR
    @Transactional
    public void modificarUsuario(UsuarioDTO usuarioDTO) throws MiExcepcion {
        Validaciones.validarVariosCampos(
            new String[]{"nombre", "apellido", "email", "contraseña", "teléfono", "barrio"},
            usuarioDTO.getNombre(), usuarioDTO.getApellido(), usuarioDTO.getEmail(), usuarioDTO.getContrasena(), 
            usuarioDTO.getTelefono(), usuarioDTO.getBarrios()
        );

        UsuarioEntidad usuario = usuarioRepositorio.findById(usuarioDTO.getId()).orElseThrow(
                () -> new MiExcepcion("No se encontró el usuario con el id ingresado."));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(new BCryptPasswordEncoder().encode(usuarioDTO.getContrasena()));
        usuario.setBarrios(usuarioDTO.getBarrios());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEstado(usuarioDTO.getEstado());

        usuarioRepositorio.save(usuario);
    }

    // MÉTODO ELIMINAR
    @Transactional
    public void eliminarUsuario(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id");
        usuarioRepositorio.deleteById(id);
    }

    // MÉTODO PARA CARGAR USUARIO EN SESIÓN
    @Override
    public UserDetails loadUserByUsername(String emailUsuario) throws UsernameNotFoundException {
        UsuarioEntidad user = usuarioRepositorio.buscarPorEmail(emailUsuario).orElse(null);

        if (user == null) return null;

        List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
        GrantedAuthority perms = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());

        permisos.add(perms);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        session.setAttribute("usuarioEnSesion", user);

        return new User(user.getEmail(), user.getContrasena(), permisos);
    }

    // BUSCAR POR MAIL
    // Método para buscar usuario por email
    public UsuarioDTO buscarPorEmail(String email) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(email, "email del usuario");
    
        return MapeadorEntidadADto.mapearUsuario(usuarioRepositorio.buscarPorEmail(email).orElseThrow(
            () -> new MiExcepcion("No existe un usuario con el email dado.")
        ));
    }

    // CAMBIAR ROL
    @Transactional
    public void cambiarRol(@PathVariable UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id del usuario");

        Optional<UsuarioEntidad> usuario = usuarioRepositorio.findById(id);

        if (usuario.isPresent()) {
            UsuarioEntidad respuestaUsuario = usuario.get();
            if (respuestaUsuario.getRol().equals(Rol.USER) || respuestaUsuario.getRol().equals(Rol.PROVEEDOR)) {
                respuestaUsuario.setRol(Rol.ADMIN);
            } else if (respuestaUsuario.getRol().equals(Rol.ADMIN)) {
                if (respuestaUsuario.getBarrios().equals(Barrios.PROVEEDOR)) {
                    respuestaUsuario.setRol(Rol.PROVEEDOR);
                } else {
                    respuestaUsuario.setRol(Rol.USER);
                }

            }

            usuarioRepositorio.save(respuestaUsuario);
        } else {
            throw new MiExcepcion("No se encontró un usuario con la id enviada");
        }
    }

    @Transactional
    public UsuarioDTO encontrarPorId(UUID id) throws MiExcepcion {
        Validaciones.validarSiCampoEsNulo(id, "id del usuario");

        return MapeadorEntidadADto.mapearUsuario(usuarioRepositorio.findById(id).orElseThrow(
            () -> new MiExcepcion("No se encontró un usuario con la id enviada")
        ));
    }
}