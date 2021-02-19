package com.revature;

import com.revature.model.BaseModel;
import com.revature.model.SQLConstraints;
import com.revature.util.ColumnField;

import java.util.Map;

public class TestClass extends BaseModel<TestClass> {

    private int id;
    private String name;

    public TestClass(){

    }

    public TestClass(int id, String name) {
        this.id = id;
        this.name = name;
    }


    protected ColumnField[] setColumns() {
        ColumnField[] columns = new ColumnField[2];
        ColumnField column1 = new ColumnField("id", "serial", SQLConstraints.PRIMARY_KEY);
        ColumnField column2 = new ColumnField("name", "varchar(25)", SQLConstraints.NOT_NULL);
        columns[0] = column1;
        columns[1] = column2;
        return columns;
    }

    public int getId() {
        return id;
    }

    public TestClass setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestClass setName(String name) {
        this.name = name;
        return this;
    }
}
