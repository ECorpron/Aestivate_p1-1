package com.revature.model;

public enum SQLConstraints {
    PRIMARY_KEY, FOREIGN_KEY, NOT_NULL, UNIQUE, CHECK, DEFAULT, INDEX;

    public String defaultValue = "";
}
