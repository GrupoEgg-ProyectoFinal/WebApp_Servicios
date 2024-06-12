package grupo_app_servicios.appservicios.utilidades;

import grupo_app_servicios.appservicios.Dto.ImagenProveedorDTO;
import grupo_app_servicios.appservicios.Dto.ProveedorDTO;
import grupo_app_servicios.appservicios.entidades.ImagenProveedor;
import grupo_app_servicios.appservicios.entidades.Proveedor;

/* 
 * Esta clase contiene métodos que deben ser estáticos y públicos, para poder ser usados solo los que se necesiten
 * y sin necesidad de instanciar la clase. 
 * Sirve para mapear las entidades y retornar el DTO que corresponda.
*/
public class MapeadorEntidadADto {
    
    public static ProveedorDTO mapearProveedor(Proveedor proveedor) {
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
            ImagenProveedor imagenProveedor = proveedor.getFoto();

            ImagenProveedorDTO imagenProveedorDTO = MapeadorEntidadADto.mapearImagenProveedor(imagenProveedor);
            proveedorMapeado.setFoto(imagenProveedorDTO); 
        }
        
        //agregar los mapeos de servicio y solicitudes

        return proveedorMapeado;
    }

    public static ImagenProveedorDTO mapearImagenProveedor(ImagenProveedor imagenProveedor) {
        ImagenProveedorDTO imagenMapeada = new ImagenProveedorDTO();
        imagenMapeada.setId(imagenProveedor.getId());
        imagenMapeada.setContenido(imagenProveedor.getContenido());
        // imagenMapeada.setFormato(imagenProveedor.getFormato());
        imagenMapeada.setNombre(imagenProveedor.getNombre());

        return imagenMapeada;
    } 
}
