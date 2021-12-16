package com.employeemanagement.app.controller;

import com.employeemanagement.app.dao.EmployeeDA;
import com.employeemanagement.app.entities.Employee;
import com.employeemanagement.app.request.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.service.EmployeeService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService objBL;

	@PostMapping("")
	public ResponseEntity<Object> getList( HttpSession session) throws Exception {
		if(!EmployeeDA.GetDataSource(session)){
			return ResponseEntity.status(400).body("Bad request");
		}
		return ResponseEntity.ok(objBL.getlist());
	}

	@PostMapping("login")
	public ResponseEntity<Object> login (@RequestBody LoginReq req, HttpSession session){

		return objBL.login(req, session);
	}
	@GetMapping("logout")
	public ResponseEntity<Object> logout (HttpSession session){
		return objBL.logout(session);
	}
}
