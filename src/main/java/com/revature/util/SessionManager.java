package com.revature.util;

import com.revature.factories.PostgreSQLSessionFactory;
import com.revature.factories.SessionFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstracts away what type of session factory has been made. Given a Database, creates and moderates needed session
 * factory.
 */
public class SessionManager {

    private static SessionManager sess = new SessionManager();
    private static SessionFactory connections;

    private SessionManager() {
        Database db = XMLReader.getDatabaseSet();
        String dbName = db.getSqlDatabase();

        if (dbName.equals("postgresql")) {
            connections = new PostgreSQLSessionFactory(db);
        }
    }

    public static Connection getConnection() {
        try {
            return connections.getConnection();
        } catch (SQLException throwables) {
            System.out.println("Couldn't make connection to database");
            throwables.printStackTrace();
        }
        return null;
    }
}
