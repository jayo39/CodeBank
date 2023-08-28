$(function() {
    if (localStorage.getItem('registrationSuccess') === 'true') {
        $("#register-success").show();
        localStorage.removeItem('registrationSuccess');
    }
    $('#register').click(function() {
        var email = $("input[name='email']").val().trim();
        var name = $("input[name='name']").val().trim();
        var password = $("input[name='password']").val().trim();
        var password_confirm = $("input[name='password-confirm']").val().trim();

        if(!email) {
            $("#email").focus();
            return;
        } else if (!name) {
            $("#name").focus();
            return;
        } else if (!password) {
            $("#password").focus();
            return;
        } else if (!password_confirm) {
            $("#password-confirm").focus();
            return;
       }

        const data = {
            "email": email,
            "name": name,
            "password": password,
            "re_password": password_confirm,
        };

        $.ajax({
            url: "/user/register",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr) {
                localStorage.setItem('registrationSuccess', 'true');
                location.href = "/user/login";
            },
            error: function(xhr, status, error) {
                if (xhr.status === 400) {
                    var errorMsg = xhr.responseText;
                    const errorField = $("#errorField");
                    errorField.text(errorMsg);
                }
            }
        });
    });
});