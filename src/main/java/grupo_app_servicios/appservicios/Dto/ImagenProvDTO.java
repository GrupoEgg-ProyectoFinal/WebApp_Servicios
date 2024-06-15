package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ImagenProvDTO {
    private UUID id;
    private String mime;
    private String nombre;
    private byte[] contenido;
}
