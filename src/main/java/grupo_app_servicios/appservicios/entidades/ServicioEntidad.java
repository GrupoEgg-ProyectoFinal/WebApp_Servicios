package grupo_app_servicios.appservicios.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private Boolean estado;

    // La rutaImagen se usaría en los servicios que vienen por defecto y su imagen está dentro de la carpeta src/resources/static..
    // La imagen como entidad está para cuando un admin quiera agregar un nuevo servicio
    private String rutaImagen;
    @OneToOne
    private ImagenProvEntidad imagen;
}
