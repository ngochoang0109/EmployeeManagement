$(document).ready(function() {

	// load first when coming page
	window.onload = function() {

	};


	// xác nhận thêm tài khoản
	$(document).on('click', '#btnSubmit', function(event) {
		event.preventDefault();
		ajaxPostTaiKhoan();
	});

	function ajaxPostTaiKhoan() {
		var data = JSON.stringify($('.login-form').serializeJSON());
		console.log(data);

		// do post
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/employee/login",
			enctype: 'multipart/form-data',
			data: data,
			success: function(response) {
				if (response.object == true) {
					alert("Success");
					window.location.href = "/admin/employee"
				}
				else {
					console.log("ERROR: ", response.toastMessage);
				}
			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

	}



	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [o[this.name]];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);

	// remove element by class name
	function removeElementsByClass(className) {
		var elements = document.getElementsByClassName(className);
		while (elements.length > 0) {
			elements[0].parentNode.removeChild(elements[0]);
		}
	}
});