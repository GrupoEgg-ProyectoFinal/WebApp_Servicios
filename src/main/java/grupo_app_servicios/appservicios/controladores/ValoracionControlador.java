package grupo_app_servicios.appservicios.controladores;

import java.util.List;
import java.util.UUID;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo_app_servicios.appservicios.Dto.SolicitudDTO;
import grupo_app_servicios.appservicios.Dto.ValoracionDTO;
import grupo_app_servicios.appservicios.excepciones.MiExcepcion;
import grupo_app_servicios.appservicios.servicios.SolicitudServicio;
import grupo_app_servicios.appservicios.servicios.ValoracionServicio;
import grupo_app_servicios.appservicios.utilidades.MapeadorEntidadADto;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/valoraciones")
public class ValoracionControlador {

   @Autowired
   private ValoracionServicio valoracionServicio;
   @Autowired
   private SolicitudServicio solicitudServicio;

   // CALIFICAR FORMULARIO
   @GetMapping("/calificar/{id}")
   public String calificar(@PathVariable String id, Model modelo) {
      SolicitudDTO solicitud = MapeadorEntidadADto
            .mapearSolicitud(solicitudServicio.buscarSolicitud(UUID.fromString(id)));
      modelo.addAttribute("solicitud", solicitud);
      modelo.addAttribute("valoracionDTO", new ValoracionDTO());
      return "calificar.html";
   }

   // CARGAR CALIFICACION
   @PostMapping("/calificarS/{id}")
   public String cargaCalificacion(@PathVariable String id,
         @ModelAttribute("valoracionDTO") ValoracionDTO valoracionDTO, Model modelo) throws MiExcepcion {
      try {
         solicitudServicio.cargarValoracion(UUID.fromString(id), valoracionDTO);

         return "redirect:../../contratar/lista";
      } catch (MiExcepcion ex) {
         modelo.addAttribute("error", ex.getMessage());
         return "redirect:/calificar/{id}";
      }

   }

   // LISTAR VALORACIONES
   @GetMapping("/listaValoraciones")
   public String listarValoraciones(Model model) {
      List<SolicitudDTO> solicitudes = solicitudServicio.listarPorValoracion();
      model.addAttribute("solicitudes", solicitudes);
      return "comentariosAdmin.html";
   }

   @PostMapping("/eliminar/{id}")
   public String eliminarComentario(@PathVariable String id,Model modelo) throws MiExcepcion {
      try {
         valoracionServicio.eliminarComnetario(UUID.fromString(id));
         return "redirect:../listaValoraciones";
      }  catch (MiExcepcion ex) {
         modelo.addAttribute("error", ex.getMessage());
         return "redirect:../listaValoraciones";
      }

   }

}
