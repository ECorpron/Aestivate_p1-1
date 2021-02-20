package com.revature.util;

import com.revature.factories.PostgreSQLSessionFactory;
import com.revature.factories.SessionFactory;

import javax.management.modelmbean.XMLParseException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstracts away what type of session factory has been made. Given a Database, creates and moderates a needed session
 * factory in a static block through a Singleton Design Pattern, so it is done upon class load in.
 */
public class SessionManager {

    private static SessionManager sess;

    static {
        try {
            sess = new SessionManager();
        } catch (XMLParseException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory connections;

    private SessionManager() throws XMLParseException {
        Database db = XMLReader.getDatabaseSet();
        String dbName = db.getSqlDatabase();

        if (dbName.equals("postgresql")) {
            connections = new PostgreSQLSessionFactory(db);
        } else {
            throw new XMLParseException("An invalid database name given");
        }
    }

    /**
     * Access method for the connection.
     * @return Returns a connection to the database
     */
    public static Connection getConnection() {
        try {
            //System.out.println("got in get connection");
            return connections.getConnection();
        } catch (SQLException throwables) {
            System.out.println("Couldn't make connection to database");
            throwables.printStackTrace();
        }
        return null;
    }
}
