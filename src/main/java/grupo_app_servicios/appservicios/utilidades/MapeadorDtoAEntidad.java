package grupo_app_servicios.appservicios.utilidades;

import org.springframework.beans.factory.annotation.Autowired;

import grupo_app_servicios.appservicios.Dto.ImagenProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.entidades.Proveedor;
import grupo_app_servicios.appservicios.entidades.Servicio;
import grupo_app_servicios.appservicios.repositorios.ServicioRepositorio;

/* 
 * Esta clase contiene métodos que deben ser estáticos y públicos, para poder ser usados solo los que se necesiten
 * y sin necesidad de instanciar la clase. 
 * Sirve para mapear los DTO y retornar la entidad que corresponda.
*/
public class MapeadorDtoAEntidad {   

    public static Proveedor mapearProveedor(ProveedorDTO proveedor) {
        Proveedor proveedorMapeado = new Proveedor();
        proveedorMapeado.setId(proveedor.getId());
        proveedorMapeado.setNombre(proveedor.getNombre());
        proveedorMapeado.setApellido(proveedor.getApellido());
        proveedorMapeado.setEmail(proveedor.getEmail());
        proveedorMapeado.setContrasena(proveedor.getContrasena());

        proveedorMapeado.setDescripcion(proveedor.getDescripcion());
        proveedorMapeado.setTelefono(proveedor.getTelefono());
        proveedorMapeado.setMatricula(proveedor.getMatricula());

        if (proveedor.getFoto() != null) {
            ImagenProveedorDTO imagenProveedorDTO = proveedor.getFoto();

            ImagenProveedor imagenProveedor = MapeadorDtoAEntidad.mapearImagenProveedor(imagenProveedorDTO);
            // acá se mapearía una entidad de imagenProveedor y se le asignaria al setter
            proveedorMapeado.setFoto(imagenProveedor); 
        }

        //agregar los mapeos de servicio y solicitudes
        
      

        return proveedorMapeado;
    }

    public static ImagenProveedor mapearImagenProveedor(ImagenProveedorDTO imagenProveedor) {
        ImagenProveedor imagenMapeada = new ImagenProveedor();
        imagenMapeada.setId(imagenProveedor.getId());
        imagenMapeada.setContenido(imagenProveedor.getContenido());
        imagenMapeada.setFormato(imagenProveedor.getFormato());
        imagenMapeada.setNombre(imagenProveedor.getNombre());

        return imagenMapeada;
    }
}
