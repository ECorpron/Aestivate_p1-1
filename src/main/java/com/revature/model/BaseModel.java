package com.revature.model;

import com.revature.repos.GenericClassRepository;
import com.revature.services.ClassService;
import com.revature.util.ColumnField;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Inheritance based model for an ORM
 * @param <T> the type of the class
 */
public abstract class BaseModel<T> {

    public static ColumnField[] columns;
    private ClassService<T> service;

    @SuppressWarnings("unchecked")
    public BaseModel() {
        Class<T> tClass = (Class<T>) this.getClass();
        service = new ClassService<T>(new GenericClassRepository<T>(tClass), tClass);
        columns = setColumns();
    }

    protected abstract ColumnField[] setColumns();

    /**
     * Returns true if the table is created, false if the table already exists.
     */
    public void createTableIfNonexistant() throws SQLException, NoSuchFieldException {
        service.createClassTableIfDoesNotExist();
    }

    public void createTable() throws SQLException, NoSuchFieldException {
        service.dropThenCreateClassTable();
    }

//    public boolean createTable(Connection conn) {
//
//    }

}
