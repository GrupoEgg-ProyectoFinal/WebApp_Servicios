package grupo_app_servicios.appservicios.servicios;


import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.Usuario;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {
    @Autowired
     UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void  crearUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
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
    }