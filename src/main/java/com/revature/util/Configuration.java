package com.revature.util;

public class Configuration {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int minIdle;
    private int maxIdle;
    private int maxOpenPreparedStatements;

    public Configuration(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.minIdle = 1;
        this.maxIdle = 5;
        this.maxOpenPreparedStatements = 100;
    }


    public Configuration(String dbUrl, String dbUsername, String dbPassword, int minIdle, int maxIdle, int maxOpenPreparedStatements) {
        this(dbUrl, dbUsername, dbPassword);

        this.minIdle = minIdle;
        this.maxIdle = maxIdle;
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }
}
