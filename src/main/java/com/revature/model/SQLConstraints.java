package com.revature.model;

public enum SQLConstraints {
    PRIMARY_KEY, FOREIGN_KEY, NOT_NULL, UNIQUE, CHECK, DEFAULT, INDEX;

    //public String defaultValue = "";

    public static String stringReprestation(SQLConstraints con) {
        switch (con) {
            case PRIMARY_KEY:
                return "PRIMARY KEY";
            case FOREIGN_KEY:
                return "FOREIGN KEY";
            case NOT_NULL:
                return "NOT NULL";
            case UNIQUE:
                return "UNIQUE";
            case CHECK:
                return "CHECK";
            case DEFAULT:
                return "DEFAULT";
            case INDEX:
                return "INDEX";
        }
        return null;
    }
}
