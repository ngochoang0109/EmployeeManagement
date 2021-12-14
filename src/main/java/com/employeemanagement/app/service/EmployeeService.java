package com.employeemanagement.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
			List<Employee> employees = employeeDA.getList(null, null, null);
			apiRes.setObject(employees);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

}
