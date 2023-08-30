$(function() {
    var profileUpload = $('#profile-upload');
    var profileClick = $('#image-wrap');
    const user_id = $('#user-id').val().trim();
    $(".snippet-description").each(function() {
        if ($(this).text().length > 60) {
            $(this).text($(this).text().substring(0, 60) + "..")
        }
    });
    profileClick.on("click", function(event) {
        event.preventDefault();
        profileUpload.click();
    });

    profileUpload.on('change', function() {
        $('#profilePicture').submit();
    });

    profileClick.hover(
        function() {
            $('#click-profile').css("opacity", 0.7);
            $('#upload-info').css("opacity", 1);
        },
        function () {
            $('#click-profile').css("opacity", 1);
            $('#upload-info').css("opacity", 0);
        }
    );
});

function deleteThis(element) {
    let answer = confirm("Are you sure you want to delete this code?");
    if(answer) {
        snippet_id = element.getAttribute('data-snippet-id');
        $('#snippetRemoveId').val(snippet_id);
        $("form[name='delete']").submit();
    }
}
