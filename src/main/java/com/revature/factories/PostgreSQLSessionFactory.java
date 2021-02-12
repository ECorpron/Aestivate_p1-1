package com.revature.factories;

import com.revature.util.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection factory for PostgreSQL
 */
public class PostgreSQLSessionFactory extends SessionFactory {

    private BasicDataSource ds = new BasicDataSource();
    private Configuration config;


    // For the postgreSQL connection to exist, need the postresql driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PostgreSQLSessionFactory(Configuration config) {
        this.config = config;
        ds.setUrl(config.getDbUrl());
        ds.setUsername(config.getDbUsername());
        ds.setPassword(config.getDbPassword());
        ds.setMinIdle(config.getMinIdle());
        ds.setMaxIdle(config.getMaxIdle());
        ds.setMaxOpenPreparedStatements(config.getMaxOpenPreparedStatements());
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
