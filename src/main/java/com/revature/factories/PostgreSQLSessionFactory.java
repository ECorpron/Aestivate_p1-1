package com.revature.factories;

import com.revature.util.Database;
import com.revature.util.XMLReader;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection factory for PostgreSQL
 */
public class PostgreSQLSessionFactory extends SessionFactory {

    private static BasicDataSource ds = new BasicDataSource();


    // For the postgreSQL connection to exist, need the postresql driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PostgreSQLSessionFactory() {
        Database sqlDb = XMLReader.getDatabaseSet();

        ds.setUrl(sqlDb.getUrl());
        ds.setUsername(sqlDb.getLoginName());
        ds.setPassword(sqlDb.getPassword());
        ds.setMinIdle(sqlDb.getMinIdle());
        ds.setMaxIdle(sqlDb.getMaxIdle());
        ds.setMaxOpenPreparedStatements(sqlDb.getMaxOpenPreparedStatements());
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
