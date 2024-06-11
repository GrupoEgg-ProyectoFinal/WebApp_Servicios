package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ImagenProveedorDTO {
    private UUID id;
    private byte[] contenido;
    private String formato;
    private String nombre;
}
