package grupo_app_servicios.appservicios.controladores;


import grupo_app_servicios.appservicios.Dto.UsuarioDTO;
import grupo_app_servicios.appservicios.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
//Accedo al metodo
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    //mapeo motodo
    @PostMapping("/crear")
    public ResponseEntity<Object> crearUsuario(@RequestBody UsuarioDTO usuarioDTO){
        try{
            usuarioServicio.crearUsuario(usuarioDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
