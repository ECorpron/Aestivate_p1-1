package com.revature.services;

import com.revature.model.BaseModel;
import com.revature.repos.GenericClassRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLSyntaxErrorException;

public class ClassService<T> {

    GenericClassRepository<T> repo;
    Class<T> tClass;

    public ClassService(GenericClassRepository<T> repo, Class<T> tClass) {
        this.repo = repo;
        this.tClass = tClass;
    }

    /**
     * Creates a class table if it does not already exist. Otherwise returns false.
     * @return returns true if a new table is made, false is one is not made.
     */
    public boolean createClassTableIfDoesNotExist(){
        return repo.createClassTable();
    }

    /**
     * Drops a table if one exists, then creates a class table.
     */
    public void dropThenCreateClassTable(){
        repo.dropClassTableAlways();
        repo.createClassTable();
    }

    /**
     * If the given base model doesn't have an entry in the class table, it adds it to the table. If the object already
     * has an entry, it updates it instead.
     * @param save the object instance to be saved.
     */
    public void save(T save) {
        Object pk = getPrimaryKey(save);

        try {
            if (repo.findByPrimaryKey(pk) == null) {
                System.out.println("The entry does not already exist, creating a new one.");
                repo.saveNewToClassTable(save);
            } else {
                System.out.println("Entry exists, updating it");
                repo.updateByPrimaryKey(save);
            }
        } catch (SQLSyntaxErrorException throwables) {
            throwables.printStackTrace();
            return;
        }
    }

    private Object getPrimaryKey(T instance) {
        Field pk = null;
        try {
            pk = repo.getPkField();
        } catch (NoSuchFieldException e) {
            System.out.println("Class is missing a column labeled as a primary key");
            e.printStackTrace();
        }

        if (Modifier.isPrivate(pk.getModifiers())) {
            pk.setAccessible(true);
        }

        try {
            return pk.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
