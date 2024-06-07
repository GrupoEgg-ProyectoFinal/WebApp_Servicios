package grupo_app_servicios.appservicios.entidades;

import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private Integer telefono;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Barrios barrios;
    private boolean alta = true;

}
