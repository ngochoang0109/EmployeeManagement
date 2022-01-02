package com.employeemanagement.app.entities;

import oracle.sql.TIMESTAMP;

public class Employee {
	private Integer Id;
	private String Name;
	private String DateOfBirth;
	private String Email;
	private String DepartmentId;
	private String TaxCode;
	private Integer Salary;
	private Integer ManagerId;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
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

	public String getTaxCode() {
		return TaxCode;
	}

	public void setTaxCode(String taxCode) {
		TaxCode = taxCode;
	}

	public Integer getSalary() {
		return Salary;
	}

	public void setSalary(Integer salary) {
		Salary = salary;
	}

	public Integer getManagerId() {
		return ManagerId;
	}

	public void setManagerId(Integer managerId) {
		ManagerId = managerId;
	}

}
