package com.revature.model;

import com.revature.repos.GenericClassRepository;
import com.revature.services.ClassService;
import com.revature.util.ColumnField;

import java.sql.SQLException;

/**
 * Inheritance based model for an ORM
 * @param <T> the type of the class
 */
public abstract class BaseModel<T> {

    public static ColumnField[] columns;
    private ClassService<T> service;
    //private T selfReference;

    @SuppressWarnings("unchecked")
    public BaseModel() {
        Class<T> tClass = (Class<T>) this.getClass();
        service = new ClassService<T>(new GenericClassRepository<T>(tClass), tClass);
        columns = setColumns();
        //selfReference = this;
    }

    protected abstract ColumnField[] setColumns();

    /**
     * Creates a table of the class only if the table doesn't already exists.
     */
    public void createTableIfNonexistant() throws SQLException, NoSuchFieldException {
        service.createClassTableIfDoesNotExist();
    }

    /**
     * Always creates a table with the class table name, dropping one with the same name if it exists.
     */
    public void createTable(){
        service.dropThenCreateClassTable();
    }

    /**
     * Creates a new row in the database if the object doesn't exist yet, or updates an already existing row if an
     * entry with the same primary key already exists.
     */
    public void save() {
        service.save((T) this);
    }

}
