package com.employeemanagement.app.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.jdbc-url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.hikari.minimum-idle}")
	private int minimumIdle;

	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int maximumPoolSize;

	@Value("${spring.datasource.hikari.idle-timeout}")
	private int idleTimeout;

	@Value("${spring.datasource.hikari.connection-timeout}")
	private int connectionTimeout;

	@Value("${spring.datasource.hikari.max-lifetime}")
	private int maxLifetime;

	@Value("${spring.datasource.hikari.pool-name}")
	private String poolName;

	@Value("${spring.datasource.hikari.auto-commit}")
	private Boolean autoCommit;

	@Value("${spring.datasource.hikari.leak-detection-threshold}")
	private int leakDetectionThreshold;
	
	@Bean(name = "DatabaseConfig")
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setMinimumIdle(minimumIdle);
		config.setMaximumPoolSize(maximumPoolSize);
		config.setIdleTimeout(idleTimeout);
		config.setConnectionTimeout(connectionTimeout);
		config.setMaxLifetime(maxLifetime);
		config.setPoolName(poolName);
		config.setAutoCommit(autoCommit);
		config.setLeakDetectionThreshold(leakDetectionThreshold);		 
		return new HikariDataSource(config);
	}
}
