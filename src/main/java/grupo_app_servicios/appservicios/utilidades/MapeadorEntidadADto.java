package grupo_app_servicios.appservicios.utilidades;

import grupo_app_servicios.appservicios.Dto.ImagenProvDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ServicioDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProvEntidad;
import grupo_app_servicios.appservicios.entidades.ProveedorEntidad;
import grupo_app_servicios.appservicios.entidades.ServicioEntidad;

/* 
 * Esta clase contiene métodos que deben ser estáticos y públicos, para poder ser usados solo los que se necesiten
 * y sin necesidad de instanciar la clase. 
 * Sirve para mapear las entidades y retornar el DTO que corresponda.
*/
public class MapeadorEntidadADto {
    
    public static ProveedorDTO mapearProveedor(ProveedorEntidad proveedor) {
        ProveedorDTO proveedorMapeado = new ProveedorDTO();
        proveedorMapeado.setId(proveedor.getId());
        proveedorMapeado.setNombre(proveedor.getNombre());
        proveedorMapeado.setApellido(proveedor.getApellido());
        proveedorMapeado.setEmail(proveedor.getEmail());
        proveedorMapeado.setContrasena(proveedor.getContrasena());

        proveedorMapeado.setDescripcion(proveedor.getDescripcion());
        proveedorMapeado.setTelefono(proveedor.getTelefono());
        proveedorMapeado.setMatricula(proveedor.getMatricula());

        if (proveedor.getFoto() != null) {
            ImagenProvEntidad imagenProveedor = proveedor.getFoto();

            ImagenProvDTO imagenProveedorDTO = MapeadorEntidadADto.mapearImagenProveedor(imagenProveedor);
            proveedorMapeado.setFoto(imagenProveedorDTO); 
        }
        
        if (proveedor.getServicio() != null) {
            ServicioEntidad servicioDTO = proveedor.getServicio();
            ServicioDTO servicio = MapeadorEntidadADto.mapearServicio(servicioDTO);

            proveedorMapeado.setServicio(servicio);
        }

        return proveedorMapeado;
    }

    public static ImagenProvDTO mapearImagenProveedor(ImagenProvEntidad imagenProveedor) {
        ImagenProvDTO imagenMapeada = new ImagenProvDTO();
        imagenMapeada.setId(imagenProveedor.getId());
        imagenMapeada.setContenido(imagenProveedor.getContenido());
        imagenMapeada.setMime(imagenProveedor.getMime());
        imagenMapeada.setNombre(imagenProveedor.getNombre());

        return imagenMapeada;
    } 

    public static ServicioDTO mapearServicio(ServicioEntidad servicio) {
        ServicioDTO servicioMapeado = new ServicioDTO();
        servicioMapeado.setId(servicio.getId());
        servicioMapeado.setNombre(servicio.getNombre());
        servicioMapeado.setEstado(servicio.getEstado());

        return servicioMapeado;
    }
}
