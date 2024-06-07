package grupo_app_servicios.appservicios.entidades;


import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Campos que no deben ser nulos
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private Integer telefono;
    @Column(nullable = false, unique = true)
    private Integer numeroMatricula;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena;

    private String descripcion;

    // Relaciones con entidades
    @OneToOne
    private ImagenProveedor foto;
    @ManyToOne
    private Servicio servicio;
    @OneToMany(mappedBy = "id_proveedor")
    private List<Valoracion> valoraciones;
    @OneToMany(mappedBy = "id_proveedor")
    private List<Solicitud> solicitudes;
}
