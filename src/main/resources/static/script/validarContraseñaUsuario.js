// FUNCION PARA VALIDAR CONTRASEÑAS EN LOS REGISTROS // 

document.getElementById('formularioUsuario').addEventListener('submit', function(event) {
    const contrasena = document.getElementById('contrasena').value;
    const confirmarContrasena = document.getElementById('confirmarContrasena').value;
    const mensajeError = document.getElementById('mensajeError');

    if (contrasena !== confirmarContrasena) {
        event.preventDefault(); // Evita el envío del formulario
        alert("Las contraseñas no coinciden.");
            } else {
                mensajeError.textContent = ""; // Limpia el mensaje de error
                alert("¡Su cuenta ha sido creada con éxito!");
        }
});