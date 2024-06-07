package grupo_app_servicios.appservicios.Dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProveedorDTO {

    private UUID id;
    private String nombre;
    private String apellido;
    private Integer telefono;
    private Integer matricula;
    private String email;
    private String contrasena;
    private String descripcion;
    private ImagenProveedorDTO foto;
    private ServicioDTO servicio;
    private List<SolicitudDTO> solicitudes;
    
}