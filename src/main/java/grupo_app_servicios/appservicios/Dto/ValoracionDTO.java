package grupo_app_servicios.appservicios.Dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ValoracionDTO {
   private UUID id;
   private String comentario;
   private Integer puntaje;


}
