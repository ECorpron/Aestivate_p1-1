package com.revature.util;

import com.revature.factories.PostgreSQLSessionFactory;
import com.revature.factories.SessionFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.management.modelmbean.XMLParseException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstracts away what type of session factory has been made. Given a Database, creates and moderates needed session
 * factory.
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
