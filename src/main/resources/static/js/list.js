$(function() {
    $(".snippet-description").each(function() {
        if ($(this).text().length > 50) {
        $(this).text($(this).text().substring(0, 50) + "...");
        }
    });
});