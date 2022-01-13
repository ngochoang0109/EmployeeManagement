package com.employeemanagement.app.controller;

import com.employeemanagement.app.dao.EmployeeDA;
import com.employeemanagement.app.helpers.ApiRes;
import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.FilterReq;
import com.employeemanagement.app.request.LoginReq;
import com.employeemanagement.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService objBL;

    @RequestMapping(value = "/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getList(HttpSession session, @RequestBody FilterReq req) throws Exception {
        if (!EmployeeDA.GetDataSource(session)) {
            ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
        }
        return ResponseEntity.ok(objBL.getlist(req));
    }

    @RequestMapping(value = "/getinfor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getInfor(HttpSession session, @RequestBody EmployeeReq req) throws Exception {
        if (!EmployeeDA.GetDataSource(session)) {
            ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
        }
        return ResponseEntity.ok(objBL.getInfor(req));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> add(HttpSession session, @RequestBody EmployeeReq req) throws Exception {
        if (!EmployeeDA.GetDataSource(session)) {
        	ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> delete(@RequestBody EmployeeReq employeeReq, HttpSession session) {
        if (!EmployeeDA.GetDataSource(session)) {
            ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
        }
        return ResponseEntity.ok(objBL.delEmp(employeeReq.getId()));
    }

    /*
     * @RequestMapping(value = "/getinfor", method = RequestMethod.POST, consumes =
     * "application/json", produces = "application/json; charset=utf-8") public
     * ResponseEntity<Object> getEmployee(@RequestBody EmployeeReq employeeReq,
     * HttpSession session) { if (!EmployeeDA.GetDataSource(session)) {
     * ApiRes<Object> apiRes = new ApiRes<Object>(); apiRes.setError(true);
     * apiRes.setErrorReason("Vui lòng đăng nhập"); return
     * ResponseEntity.ok(apiRes); } return
     * ResponseEntity.ok(objBL.getEmpById(employeeReq.getId())); }
     */

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> updateEmp(@RequestBody EmployeeReq employeeReq, HttpSession session) {
        if (!EmployeeDA.GetDataSource(session)) {
            ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
        }
        return ResponseEntity.ok(objBL.updateEmp(employeeReq, true));
    }

    @GetMapping("/audit")
    public ResponseEntity<Object> getAudit(HttpSession session) {
        if (!EmployeeDA.GetDataSource(session)) {
            ApiRes<Object> apiRes = new ApiRes<Object>();
            apiRes.setError(true);
            apiRes.setErrorReason("Vui lòng đăng nhập");
            return ResponseEntity.ok(apiRes);
        }
        return ResponseEntity.ok(objBL.audits());
    }
}
