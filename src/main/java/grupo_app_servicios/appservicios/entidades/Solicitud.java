package grupo_app_servicios.appservicios.entidades;

import java.util.UUID;

import grupo_app_servicios.appservicios.enumeraciones.Estados;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String comentario;

    @Enumerated(EnumType.STRING)
    private Estados estado;

    // Relaciones con entidades
    @JoinColumn(nullable = true)
    @OneToOne
    private Valoracion idValoracion;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Proveedor idProveedor;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Usuario idUsuario;

}
