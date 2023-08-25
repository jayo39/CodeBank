const modal = $('#profileModal');

$(window).on('click', function(event) {
    if (event.target === modal[0]) {
        closeModal();
    }
});

function openModal() {
    modal.css('display', 'flex');
}

function closeModal() {
    modal.css('display', 'none');
}