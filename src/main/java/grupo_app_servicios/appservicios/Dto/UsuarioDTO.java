package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private Barrios barrios;
    private Integer telefono;
    private Boolean estado;
    private Rol rol;
}
