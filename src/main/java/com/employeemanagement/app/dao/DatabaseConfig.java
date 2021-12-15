package com.employeemanagement.app.dao;

import javax.sql.DataSource;

import com.employeemanagement.app.core.Util;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	public static DataSource dataSource(String username, String password) {

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(Util.driverClassName);
		config.setJdbcUrl(Util.jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setMinimumIdle(Util.minimumIdle);
		config.setMaximumPoolSize(Util.maximumPoolSize);
		config.setIdleTimeout(Util.idleTimeout);
		config.setConnectionTimeout(Util.connectionTimeout);
		config.setMaxLifetime(Util.maxLifetime);
		config.setPoolName(Util.poolName);
		config.setAutoCommit(Util.autoCommit);
		config.setLeakDetectionThreshold(Util.leakDetectionThreshold);

		return new HikariDataSource(config);
	}
}
