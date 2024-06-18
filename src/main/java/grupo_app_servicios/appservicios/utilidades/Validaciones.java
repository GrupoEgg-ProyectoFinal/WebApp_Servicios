package grupo_app_servicios.appservicios.utilidades;

public class Validaciones {
    public static void validarSiCampoEsNulo(Object campo, String nombreDelCampo) {
        if (campo == null) throw new RuntimeException("El campo " + nombreDelCampo + " no puede ser nulo");
    }
}
