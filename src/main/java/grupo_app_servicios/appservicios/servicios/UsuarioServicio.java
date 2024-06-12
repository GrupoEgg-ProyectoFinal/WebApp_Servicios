package grupo_app_servicios.appservicios.servicios;


import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.entidades.Usuario;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        usuario.setEstado(true);
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }


    @Transactional
    public void modificarUsuario(UsuarioDTO usuarioDTO) throws MiExcepcion {
        validar(usuarioDTO.getNombre(), usuarioDTO.getEmail());

        Usuario usuario = usuarioRepositorio.getById(usuarioDTO.getId());

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

    private void validar(String nombre, String email) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede estar vacío");
        }

        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede estar vacío");
        }
        // Puedes agregar más validaciones aquí según tus requisitos
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
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

    @Transactional
    public void eliminarUsuario(UUID id) {
        usuarioRepositorio.deleteById(id);
    }


}