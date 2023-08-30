const followBtn = `
        <button class="follow-btn follow-click btn btn-primary d-flex align-items-center">
            <span>Follow</span>
            <span class="material-symbols-outlined follow-icon">
                person_check
            </span>
            <input class="logged_user" type="hidden">
            <input class="post_user" type="hidden">
        </button>
`

const unfollowBtn = `
        <button class="follow-btn follow-click btn btn-danger d-flex align-items-center">
            <span>Unfollow</span>
            <span class="material-symbols-outlined follow-icon">
                person_remove
            </span>
            <input class="logged_user" type="hidden">
            <input class="post_user" type="hidden">
        </button>
`

$('#follow-buttons').on('click', '.follow-click', function() {
    var logged_user = $('.logged_user').val();
    var post_user = $('.post_user').val();

    const data = {
        "logged_userId": logged_user,
        "post_userId": post_user,
    };

    $.ajax({
        url: "/user/followOk",
        type: "POST",
        data: data,
        cache: false,
        success: function(data, status, xhr) {
            var followerCount = data.followerCount;
            $('#follower-num').text(followerCount);
            if (data.followStatus == "unfollow") {
                $('#follow-buttons').html(followBtn);
                followerNum--;
                $('#followers-num').text('Followers: ' + followerNum);
            } else if (data.followStatus == "follow") {
                $('#follow-buttons').html(unfollowBtn);
                followerNum++;
                $('#followers-num').text('Followers: ' + followerNum);
            }
            $('.logged_user').val(current_userId);
            $('.post_user').val(snippet_userId);
        }
    });
})