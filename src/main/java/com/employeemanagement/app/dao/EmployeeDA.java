package com.employeemanagement.app.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.LoginReq;
import org.postgresql.util.PGobject;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.employeemanagement.app.entities.Employee;

import oracle.jdbc.OracleTypes;
import oracle.sql.TIMESTAMP;

@Service
public class EmployeeDA {

	private static DataSource databaseConfig;

	public static boolean GetDataSource(HttpSession session) {
		databaseConfig = (DataSource) session.getAttribute("datasource");
		if (databaseConfig == null)
			return false;
		return true;
	}

	public Boolean add(EmployeeReq obj) throws Exception {
		Boolean bolResult = false;
		try (Connection conn = databaseConfig.getConnection()) {
			try {
				add(conn, obj);
				conn.commit();
				bolResult = true;
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return bolResult;
	}

	/*
	 * public Boolean update(Employee obj) throws Exception { Boolean bolResult =
	 * false; try (Connection conn = databaseConfig.getConnection()) { try {
	 * update(conn, obj); conn.commit(); bolResult = true; } catch (Exception e) {
	 * conn.rollback(); e.printStackTrace(); throw new RuntimeException(e); } }
	 * return bolResult; }
	 */

	public Boolean delete(String strId) throws Exception {
		Boolean bolResult = false;
		try (Connection conn = databaseConfig.getConnection()) {
			try {
				bolResult = delete(conn, strId);
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return bolResult;
	}

	public Employee getInfor(Integer strId) throws Exception {
		Employee obj = null;
		try (Connection conn = databaseConfig.getConnection()) {
			PGobject objPGobject = new org.postgresql.util.PGobject();
			objPGobject.setType("refcursor");
			String callProc = "{call DBSECURITYGR06.Employee_getinfor(?,?)}";
			try (CallableStatement proc = conn.prepareCall(callProc)) {
				proc.registerOutParameter(1, OracleTypes.CURSOR);
				proc.setObject(2, strId);
				proc.execute();
				ResultSet results = (ResultSet) proc.getObject(1);
				while (results.next()) {
					obj = parseInfor(results, false);
				}
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return obj;
	}

	public List<Employee> getList(String strKeyword) throws Exception {
		List<Employee> lst = null;
		try (Connection conn = databaseConfig.getConnection()) {
			PGobject objPGobject = new org.postgresql.util.PGobject();
			objPGobject.setType("refcursor");
			String callProc = "{call DBSECURITYGR06.Employee_getlist(?,?)}";
			try (CallableStatement proc = conn.prepareCall(callProc)) {

				proc.setString(1, strKeyword);
				proc.registerOutParameter(2, OracleTypes.CURSOR);

				proc.execute();
				ResultSet results = (ResultSet) proc.getObject(2);
				if (results.isBeforeFirst())
					lst = new ArrayList<Employee>();
				while (results.next()) {
					Employee obj = parseInfor(results, false);
					lst.add(obj);
				}
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return lst;
	}

	private void add(Connection conn, Employee obj) throws Exception {
		String callProc = "{call DBSECURITYGR06.Employee_add(?,?,?,?,?,?,?)}";
		try (CallableStatement proc = conn.prepareCall(callProc)) {

			proc.setObject(1, obj.getName());
			proc.setObject(2, obj.getDepartmentId());
			proc.setObject(3, obj.getTaxCode());
			proc.setObject(4, obj.getSalary());
			proc.setObject(5, obj.getEmail());
			proc.setObject(6, obj.getManagerId());
			proc.setObject(7, obj.getDateOfBirth());
			proc.execute();
		}
	}

	/*
	 * private Boolean update(Connection conn, Employee obj) throws Exception {
	 * String callProc = "{call DBSECURITYGR06.Employee_upd(?,?,?,?,?,?,?)}"; try
	 * (CallableStatement proc = conn.prepareCall(callProc)) {
	 * proc.registerOutParameter(1, Types.BOOLEAN); proc.setObject(1,
	 * obj.getName()); proc.setObject(2, obj.getDepartmentId()); proc.setObject(3,
	 * obj.getTaxCode()); proc.setObject(4, obj.getSalary()); proc.setObject(5,
	 * obj.getEmail()); proc.setObject(6, obj.getManagerId()); proc.setObject(7,
	 * obj.getDateOfBirth()); proc.execute(); return
	 * Boolean.parseBoolean(proc.getObject(1).toString()); } }
	 */

	private Boolean delete(Connection conn, String strId) throws Exception {
		String callProc = "{? = call pim.pim_Employee_del(?)}";
		try (CallableStatement proc = conn.prepareCall(callProc)) {
			proc.registerOutParameter(1, Types.BOOLEAN);
			proc.setObject(2, strId);
			proc.execute();
			return Boolean.parseBoolean(proc.getObject(1).toString());
		}
	}

	private Employee parseInfor(ResultSet results, boolean bolIsInfor) throws Exception {
		Employee obj = new Employee();
		obj.setId(results.getInt("id"));
		obj.setEmail(results.getString("email"));
		obj.setName(results.getString("name"));
		obj.setDepartmentId(results.getString("departmentid"));
		obj.setTaxCode(results.getString("taxcode"));
		obj.setManagerId(results.getInt("managerid"));
		obj.setSalary(results.getInt("salary"));
		obj.setDateOfBirth(results.getString("dateofbirth"));
		return obj;
	}

	public DataSource connect(LoginReq req) {
		try {
			return DatabaseConfig.dataSource(req.getUsername(), req.getPassword());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
