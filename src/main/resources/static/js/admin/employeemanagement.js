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
							'<button class="btn btn-danger btnDelete" >Xóa</button></td>';
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
		ajaxGet(1);
	});
	// xác nhận thêm viên
	$(document).on('click', '#btnSaveSubmit', function(event) {


		ajaxSaveNhanVien();
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
	//edit EMP
	$(document).on('click', '.btnUpdateEmp', function(event) {
		var editId = $(this).attr('id');
		var field = editId.split('.');
		var EmpId= field[1];
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


			},

			error: function(e) {
				alert("error");
				console.log("ERROR: ", e);
			}

		});




		$('#btnSaveSubmit').unbind().click(function() {

			var formData = new FormData();


			var name = $('#nameGrammar').val();
			var contentMarkdown = simplemde.value(); //get from textarea markdown
			var contentHTML = simplemde.options.previewRender(contentMarkdown);




			formData.append("idGrammar", idBaiGrammar);
			formData.append("name", name);
			formData.append("contentMarkdown", contentMarkdown);
			formData.append("contentHtml", contentHTML);


			$.ajax({
				data: formData,
				type: 'POST',
				url: "http://localhost:8080/api/admin/grammar/update",
				enctype: 'multipart/form-data',
				contentType: false,
				cache: false,
				processData: false,

				success: function(data) {
					$('#grammarModal').modal('hide');
					$('#info-success').text("Cập nhật bài grammar thành công");
					loadAllGrammar();

				},

				error: function(e) {
					alert("error");
					console.log("ERROR: ", e);
				}
			});
		});
	});
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