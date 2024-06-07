package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ServicioDTO {
    private UUID id;
    private String nombre;
    private Boolean estado;
}
