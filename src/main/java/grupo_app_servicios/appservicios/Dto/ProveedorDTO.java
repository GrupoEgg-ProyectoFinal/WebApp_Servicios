package grupo_app_servicios.appservicios.Dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProveedorDTO {
    private UUID id;
    private Integer matricula;
    private String descripcion;
    private UsuarioDTO usuario;
    private ImagenProvDTO foto;

    private ServicioDTO servicio;
    private List<SolicitudDTO> solicitudes;
}