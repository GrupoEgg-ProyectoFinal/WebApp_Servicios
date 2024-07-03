function calcularHonorarios() {
    const tarifaPorHora = 50; // Puedes cambiar esta tarifa según sea necesario
    const horas = document.getElementById("horas").value;
    const honorariosEstimados = tarifaPorHora * horas;
    document.getElementById("honorariosEstimados").innerText = "Honorarios estimados: $" + honorariosEstimados.toFixed(2);
}