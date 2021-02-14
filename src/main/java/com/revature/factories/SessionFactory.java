package com.revature.factories;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract class that denotes what needs to be implemented for a session factory. In theory, could be multiple
 * different types of sessions, not just to postgresql
 */
public interface SessionFactory {

    //abstract SessionFactory(Configuration con);
    //abstract SessionFactory getInstance();
     public Connection getConnection() throws SQLException;
}
