package com.employeemanagement.app.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	//@Value("${spring.datasource.driver-class-name}")
	private static String driverClassName = "oracle.jdbc.OracleDriver";

	//@Value("${spring.datasource.jdbc-url}")
	private static String jdbcUrl ="jdbc:oracle:thin:@//localhost:1521/ORCLPDB";

//	@Value("${spring.datasource.username}")
	public static String username;

//	@Value("${spring.datasource.password}")
	public static String password;

	//@Value("${spring.datasource.hikari.minimum-idle}")
	private static int minimumIdle = 5;

	//@Value("${spring.datasource.hikari.maximum-pool-size}")
	private static int maximumPoolSize = 10;

	//@Value("${spring.datasource.hikari.idle-timeout}")
	private static int idleTimeout = 10000;

	//@Value("${spring.datasource.hikari.connection-timeout}")
	private static int connectionTimeout= 30000;

	//@Value("${spring.datasource.hikari.max-lifetime}")
	private static int maxLifetime =2000000;

	//@Value("${spring.datasource.hikari.pool-name}")
	private static String poolName = "springJPAHikariCP";

	//@Value("${spring.datasource.hikari.auto-commit}")
	private static Boolean autoCommit = false;

	//@Value("${spring.datasource.hikari.leak-detection-threshold}")
	private static int leakDetectionThreshold = 2000;

	private static DataSource _dataSource = null;
	//@Bean(name = "DatabaseConfig")
	public static DataSource dataSource() {
		if(_dataSource!=null)
			return _dataSource;
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
		_dataSource = new HikariDataSource(config);
		return _dataSource;
	}
}
