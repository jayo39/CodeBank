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

$('.follow-click').click(function() {
    var logged_user = $('#logged_user').val();
    var post_user = $('#post_user').val();

    const data = {
        "logged_userId": logged_user,
        "post_userId": post_user,
    };

    $.ajax({
        url: "/user/followOk",
        type: "POST",
        data: data,
        cache: false,
        success: function(data, status, xhr) {
            console.log("success?")
        }
    });
})