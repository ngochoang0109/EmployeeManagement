package com.employeemanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ControllerAdvice;

@SpringBootApplication
@ComponentScan(basePackages = { "com.employeemanagement.app.controller", "com.employeemanagement.app" })
@ControllerAdvice
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}
