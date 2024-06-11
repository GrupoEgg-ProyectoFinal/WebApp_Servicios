package grupo_app_servicios.appservicios.entidades;

import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenProveedor {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @Lob
  @Basic
  @Column(columnDefinition = "LONGBLOB")
  private byte[] contenido;
  private String formato;
  private String nombre;
}
