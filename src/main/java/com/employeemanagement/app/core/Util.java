package com.employeemanagement.app.core;

public class Util {

    public static String driverClassName = "oracle.jdbc.OracleDriver";


    public static String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/ORCLPDB";

    public static int minimumIdle = 5;

    public static int maximumPoolSize = 10;

    public static int idleTimeout = 10000;

    public static int connectionTimeout = 30000;

    public static int maxLifetime = 2000000;

    public static String poolName = "springJPAHikariCP";

    public static Boolean autoCommit = false;

    public static int leakDetectionThreshold = 2000;
}
