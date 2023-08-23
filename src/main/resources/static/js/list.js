$(function() {
    var sortSelect = $('#sort');
    $(".snippet-description").each(function() {
        if ($(this).text().length > 94) {
        $(this).text($(this).text().substring(0, 94) + "..");
        }
    });
    if (sort == "popular") {
        $('#sortTitle').text("Most Popular");
        $('#sortIcon').text("local_fire_department");
        sortSelect.val("popular");
    } else if (sort == "newest") {
        $('#sortTitle').text("Recently Updated");
        $('#sortIcon').text("new_releases");
        sortSelect.val("newest");
    } else if (sort == "following") {
        $('#sortTitle').text("Following");
        $('#sortIcon').text("person_pin_circle");
        sortSelect.val("following");
    }
});

function changeSort() {
    var selectedSort = $('#sort').val();
    if (selectedSort == "popular") {
        $('#sortTitle').text("Most Popular");
        $('#sortIcon').text("local_fire_department");
    } else if (selectedSort == "newest") {
        $('#sortTitle').text("Recently Updated");
        $('#sortIcon').text("new_releases");
    } else if (selectedSort == "following") {
        $('#sortTitle').text("Following");
        $('#sortIcon').text("person_pin_circle");
    }

    $("form[name='sort']").submit();
}