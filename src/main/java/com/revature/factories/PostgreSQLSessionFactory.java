package com.revature.factories;

import com.revature.util.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection factory for PostgreSQL
 */
public class PostgreSQLSessionFactory extends SessionFactory {

    private static SessionFactory connFactory;

    // For the postgreSQL connection to exist, need the postresql driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static getter for the connection
     */
    public SessionFactory getInstance() {
        if (connFactory == null) {
            connFactory = new PostgreSQLSessionFactory();
        }
        return connFactory;
    }

    public Connection getConnection(Configuration con) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(con.getDbUrl(), con.getDbUsername(), con.getDbPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
