document.addEventListener("DOMContentLoaded", function() {
    var faqItems = document.querySelectorAll(".faq-item");

    faqItems.forEach(function(item) {
        var question = item.querySelector(".faq-question");
        question.addEventListener("click", function() {
            item.classList.toggle("active");
        });
    });
});

