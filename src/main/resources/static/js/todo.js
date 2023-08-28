$(function() {
    const sidebar = $('#sidebar');
    const openSidebar = $('#todo_alert');
    const closeSidebar = $('#closeSidebar')

    $('#todo_alert').css({'color': "#ffffff"});

    $('#todo_alert').animate({'color': "#FF8C00"}, 1050).promise()
        .then(function() {
            loopAlert();
        });

    function loopAlert() {
        $('#todo_alert').animate({'color': "#ffffff"}, 1050);
        $('#todo_alert').animate({'color': "#FF8C00"}, 1050, loopAlert);
    }

    openSidebar.click(function() {
      sidebar.css('right', '0');
    });

    closeSidebar.click(function() {
      sidebar.css('right', '-350px');
    });

});