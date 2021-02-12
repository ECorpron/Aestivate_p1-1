package com.revature.util;

public class Configuration {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public Configuration(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbPassword = dbPassword;
        this.dbUsername = dbUsername;
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
}
