$(document).ready(function () {

    // load first when coming page
    ajaxGet();

    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/employee/audit",
            success: function (result) {
                if (result.error == true) {
                    alert(result.toastMessage);
                } else {
                    console.log(result)
                    $(".taiKhoanTable tbody").empty();
                    $.each(result.object, function (i, audit) {
                        var auditRow = '<tr>' +
                            '<td>' + audit.username + '</td>' +
                            '<td>' + audit.obj + '</td>' +
                            '<td>' + audit.action + '</td>' +
                            '<td>' + audit.timeStamp + '</td>';
                        $('.taiKhoanTable tbody').append(auditRow);
                    });

                }

            },
            error: function (e) {
                if (e.error == true) {
                    alert(result.toastMessage);
                }
                console.log(e)
            }
        });
    };
});