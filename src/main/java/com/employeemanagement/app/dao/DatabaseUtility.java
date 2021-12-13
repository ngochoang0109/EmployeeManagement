package com.employeemanagement.app.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseUtility {
	@Autowired
	Environment environment;

	@Bean(name = "DatabaseUtility")
	public DataSource DatabaseUtility() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setJdbcUrl(environment.getProperty("spring.datasource.jdbc-url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		dataSource.setMinimumIdle(Integer.parseInt(environment.getProperty("spring.datasource.hikari.minimum-idle")));
		dataSource.setMaximumPoolSize(
				Integer.parseInt(environment.getProperty("spring.datasource.hikari.maximum-pool-size")));
		dataSource.setPoolName(environment.getProperty("spring.datasource.hikari.pool-name"));
		dataSource.setAutoCommit(Boolean.parseBoolean(environment.getProperty("spring.datasource.hikari.auto-commit")));
		return dataSource;
	}
}
