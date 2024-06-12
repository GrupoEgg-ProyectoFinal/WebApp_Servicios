package grupo_app_servicios.appservicios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class controladorRest {
//llamar a la pagina principal
    public String pantallaInicial(){
        return "index";
    }
}




