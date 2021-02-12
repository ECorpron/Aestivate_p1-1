package com.revature.repos;

public class PgSQLRepository<T> implements CrudRepository<T> {

    @Override
    public void createTable(T newObj) {
        
    }

    @Override
    public void save(T newObj) {

    }

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public boolean update(T updatedObj) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
