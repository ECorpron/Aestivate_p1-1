package com.revature.repos;

import com.revature.model.BaseModel;
import com.revature.model.SQLConstraints;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Map;

/**
 * Interface for implementing a CrudRepository. Requires classes that implement it to implement a number of methods that
 * handle accessing the database.
 * @param <T> The element type that will be accessed in the database
 */
public interface CrudRepository<T> {

    /**
     * Creates a table of the specified object
     */
    boolean createClassTable() throws NoSuchFieldException, SQLException;

    ResultSet getAll() throws SQLException;

    /**
     * A save method that saves a given object to the database.
     * @param newObj the object to be saved
     */
    void saveNewToClassTable(T newObj);

    /**
     * A findAll methods that returns a LinkedList of all items in the database
     * @return a linkedList of all entries in the database
     */
    //LinkedList<T> findAll();

    /**
     * Finds an entry by its id
     * @param primaryKey the key to search an entry for
     * @return returns the object with the corresponding id
     */
    T findByPrimaryKey(Object primaryKey) throws NoSuchFieldException, SQLSyntaxErrorException;

    /**
     * Updates the given object in the database.
     * @param updatedObj The updated object
     * @return returns true if changed, else returns false
     */
    boolean updateByPrimaryKey(T updatedObj);

    /**
     * Deletes an entry from the database based on the id
     * @param id the id of the entry to delete
     * @return returns true if deleted, else returns false
     */
    boolean deleteByPrimaryKey(Object id) throws SQLSyntaxErrorException;
}

