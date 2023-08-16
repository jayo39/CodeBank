$(function() {
    $('#login').click(function() {
        const email = $("input[name='username']").val().trim();
        const password = $("#passwordInput").val().trim();
        if(!email) {
            $("#username").focus();
            return;
        } else if (!password) {
            $("#passwordInput").focus();
            return;
        }
        $('#loginForm').submit();
    });
});