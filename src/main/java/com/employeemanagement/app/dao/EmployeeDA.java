package com.employeemanagement.app.dao;

import com.employeemanagement.app.entities.Audit;
import com.employeemanagement.app.entities.Employee;
import com.employeemanagement.app.request.EmployeeReq;
import com.employeemanagement.app.request.LoginReq;
import oracle.jdbc.OracleTypes;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void update(Employee obj, Boolean bolIsUpdate) throws Exception {
        try (Connection conn = databaseConfig.getConnection()) {
            try {
                if (bolIsUpdate)
                    update(conn, obj);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public boolean delEmp(int id) throws SQLException {
        Boolean bolResult = false;
        try (Connection conn = databaseConfig.getConnection()) {
            try {
                this.delete(conn, id);
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
            String callProc = "{call DBSECURITYGR06.employee_getlist(?,?)}";
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
        String callProc = "{call DBSECURITYGR06.employee_add(?,?,?,?,?,?)}";
        try (CallableStatement proc = conn.prepareCall(callProc)) {
            proc.setObject(1, obj.getName());
            proc.setObject(2, obj.getDepartmentId());
            proc.setObject(3, obj.getTaxCode());
            proc.setObject(4, obj.getSalary());
            proc.setObject(5, obj.getEmail());
            proc.setObject(6, obj.getDateOfBirth());
            proc.execute();
        }
    }

    private void update(Connection conn, Employee obj) throws Exception {
        String callProc = "{call DBSECURITYGR06.udpateEmp(?,?,?,?,?,?,?,?)}";
        try (CallableStatement proc = conn.prepareCall(callProc)) {
            proc.setInt(1, obj.getId());
            proc.setString(2, obj.getName());
            proc.setString(3, obj.getDepartmentId());
            proc.setString(4, obj.getDateOfBirth());
            proc.setString(5, obj.getTaxCode());
            proc.setInt(6, obj.getSalary());
            proc.setString(7, obj.getEmail());
            proc.setInt(8, obj.getManagerId());
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

    private void delete(Connection conn, int strId) throws Exception {
        String callProc = "{call DBSECURITYGR06.employee_del(?)}";
        try (CallableStatement proc = conn.prepareCall(callProc)) {
            proc.setObject(1, strId);
            proc.execute();
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

    public List<Audit> getAudits() throws Exception {
        List<Audit> audits = null;
        try (Connection conn = databaseConfig.getConnection()) {
            PGobject objPGobject = new org.postgresql.util.PGobject();
            objPGobject.setType("refcursor");
            String query = "SELECT USERNAME , OBJ_NAME , ACTION_NAME , TIMESTAMP FROM DBA_AUDIT_TRAIL WHERE OBJ_NAME = 'EMPLOYEE' ORDER BY TIMESTAMP DESC";
            try (Statement cs = conn.createStatement()) {
                ResultSet results = cs.executeQuery(query);
                audits = new ArrayList<Audit>();
                if (results == null)
                    return null;
                while (results.next()) {
                    Audit obj = parseAudit(results, false);
                    audits.add(obj);
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return audits;
    }

    private Audit parseAudit(ResultSet results, boolean b) throws Exception {
        Audit obj = new Audit();
        obj.setUsername(results.getString("USERNAME"));
        obj.setObj(results.getString("OBJ_NAME"));
        obj.setAction(results.getString("ACTION_NAME"));
        obj.setTimeStamp(results.getTimestamp("TIMESTAMP"));
        return obj;
    }

}
