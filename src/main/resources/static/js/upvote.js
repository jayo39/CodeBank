$(function() {
    $('.upvoteBtn').click(function() {
        // id of the snippet
        var snippetId = $(this).find('.snippet-id').val();
        var button = $(this);
        var container = $(this).parent();
        const data = {
            "snippet_id": snippetId,
        };
        $.ajax({
            url: "/snippet/upvote",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr) {
                // Assume data.likedStatus is true or false based on server response
                var likedStat = data.isUpvote;

                // Change button color based on likedStatus
                if (likedStat) {
                    button.removeClass('liked');
                    button.removeClass('not-liked');
                    button.addClass('liked');
                } else {
                    button.removeClass('not-liked');
                    button.removeClass('liked');
                    button.addClass('not-liked');
                }
            },
        });
    });
});