package com.employeemanagement.app.service;

import com.employeemanagement.app.dao.EmployeeDA;
import com.employeemanagement.app.entities.Audit;
import com.employeemanagement.app.entities.Employee;
import com.employeemanagement.app.helpers.ApiRes;
import com.employeemanagement.app.helpers.ValidationHelper.Result;
import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.FilterReq;
import com.employeemanagement.app.request.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.List;

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

    public ApiRes<Object> getInfor(EmployeeReq req) {
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try {
            Employee objEmployee = employeeDA.getInfor(req.getId());
            apiRes.setObject(objEmployee);
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

    /*
     * public ApiRes<Object> update(EmployeeReq req) { ApiRes<Object> apiRes = new
     * ApiRes<Object>(); try { employeeDA.update(req); apiRes.setObject(true); }
     * catch (Exception e) { apiRes.setError(true);
     * apiRes.setErrorReason(e.getMessage()); } return apiRes; }
     */

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

    public ApiRes<Object> delEmp(int id) {
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try {
            employeeDA.delEmp(id);
            apiRes.setObject(true);
        } catch (Exception e) {
            apiRes.setError(true);
            apiRes.setErrorReason(e.getMessage());
        }
        return apiRes;
    }

    /*
     * public ApiRes<Object> getEmpById(int id){ ApiRes<Object> apiRes= new
     * ApiRes<Object>(); try { Employee employee= employeeDA.getInfor(id);
     * apiRes.setObject(employee); } catch (Exception e) { e.printStackTrace();
     * apiRes.setError(true); apiRes.setErrorReason(e.getMessage()); } return
     * apiRes; }
     */

    public ApiRes<Object> updateEmp(Employee employee, boolean isUpdate) {
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try {
            employeeDA.update(employee, isUpdate);
            apiRes.setObject(true);
        } catch (Exception e) {
            e.printStackTrace();
            apiRes.setError(true);
            apiRes.setErrorReason(e.getMessage());
        }
        return apiRes;
    }

    public ApiRes<Object> audits() {
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try {
            List<Audit> audits = employeeDA.getAudits();
            apiRes.setObject(audits);
        } catch (Exception e) {
            apiRes.setError(true);
            apiRes.setErrorReason(e.getMessage());
        }
        return apiRes;
    }
}
