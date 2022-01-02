package com.employeemanagement.app.controller;

import com.employeemanagement.app.dao.EmployeeDA;
import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.FilterReq;
import com.employeemanagement.app.request.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.employeemanagement.app.service.EmployeeService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService objBL;

	@RequestMapping(value = "/getlist", method = RequestMethod.POST, 
			consumes = "application/json", 
			produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getList(HttpSession session, @RequestBody FilterReq req) throws Exception {
		if (!EmployeeDA.GetDataSource(session)) {
			return ResponseEntity.status(400).body("Bad request");
		}
		return ResponseEntity.ok(objBL.getlist(req));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> add(HttpSession session, @RequestBody EmployeeReq req) throws Exception {
		if (!EmployeeDA.GetDataSource(session)) {
			return ResponseEntity.status(400).body("Bad request");
		}
		return ResponseEntity.ok(objBL.add(req));
	}

	@PostMapping("login")
	public ResponseEntity<Object> login(@RequestBody LoginReq req, HttpSession session) {
		return ResponseEntity.ok(objBL.login(req, session));
	}

	@GetMapping("logout")
	public ResponseEntity<Object> logout(HttpSession session) {
		return ResponseEntity.ok(objBL.logout(session));
	}
}
