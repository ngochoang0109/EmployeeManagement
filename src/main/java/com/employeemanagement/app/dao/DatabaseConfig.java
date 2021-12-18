package com.employeemanagement.app.dao;

import javax.sql.DataSource;

import com.employeemanagement.app.core.Util;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    // public static Map<String, DataSource> dataSources = new HashMap<>();

    public static DataSource dataSource(String username, String password) {
	/*	if(dataSources.get(username+password)!=null){
			return dataSources.get(username+password);
		}*/
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
        DataSource dataSource = new HikariDataSource(config);
        //	dataSources.put(username+password,dataSource);
        return dataSource;
    }
}
