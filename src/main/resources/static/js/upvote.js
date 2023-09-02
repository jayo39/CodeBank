$(function() {
    $('.upvoteBtn').click(function() {
        if (typeof logged_user === 'undefined') {
            alert('You must login to like the post.');
            return;
        }
        // id of the snippet
        var snippetId = $(this).find('.snippet-id').val();
        var likeContainer = $('.likecontainer_' + snippetId);
        var likeCount = $('.likeCount_' + snippetId);
        const data = {
            "snippet_id": snippetId,
        };
        $.ajax({
            url: "/snippet/upvote",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr) {
                var likedStat = data.isUpvote;
                var likeNum = data.likeCount;

                if (likedStat) {
                    likeContainer.each(function() {
                        $(this).removeClass('liked');
                        $(this).removeClass('not-liked');
                        $(this).addClass('liked');
                    });
                    likeCount.each(function() {
                        $(this).text(likeNum);
                    });
                } else {
                    likeContainer.each(function() {
                        $(this).removeClass('not-liked');
                        $(this).removeClass('liked');
                        $(this).addClass('not-liked');
                    });
                    likeCount.each(function() {
                        $(this).text(likeNum);
                    });
                }
            },
        });
    });
});