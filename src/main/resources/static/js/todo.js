$(function() {
    const sidebar = $('#sidebar');
    const openSidebar = $('#todo_alert');
    const closeSidebar = $('#closeSidebar');

    openSidebar.click(function() {
        loadTodo();
        sidebar.css('right', '0');
    });

    closeSidebar.click(function() {
        sidebar.css('right', '-350px');
    });

    $("#add-btn").click(function() {
        const content = $('#todo-content').val().trim();
        if(!content) {
            $('#todo-content').focus();
            return;
        }
        const data = {
            "content": content
        };
        $.ajax({
            url: "/todo/add",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr) {
                loadTodo();
                $('#todo-content').val('');
            }
        });
    });

    $('#todo-content').on("input", function() {
        var maxLength = 25;
        if ($(this).val().length > maxLength) {
          $(this).val($(this).val().substring(0, maxLength));
        }
    });
});

function loadTodo() {
    $.ajax({
        url: "/todo/list?user_id=" + todoUser_id,
        type: "GET",
        cache: false,
        success: function(data, status, xhr) {
            buildTodo(data);
            listenDelete(data);
            if (data.length == 8) {
                $('#add-btn').addClass('disabled');
            }
        }
    });
}

function buildTodo(result) {
    const out = [];
    result.forEach(todo => {
        let id = todo.id;
        let content = todo.content;
        let regDate = todo.regDate;
        const row = `
        <div id="todo-${id}" class="d-flex align-items-center justify-content-between">
            <div>
                <div class="todo-start">
                  <div>${content}</div>
                </div>
                <div class="d-flex justify-content-start">
                  <div class="todo-date">${regDate}</div>
                </div>
            </div>
            <div class="form-check form-switch">
                <input data-todo-id="${id}" class="form-check-input" type="checkbox">
            </div>
        </div>
        `;
        out.push(row);
    });
    $('#todo-list').html(out.join("\n"));
}

function listenDelete(result) {
    $("[data-todo-id]").click(function() {
        const todo_id = $(this).attr("data-todo-id");
        $('#todo-' + todo_id).css('text-decoration', 'line-through');
        setTimeout(function() {
            $.ajax({
                url: "/todo/delete",
                type: "POST",
                cache: false,
                data: {"id": todo_id},
                success: function(data, status, xhr) {
                    loadTodo();
                    $('#add-btn').removeClass('disabled');
                }
            });
        }, 150);
    });
}