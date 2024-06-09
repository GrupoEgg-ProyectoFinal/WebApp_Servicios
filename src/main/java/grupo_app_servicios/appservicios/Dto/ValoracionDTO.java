package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ValoracionDTO {
   private UUID id;
   private String comentario;
   private Integer puntaje; 
}
