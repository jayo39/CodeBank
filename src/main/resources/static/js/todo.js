$(function() {
    const sidebar = $('#sidebar');
    const openSidebar = $('#todo_alert');
    const closeSidebar = $('#closeSidebar')

    $('#todo_alert').css({'color': "#ffffff"});

    $('#todo_alert').animate({'color': "#FF8C00"}, 1050).promise()
        .then(function() {
            loopAlert();
        });

    function loopAlert() {
        $('#todo_alert').animate({'color': "#ffffff"}, 1050);
        $('#todo_alert').animate({'color': "#FF8C00"}, 1050, loopAlert);
    }

    openSidebar.click(function() {
        loadTodo();
        sidebar.css('right', '0');
    });

    closeSidebar.click(function() {
        sidebar.css('right', '-350px');
    });

    $("#add-btn").click(function() {

    });

    $("[data-todo-id]").click(function() {
        const todo_id = $(this).attr("data-todo-id");
        $.ajax({
            url: "/todo/delete",
            type: "POST",
            cache: false,
            data: {"id": todo_id},
            success: function(data, status, xhr) {
                loadTodo();
            }
        });
    });
});

function loadTodo() {
    $.ajax({
        url: "/todo/list?user_id=" + todoUser_id,
        type: "GET",
        cache: false,
        success: function(data, status, xhr) {
            buildTodo(data);
        }
    });
}

function buildTodo(result) {
    result.data.forEach(todo => {
        let id = todo.id;
        let content = todo.content;
        let regDate = todo.regDate;
        const row = `
                <div class="d-flex align-items-center todo-start">
                  <div>${content}</div>
                  <div class="form-check form-switch">
                    <input data-todo-id="${id}" class="form-check-input" type="checkbox">
                  </div>
                </div>
                <div class="d-flex justify-content-start">
                  <div class="todo-date">${regDate}</div>
                </div>
        `;
        out.push(row);
        $('#todo-list').html(out.join("\n"));
    });
}