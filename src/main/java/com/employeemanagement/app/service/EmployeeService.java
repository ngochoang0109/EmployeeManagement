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

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDA employeeDA;

	public ApiRes<Object> getlist(EmployeeReq req) {
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

	public ResponseEntity<Object> login(LoginReq req){
		DatabaseConfig.username=req.getUsername();
		DatabaseConfig.password = req.getPassword();
		if(employeeDA.checkConnect())
			return ResponseEntity.ok("Connect");
		else return ResponseEntity.status(400).body("Kiem tra lai tai khoan va mat khau");
	}

}
