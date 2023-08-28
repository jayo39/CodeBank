$(function() {
    $('#todo_alert').css({'color': "#ffffff"});

    $('#todo_alert').animate({'color': "#ff8c00"}, 1050).promise()
        .then(function() {
            loopAlert();
        });

    function loopAlert() {
        $('#todo_alert').animate({'color': "#ffffff"}, 1050);
        $('#todo_alert').animate({'color': "#ff8c00"}, 1050, loopAlert);
    }
});