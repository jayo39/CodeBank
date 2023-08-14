// Register modal
const modal = $('#myModal');
const closeButton = $('.close');

function openModal() {
    modal.css('display', 'flex');
    $("input[name='email']").val($("#signup").val());
    $("input[name='name']").val('');
    $("input[name='password']").val('');
    $("input[name='password-confirm']").val('');
}

function closeModal() {
    modal.css('display', 'none');
}

$(window).on('click', function(event) {
    if (event.target === modal[0]) {
        closeModal();
    }
});

$('#closeModal').on('click', function() {
    closeModal();
});

closeButton.on('click', closeModal);

$(function() {
    // Title click event
    $('#title').on('click', function() {
        location.href = window.location.protocol + "//" + window.location.host + "/";
    });
});