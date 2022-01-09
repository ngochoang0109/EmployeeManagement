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
				else {
					console.log(result)
					$(".taiKhoanTable tbody").empty();
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
						//taiKhoanRow += '<td>' +
						taiKhoanRow += '<td>' + '<input style="display:none" id="idTaiKhoan" value=' + emp.id + '>' +
							'<button class="btn btn-primary btnUpdateEmp" id="Update.' + emp.id + '" >Cập nhật</button>' +
							'<button class="btn btn-danger btnDelete" id="Delete.' + emp.id + '" >Xóa</button></td>';
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
				if (e.error == true) {
					alert(result.toastMessage);
				}
				console.log(e)
			}
		});
	};

	$(document).on('click', '#btnLogout', function(event) {
		event.preventDefault();
		ajaxLogout();
	});

// reset table after post, put, filter
	function resetData() {
		var page = $('li.active').children().text();
		$('.taiKhoanTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(page);
	};
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

	// click thêm nhân viên
	$(document).on('click', '.btnThemNhanVien', function(event) {

		$('#btnSaveSubmit').hide();
		$('#btnAddSubmit').show();
		var modal = $('#nhanVienModal');
		modal.find('.modal-header #exampleModalLabel').text("Thêm mới nhân viên");
		$("#nhanVienModal").modal();
	});
	// xác nhận thêm viên
	$(document).on('click', '#btnAddSubmit', function(event) {

		ajaxAddNhanVien();
		
	});
	// xác nhận thêm viên
	$(document).on('click', '#btnSaveSubmit', function(event) {


		ajaxSaveNhanVien();
		
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
					resetData();
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
	//edit EMP
	$(document).on('click', '.btnUpdateEmp', function(event) {
		var editId = $(this).attr('id');
		var field = editId.split('.');
		var EmpId = field[1];
		var data = { id: EmpId };
		$.ajax({
			type: 'POST',
			url: "http://localhost:8080/api/employee/getinfor",
			data: JSON.stringify(data),
			contentType: "application/json",
			success: function(result) {
				//set data for modal

				console.log(result)
				var modal = $('#nhanVienModal');
				modal.find('.modal-body #empId').val(result.object.id);
				modal.find('.modal-body #empManagerId').val(result.object.managerId);
				modal.find('.modal-body #empName').val(result.object.name);
				modal.find('.modal-body #empEmail').val(result.object.email);
				modal.find('.modal-body #empTaxCode').val(result.object.taxCode);
				modal.find('.modal-body #empDateOfBirth').val(result.object.dateOfBirth);
				modal.find('.modal-body #departmentId').val(result.object.departmentId);
				modal.find('.modal-body #empSalary').val(result.object.salary);
				modal.find('.modal-header #titleModal').text("Cập nhật thông tin nhân viên");


				//simplemde = null;
				//$('#btnUpdate').show();
				$('#btnSaveSubmit').show();
				$('#btnAddSubmit').hide();
				$('#nhanVienModal').modal('show');
				resetData()


			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});




		$('#btnSaveSubmit').unbind().click(function() {
			var data = JSON.stringify($('.nhanVienForm').serializeJSON());
			console.log(data);

			// do post
			$.ajax({
				async: false,
				type: "POST",
				contentType: "application/json",
				url: "http://localhost:8080/api/employee/update",
				data: data,
				success: function(response) {
					if (response.object === true) {
						$('#nhanVienModal').modal('hide');
						alert("Cập nhật thành công");
						resetData();
					} else {
						alert(response.errorReason);
					}

				},
				error: function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});
		});
	});
	// delete request
	$(document).on("click", ".btnDelete", function() {

		var editId = $(this).attr('id');
		var field = editId.split('.');
		var EmpId = field[1];
		
		var data = { id: EmpId };
		var confirmation = confirm("Bạn chắc chắn xóa tài khoản này ?");
		if (confirmation) {
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "http://localhost:8080/api/employee/delete",
				data: JSON.stringify(data),
				success: function(response) {
					if (response.object === true) {
						$('#nhanVienModal').modal('hide');
						alert("Xóa thành công");
						resetData();
					} else {
						alert(response.errorReason);
					}
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