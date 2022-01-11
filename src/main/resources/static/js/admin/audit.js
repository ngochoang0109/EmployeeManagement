$(document).ready(function() {

	// load first when coming page
	ajaxGet();
	$(document).on('click', '#btnLogout', function(event) {
		event.preventDefault();
		ajaxLogout();
	});
	function ajaxLogout() {
		var data = {};
		// do post
		$.ajax({
			async: false,
			type: "GET",
			contentType: "application/json",
			url: "http://localhost:8080/api/employee/logout",
			enctype: 'application/json',
			success: function(response) {
				if (response.object == true) {

					window.location.href = "/"
				}
				else {
					alert("ERROR: ", response.toastMessage);
				}
			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

	}
	function ajaxGet() {
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/api/employee/audit",
			success: function(result) {
				if (result.error == true) {
					alert(result.toastMessage);
				} else {
					console.log(result)
					$(".taiKhoanTable tbody").empty();
					$.each(result.object, function(i, audit) {
						var auditRow = '<tr>' +
							'<td>' + audit.username + '</td>' +
							'<td>' + audit.obj + '</td>' +
							'<td>' + audit.action + '</td>' +
							'<td>' + audit.timeStamp + '</td>';
						$('.taiKhoanTable tbody').append(auditRow);
					});

				}

			},
			error: function(e) {
				if (e.error == true) {
					alert(result.toastMessage);
				}
				console.log(e)
			}
		});
	};
});