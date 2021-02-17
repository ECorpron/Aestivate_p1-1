package com.revature.services;

import com.revature.model.BaseModel;
import com.revature.repos.GenericClassRepository;

public class ClassService<T> {

    GenericClassRepository<T> repo;
    Class<T> tClass;

    public ClassService(GenericClassRepository<T> repo, Class<T> tClass) {
        this.repo = repo;
        this.tClass = tClass;
    }

    public boolean createClassTableIfDoesNotExist(){
        return repo.createClassTable();
    }

    public void dropThenCreateClassTable(){
        repo.dropClassTableAlways();
        repo.createClassTable();
    }

    public void save(BaseModel<T> save) {
        repo.saveNewToClassTable(save);
    }
}
