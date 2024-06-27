package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import grupo_app_servicios.appservicios.enumeraciones.Estados;
import lombok.Data;

@Data
public class SolicitudDTO {
    private UUID id;
    private String comentario;
    private Estados estado;
    private ValoracionDTO idValoracion;
    private UUID idProveedor;
    private UsuarioDTO idUsuario;
}
