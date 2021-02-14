package com.revature.util;

import java.util.Objects;

public class Database {
    private String sqlDatabase;
    private String url;
    private String loginName;
    private String password;
    private int minIdle;
    private int maxIdle;
    private int maxOpenPreparedStatements;

    public Database(){
        this.sqlDatabase = null;
        this.url = null;
        this.loginName = null;
        this.password = null;
//        minIdle = -1;
//        maxIdle = -1;
//        maxOpenPreparedStatements = -1;

    }

    public static boolean validate(Database database) {
        return ((database.getSqlDatabase() == null || database.getSqlDatabase().trim().equals("")) ||
                (database.getUrl() == null || database.getUrl().trim().equals("")) ||
                (database.getLoginName() == null || database.getLoginName().trim().equals("")) ||
                (database.getPassword() == null || database.getPassword().equals("")) ||
                (database.getMinIdle() == -1) || (database.getMaxIdle() == -1) ||
                (database.getMaxOpenPreparedStatements() == -1)
                );
    }

    public String getSqlDatabase() {
        return sqlDatabase;
    }

    public String getUrl() {
        return url;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setDatabase(String sqlDatabase) {
        this.sqlDatabase = sqlDatabase;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database database1 = (Database) o;
        return Objects.equals(sqlDatabase, database1.sqlDatabase) && Objects.equals(url, database1.url) && Objects.equals(loginName, database1.loginName) && Objects.equals(password, database1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqlDatabase, url, loginName);
    }

    @Override
    public String toString() {
        return "Database{" +
                "sqlDatabase='" + sqlDatabase + '\'' +
                ", url='" + url + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", minIdle=" + minIdle +
                ", maxIdle=" + maxIdle +
                ", maxOpenPreparedStatements=" + maxOpenPreparedStatements +
                '}';
    }
}
