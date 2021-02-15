package com.revature;

import com.revature.model.BaseModel;
import com.revature.model.SQLConstraints;
import com.revature.util.ColumnField;

import java.util.Map;

public class TestClass extends BaseModel<TestClass> {


    protected ColumnField[] setColumns() {
        ColumnField[] columns = new ColumnField[2];
        ColumnField column1 = new ColumnField("id", "serial", SQLConstraints.PRIMARY_KEY);
        ColumnField column2 = new ColumnField("name", "varchar", SQLConstraints.NOT_NULL);
        columns[0] = column1;
        columns[1] = column2;
        return columns;
    }
}
