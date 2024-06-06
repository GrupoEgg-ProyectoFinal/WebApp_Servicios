package grupo_app_servicios.appservicios.entidades;


import grupo_app_servicios.appservicios.enumeraciones.Barrios;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;

    @Column(unique = true)
    private String email;

    private String contrasena;
    private String nombre;
    private String apellido;
    private Integer telefono;
    @Enumerated(EnumType.STRING)
    private Barrios barrios;
    private boolean alta = true;

}
