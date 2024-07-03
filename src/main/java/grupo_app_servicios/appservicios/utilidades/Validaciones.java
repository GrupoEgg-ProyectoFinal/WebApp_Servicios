package grupo_app_servicios.appservicios.utilidades;

import java.util.List;

import grupo_app_servicios.appservicios.excepciones.MiExcepcion;

public class Validaciones {
    public static void validarSiCampoEsNulo(Object campo, String nombreDelCampo) throws MiExcepcion {
        if (campo == null)
            throw new MiExcepcion("El campo " + nombreDelCampo + " no puede estar vacio");
    }

    public static void validarVariosCampos(String[] nombreDeCampos, Object... valoresDeCampos) throws MiExcepcion {
        if (nombreDeCampos.length != valoresDeCampos.length)
            throw new MiExcepcion("Arrays don't have the same length");
        for (int i = 0; i < nombreDeCampos.length; i++) {
            if (valoresDeCampos[i] == null)
                throw new MiExcepcion("El campo " + nombreDeCampos[i] + "no debe ser nulo");
            if (valoresDeCampos[i] instanceof String && ((String) valoresDeCampos[i]).isBlank())
                throw new MiExcepcion("El campo " + nombreDeCampos[i] + "no debe estar vacío");
            if (valoresDeCampos[i] instanceof List) {
                List<?> lista = (List<?>) valoresDeCampos[i];
                for (Object object : lista) {
                    if (object == null)
                        throw new MiExcepcion("El campo " + nombreDeCampos[i] + "no debe estar vacío");
                }
            }
        }
    }
}
