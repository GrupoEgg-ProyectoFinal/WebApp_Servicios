package grupo_app_servicios.appservicios.controladores;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.servicios.SolicitudServicio;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionControlador {

   @Autowired
   private ValoracionServicio valoracionServicio;
   @Autowired
   private SolicitudServicio solicitudServicio;

   // CALIFICAR FORMULARIO
   @GetMapping("/calificar")
   public String calificar() {
      return "calificar.html";
   }

   // CARGAR CALIFICACION
   @PostMapping("/calificar/{id}")
   public String cargaCalificacion(@PathVariable String id,
         @ModelAttribute("valoracionDTO") ValoracionDTO valoracionDTO, Model modelo) {
      solicitudServicio.cargarValoracion(UUID.fromString(id), valoracionDTO);
      return "redirect:../contratar/lista";

   }

}
