$(function() {
    $(".snippet-description").each(function() {
        if ($(this).text().length > 60) {
            $(this).text($(this).text().substring(0, 60) + "..")
        }
    });
});

function deleteThis(element) {
    let answer = confirm("Are you sure you want to delete this code?");
    if(answer) {
        snippet_id = element.getAttribute('data-snippet-id');
        $('#snippetRemoveId').val(snippet_id);
        $("form[name='delete']").submit();
    }
}
