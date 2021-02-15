package com.revature.factories;

import com.revature.util.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract class that denotes what needs to be implemented for a session factory. In theory, could be multiple
 * different types of sessions, not just to postgresql
 */
public interface SessionFactory {

    //abstract SessionFactory(Configuration con);
    public Connection getConnection() throws SQLException;
    public String getDatabaseName();
}
