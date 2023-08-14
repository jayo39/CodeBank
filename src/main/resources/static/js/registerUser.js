$(function() {

    $('#register').click(function() {
        const email = $("input[name='email']").val().trim();
        const name = $("input[name='name']").val().trim();
        const password = $("input[name='password']").val().trim();
        const password_confirm = $("input[name='password-confirm']").val().trim();

        if(!email) {
            $("#email").focus();
            return;
        } else if (!name) {
            $("#name").focus();
            return
        } else if (!password) {
            $("#password").focus();
            return
        } else if (!password_confirm) {
            $("#password-confirm").focus();
            return
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
                // TODO change this url to something else
                location.href = "/user/add";
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