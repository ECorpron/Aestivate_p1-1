package com.revature.model;

import java.sql.Connection;
import java.util.Map;

/**
 * Inheritance/Extension base model
 * @param <T>
 */
public class TableModel<T> {

    //String[] columns;
    String tableName;
    Map<String, SQLConstraints> columns;

    public TableModel(Map<String, SQLConstraints> columns, String tableName) {
        this.tableName = tableName;
        this.columns = columns;
    }

//    public boolean createTable(Connection conn) {
//
//    }

}
