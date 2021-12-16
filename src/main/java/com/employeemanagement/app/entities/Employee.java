package com.employeemanagement.app.entities;

import java.sql.Timestamp;

public class Employee {
    private String Id;
    private String Name;
    private Timestamp DateOfBirth;
    private String Email;
    private String DepartmentId;
    private String TaxId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Timestamp getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getTaxId() {
        return TaxId;
    }

    public void setTaxId(String taxId) {
        TaxId = taxId;
    }
}
