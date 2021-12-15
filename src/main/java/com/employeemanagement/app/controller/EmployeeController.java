package com.employeemanagement.app.controller;

import com.employeemanagement.app.request.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService objBL;

	@PostMapping("")
	public ResponseEntity<Object> getList(@RequestBody EmployeeReq req) throws Exception {
		return ResponseEntity.ok(objBL.getlist(req));
	}

	@PostMapping("login")
	public ResponseEntity<Object> login (@RequestBody LoginReq req){
		return objBL.login(req);
	}

}
