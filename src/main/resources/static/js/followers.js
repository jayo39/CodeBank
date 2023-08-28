function goBack() {
    history.back();
}

$(function() {
    if(listType == "following") {
        $('#following').addClass("active");
        $('.followersBlock').hide();
        $('.followingBlock').show();
    } else if (listType == "followers") {
        $('#followers').addClass("active");
        $('.followingBlock').hide();
        $('.followersBlock').show();
    }

    $('#following').on('click', function() {
        $('#following').addClass("active");
        $('#followers').removeClass("active");
        $('.followersBlock').hide();
        $('.followingBlock').show();
    });
    $('#followers').on('click', function() {
        $('#followers').addClass("active");
        $('#following').removeClass("active");
        $('.followingBlock').hide();
        $('.followersBlock').show();
    });
});