package com.employeemanagement.app.service;

import java.util.List;

import com.employeemanagement.app.dao.DatabaseConfig;
import com.employeemanagement.app.request.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employeemanagement.app.dao.EmployeeDA;
import com.employeemanagement.app.entities.Employee;
import com.employeemanagement.app.helpers.ApiRes;
import com.employeemanagement.app.request.EmployeeReq;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDA employeeDA;

	public ApiRes<Object> getlist() {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Employee> employees = employeeDA.getList("");
			apiRes.setObject(employees);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ResponseEntity<Object> login(LoginReq req, HttpSession session){
		DataSource dataSource = employeeDA.connect(req);
		if(dataSource==null)
			return ResponseEntity.status(401).body("Kiem tra lai tai khoan hoac mat khau");
		session.setAttribute("datasource", dataSource);
		return ResponseEntity.ok().body("Connect success");
	}

	public ResponseEntity<Object> logout(HttpSession session) {
		session.removeAttribute("dataSource");
		return ResponseEntity.ok().body("logout success");
	}
}
