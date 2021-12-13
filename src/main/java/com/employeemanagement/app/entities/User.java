package com.employeemanagement.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private long Id;
	private String Email;
	@JsonIgnore
	private String Password;
	@JsonIgnore
	private String ConfirmPassword;
	private String Name;
	private String PhoneNumber;
	private String Address;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}
