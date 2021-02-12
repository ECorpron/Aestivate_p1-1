package com.revature.model;

import java.util.Map;

/**
 * Inheritance/Extension base model
 * @param <T>
 */
public abstract class TableModel<T> {

    //String[] columns;
    String tableName;
    Map<String, SQLConstraints> columns;

    public TableModel(Map<String, SQLConstraints> columns, String tableName) {
        this.columns = columns;
        this.tableName = tableName;
    }

}
