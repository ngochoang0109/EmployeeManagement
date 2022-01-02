$(document).ready(function() {

	// load first when coming page
	ajaxGet(1);

	function ajaxGet(page) {
		var data = { keyword: "" };
		page = 1;
		$.ajax({
			type: "POST",
			data: JSON.stringify(data),
			contentType: "application/json",
			url: "http://localhost:8080/api/employee/getlist",
			success: function(result) {
				if (result.error == true) {
					alert(result.toastMessage);
				}
				else{
				console.log(result)
				$.each(result.object, function(i, emp) {
					var taiKhoanRow = '<tr>' +
						'<td>' + emp.id + '</td>' +
						'<td>' + emp.name + '</td>' +
						'<td>' + emp.email + '</td>' +
						'<td>' + emp.dateOfBirth + '</td>' +
						'<td>' + emp.taxCode + '</td>' +
						'<td>' + emp.departmentId + '</td>' +
						'<td>' + emp.managerId + '</td>' +
						'<td>' + emp.salary + '</td>';
					taiKhoanRow += '</td>' +
						'<td width="0%">' + '<input type="hidden" id="idTaiKhoan" value=' + emp.id + '>' + '</td>' +
						//					                  '<td><button class="btn btn-primary btnCapNhat" >Cập nhật</button></td>' + 
						'<td><button class="btn btn-danger btnXoa" >Xóa</button></td>';;
					$('.taiKhoanTable tbody').append(taiKhoanRow);

				});
				}

				/*	if (result.totalPages > 1) {
						for (var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
							var li = '<li class="page-item "><a class="pageNumber">' + numberPage + '</a></li>';
							$('.pagination').append(li);
						};
	
						// active page pagination
						$(".pageNumber").each(function(index) {
							if ($(this).text() == page) {
								$(this).parent().removeClass().addClass("page-item active");
							}
						});
					};*/
			},
			error: function(e) {
				alert("Lỗi rồi: ", e);
				console.log("Error", e);
			}
		});
	};

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
	
	// click thêm tài khoản
	$(document).on('click', '.btnThemNhanVien', function(event) {
		
		$("#nhanVienModal").modal();
	});

	// xác nhận thêm tài khoản
	$(document).on('click', '#btnAddSubmit', function(event) {
		
		ajaxAddNhanVien();
		ajaxGet(1);
	});

	function ajaxAddNhanVien() {
		var data = JSON.stringify($('.nhanVienForm').serializeJSON());
		console.log(data);

		// do post
		$.ajax({
			async: false,
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/employee/add",
			data: data,
			success: function(response) {
				if (response.object === true) {
					$('#nhanVienModal').modal('hide');
					alert("Thêm thành công");
				} else {
					alert(response.errorReason);
				}

			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}

	// delete request
	$(document).on("click", ".btnXoa", function() {

		var taiKhoanId = $(this).parent().prev().children().val();
		var confirmation = confirm("Bạn chắc chắn xóa tài khoản này ?");
		if (confirmation) {
			$.ajax({
				type: "DELETE",
				url: "http://localhost:8080/api/admin/tai-khoan/delete/" + taiKhoanId,
				success: function(resultMsg) {
					alert("Xóa thành công")
					resetData();
				},
				error: function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
	});

	// event khi ẩn modal form
	$('#taiKhoanModal').on('hidden.bs.modal', function(e) {
		/*e.preventDefault();
		$('.taiKhoanForm input').next().remove();*/
	});


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

});