package com.employeemanagement.app.entities;

public class Employee {
	private String Id;
	private String Name;
	private String DateOfBirth;
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

	public String getTaxId() {
		return TaxId;
	}

	public void setTaxId(String taxId) {
		TaxId = taxId;
	}
}
