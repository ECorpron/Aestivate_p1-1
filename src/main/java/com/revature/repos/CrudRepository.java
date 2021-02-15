package com.revature.repos;

import com.revature.model.SQLConstraints;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    void save(T newObj);

    /**
     * A findAll methods that returns a LinkedList of all items in the database
     * @return a linkedList of all entries in the database
     */
    //LinkedList<T> findAll();

    /**
     * Finds an entry by its id
     * @param id the id to search an entry for
     * @return returns the object with the corresponding id
     */
    T findById(int id);

    /**
     * Updates the given object in the database.
     * @param updatedObj The updated object
     * @return returns true if changed, else returns false
     */
    boolean update(T updatedObj);

    /**
     * Deletes an entry from the database based on the id
     * @param id the id of the entry to delete
     * @return returns true if deleted, else returns false
     */
    boolean deleteById(int id);
}

