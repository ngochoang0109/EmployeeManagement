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
import com.employeemanagement.app.helpers.ValidationHelper.Result;
import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.FilterReq;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.employeemanagement.app.service.*;

@Service
public class EmployeeService {

	@Autowired
	private HandlerBL handlerBL;
	@Autowired
	private EmployeeDA employeeDA;

	public ApiRes<Object> getlist(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Employee> employees = employeeDA.getList(req.getKeyword());
			apiRes.setObject(employees);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> add(EmployeeReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			employeeDA.add(req);
			apiRes.setObject(true);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> login(LoginReq req, HttpSession session) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			DataSource dataSource = employeeDA.connect(req);
			if (dataSource == null)
				apiRes = handlerBL.translateError(Result.login_fail, "Đăng nhập thất bại");
			else {
				session.setAttribute("datasource", dataSource);
				apiRes.setObject(true);
				apiRes.setError(false);
			}
		} catch (Exception e) {
			apiRes = handlerBL.translateError(Result.login_fail, "Đăng nhập thất bại");
		}
		return apiRes;

	}

	public ApiRes<Object> logout(HttpSession session) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			session.removeAttribute("dataSource");
			apiRes.setObject(true);
			apiRes.setError(false);
		} catch (Exception e) {
			apiRes = handlerBL.translateError(Result.login_fail, "Đăng xuất thất bại");
		}
		return apiRes;
	}
}
