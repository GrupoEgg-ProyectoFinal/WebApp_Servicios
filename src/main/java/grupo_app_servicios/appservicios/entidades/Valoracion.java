package grupo_app_servicios.appservicios.entidades;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String comentario;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Proveedor id_proveedor;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Usuario id_usuario;

}
