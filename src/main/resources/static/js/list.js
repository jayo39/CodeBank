$(function() {
    $(".snippet-description").each(function() {
        if ($(this).text().length > 150) {
        $(this).text($(this).text().substring(0, 150) + "...");
        }
    });
});