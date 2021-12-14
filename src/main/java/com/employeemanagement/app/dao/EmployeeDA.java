package com.employeemanagement.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.util.PGobject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.employeemanagement.app.entities.Employee;

import oracle.jdbc.OracleTypes;

@Service
public class EmployeeDA {

	@Autowired
	@Qualifier("DatabaseConfig")
	private DataSource databaseConfig;

	public Boolean add(Employee obj) throws Exception {
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

	public Boolean update(Employee obj, Boolean bolIsUpdate) throws Exception {
		Boolean bolResult = false;
		try (Connection conn = databaseConfig.getConnection()) {
			try {
				if (bolIsUpdate)
					update(conn, obj);
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

	public Employee getInfor(String strId, String strLocaleCcode) throws Exception {
		return getInfor(strId, strLocaleCcode, true);
	}

	public Employee getInfor(String strId, String strLocaleCcode, Boolean bolLoadRelation) throws Exception {
		Employee obj = null;
		try (Connection conn = databaseConfig.getConnection()) {
			PGobject objPGobject = new org.postgresql.util.PGobject();
			objPGobject.setType("refcursor");
			String callProc = "{? = call pim.pim_Employee_getinfor(?,?,?)}";
			try (CallableStatement proc = conn.prepareCall(callProc)) {
				proc.registerOutParameter(1, Types.OTHER);
				proc.setObject(2, objPGobject);
				proc.setObject(3, strId);
				proc.setObject(4, strLocaleCcode);
				proc.execute();
				ResultSet results = (ResultSet) proc.getObject(1);
				while (results.next()) {
					obj = parseInfor(results, true);
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
			String callProc = "{call Employee_getlist(?,?)}";
			try (CallableStatement proc = conn.prepareCall(callProc)) {
				
				proc.setObject(1, strKeyword);
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
		String callProc = "{? = call pim.pim_Employee_add(?,?,?,?,?,?,?)}";
		try (CallableStatement proc = conn.prepareCall(callProc)) {
			proc.registerOutParameter(1, Types.VARCHAR);
			proc.setObject(2, obj.getId());
			proc.setObject(3, obj.getDateOfBirth());
			proc.setObject(4, obj.getName());
			proc.setObject(5, obj.getEmail());
			proc.setObject(6, obj.getTaxId());
			proc.setObject(7, obj.getDepartmentId());
			proc.execute();
			obj.setId(proc.getObject(1).toString());
		}
	}

	private Boolean update(Connection conn, Employee obj) throws Exception {
		String callProc = "{? = call pim.pim_Employee_upd(?,?,?)}";
		try (CallableStatement proc = conn.prepareCall(callProc)) {
			proc.registerOutParameter(1, Types.BOOLEAN);
			proc.setObject(2, obj.getId());
			proc.setObject(3, obj.getDateOfBirth());
			proc.setObject(4, obj.getName());
			proc.setObject(5, obj.getEmail());
			proc.setObject(6, obj.getTaxId());
			proc.setObject(7, obj.getDepartmentId());
			proc.execute();
			return Boolean.parseBoolean(proc.getObject(1).toString());
		}
	}

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
		obj.setId(results.getString("id"));
		obj.setEmail(results.getString("email"));
		obj.setName(results.getString("Employee_name"));
		obj.setDepartmentId(results.getString("departmentId"));
		obj.setTaxId(results.getString("taxCode"));
		obj.setDateOfBirth((Timestamp) results.getObject("dateOfBirth"));
		return obj;
	}

}
