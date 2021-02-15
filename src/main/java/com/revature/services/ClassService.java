package com.revature.services;

import com.revature.model.SQLConstraints;
import com.revature.repos.GenericClassRepository;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Map;

public class ClassService<T> {

    GenericClassRepository<T> repo;
    Class<T> tClass;

    public ClassService(GenericClassRepository<T> repo, Class<T> tClass) {
        this.repo = repo;
        this.tClass = tClass;
    }

    public boolean createTableIfDoesNotExist() throws SQLException, NoSuchFieldException {
        if (doesTableExist()) return false;

        repo.createTable(tClass);
        return true;
    }

    public boolean dropThenCreateTable() throws SQLException, NoSuchFieldException {
        if (doesTableExist()) repo.dropTable(tClass.getName());

        repo.createTable(tClass);
        return true;
    }

    public boolean doesTableExist() {
        String tableName = tClass.getName();
        boolean exists = true;

        try {
            repo.getAll(tableName);
        } catch (SQLException throwables) {
            exists = false;
        }

        return exists;
    }
}
