package com.revature.factories;

import com.revature.util.Database;
import com.revature.util.XMLReader;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates a connection factory for postgresql, ends need to continually make new connections
 */
public class PostgreSQLSessionFactory implements SessionFactory {

    private static BasicDataSource ds = new BasicDataSource();
    private final String databaseName = "postgresql";

    // For the postgreSQL connection to exist, need the postresql driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PostgreSQLSessionFactory(Database db) {
        ds.setUrl(db.getUrl());
        ds.setUsername(db.getLoginName());
        ds.setPassword(db.getPassword());
        ds.setMinIdle(db.getMinIdle());
        ds.setMaxIdle(db.getMaxIdle());
        ds.setMaxOpenPreparedStatements(db.getMaxOpenPreparedStatements());
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }
}
