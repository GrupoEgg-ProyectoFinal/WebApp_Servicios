document.addEventListener("DOMContentLoaded", function () {
    var faqItems = document.querySelectorAll(".faq-item");

    faqItems.forEach(function (item) {
        var question = item.querySelector(".faq-question");
        question.addEventListener("click", function () {
            item.classList.toggle("active");
        });
    });

    // Event listener para cerrar el modal al hacer clic fuera de él
    const modalBg = document.getElementById('modal-bg');
    modalBg.addEventListener('click', function (event) {
        if (event.target === modalBg) {
            closeModal();
        }
    });
});

// LISTENERS PARA EL MODAL DE LA SECCION 'AYUDA' 

// Función para abrir el modal y mostrar la respuesta
const modalText = document.getElementById('modal-text');
const modalBg = document.getElementById('modal-bg');
const modalContent = document.querySelector('.modal-content');

function openModal(answer) {
    modalText.innerHTML = answer;
    modalBg.style.display = 'flex'; // Mostrar el fondo oscurecido
    setTimeout(() => {
        modalContent.classList.add('active'); // Activar la transición del modal
    }, 50); // Pequeño retraso para asegurar que la transición funcione correctamente

    // Añadir clase para evitar el scroll de la página de fondo
    document.body.classList.add('modal-open');
}

// Cerrar el modal
function closeModal() {
    modalContent.classList.remove('active'); // Desactivar la transición del modal
    setTimeout(() => {
        modalBg.style.display = 'none'; // Ocultar el fondo oscurecido después de la transición
    }, 300); // Asegurar que la transición termine antes de ocultar el modal

    // Remover la clase para permitir el scroll de la página de fondo
    document.body.classList.remove('modal-open');
}

// Event listeners para los botones de preguntas
const faqQuestions = document.querySelectorAll('.faq-question');
faqQuestions.forEach((question) => {
    question.addEventListener('click', () => {
        const answer = question.nextElementSibling.querySelector('h1').innerHTML;
        openModal(answer);
    });
});

// Event listener para el botón de cerrar el modal
const closeModalBtn = document.querySelector('.close-modal');
if (closeModalBtn) {
    closeModalBtn.addEventListener('click', closeModal);
}
