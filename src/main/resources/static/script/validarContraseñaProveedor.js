// FUNCION PARA VALIDAR CONTRASEÑAS EN LOS REGISTROS // 

document.getElementById('formularioProveedor').addEventListener('submit', function(event) {
    const contrasena = document.getElementById('contrasena').value;
    const confirmarContrasena = document.getElementById('confirmarContrasena').value;
    const mensajeError = document.getElementById('mensajeError');

    if (contrasena !== confirmarContrasena) {
        event.preventDefault(); 
        alert("Las contraseñas no coinciden.");
        }   else {
                mensajeError.textContent = "";
                alert("¡Su cuenta ha sido creada con éxito!");
            }
});