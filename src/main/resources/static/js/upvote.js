$(function() {
    $('.upvoteBtn').click(function() {
        // id of the snippet
        var snippetId = $(this).find('.snippet-id').val();
        var likeContainer = $(this).parent();
        var likeCount = $('#likeCount_' + snippetId);
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

                // Change button color based on likedStatus
                if (likedStat) {
                    likeContainer.removeClass('liked');
                    likeContainer.removeClass('not-liked');
                    likeContainer.addClass('liked');
                    likeCount.text(likeNum);
                } else {
                    likeContainer.removeClass('not-liked');
                    likeContainer.removeClass('liked');
                    likeContainer.addClass('not-liked');
                    likeCount.text(likeNum);
                }
            },
        });
    });
});