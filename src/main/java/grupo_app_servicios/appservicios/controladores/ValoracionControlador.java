package grupo_app_servicios.appservicios.controladores;

import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionControlador {

    @Autowired
    private ValoracionServicio valoracionServicio;

    @GetMapping
    public List<ValoracionDTO> obtenerTodasLasValoraciones() {
        return valoracionServicio.obtenerTodasLasValoraciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValoracionDTO> obtenerValoracionPorId(@PathVariable UUID id) {
        Optional<ValoracionDTO> valoracion = valoracionServicio.obtenerValoracionPorId(id);
        return valoracion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ValoracionDTO> crearValoracion(@RequestBody ValoracionDTO nuevaValoracion) {
        ValoracionDTO valoracionGuardada = valoracionServicio.crearValoracion(nuevaValoracion);
        return new ResponseEntity<>(valoracionGuardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValoracionDTO> actualizarValoracion(@PathVariable UUID id, @RequestBody ValoracionDTO valoracionActualizada) {
        Optional<ValoracionDTO> valoracionOpt = valoracionServicio.actualizarValoracion(id, valoracionActualizada);
        return valoracionOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarValoracion(@PathVariable UUID id) {
        if (valoracionServicio.eliminarValoracion(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
