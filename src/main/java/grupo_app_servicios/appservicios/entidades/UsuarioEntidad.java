package grupo_app_servicios.appservicios.entidades;

import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import grupo_app_servicios.appservicios.enumeraciones.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private Long telefono;
    @Column(nullable = false)
    private Boolean estado;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Barrios barrios;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;

}
