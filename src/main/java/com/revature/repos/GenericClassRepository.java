package com.revature.repos;

import com.revature.model.BaseModel;
import com.revature.util.ColumnField;
import com.revature.util.SessionManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A repository that can run CRUD methods for any class that extends the BaseModel.
 * @param <T> The class that is using the Repository
 */
public class GenericClassRepository<T> implements CrudRepository<T> {

    Class<T> tClass;
    String classTableName;
    String select = "SELECT ? "+
                    "FROM ?";

    public GenericClassRepository(Class<T> tClass) {
        this.tClass = tClass;
        classTableName = replacePeriods(new StringBuilder(tClass.getName())).toString();
    }

    /**
     * Creates the class table. Returns true if the table is successfully made, or false if the table already exists.
     * @return true if the table is made, false if the table already exists
     */
    @Override
    public boolean createClassTable() {

        Field field = null;
        try {
            field = tClass.getField("columns");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        ColumnField[] columns = null;

        try {
            assert field != null;
            columns = (ColumnField[]) field.get(null);
        } catch (IllegalAccessException e) {
            System.out.println("Illegal Access exception to the columns field in the class");
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder("CREATE TABLE "+classTableName+" (\n");

        assert columns != null;
        for (ColumnField column : columns) {
            String line = column.getRowAsString()+"\n";
            builder.append(line);
        }

        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(");");

        Connection conn = SessionManager.getConnection();

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(builder.toString());
            pstmt.execute();
        } catch (SQLException throwables) {
            System.out.println("Class already exists!");
            return false;
        }

        return true;
    }

    @Override
    public ResultSet getAll() {
        Connection conn = SessionManager.getConnection();

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, "*");
            pstmt.setString(2, classTableName);

            return pstmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * A method that always drops the class table, whether or not it exists.
     */
    public void dropClassTableAlways(){
        Connection conn = SessionManager.getConnection();

        String sql = "DROP TABLE IF EXISTS "+classTableName;
        System.out.println(sql);

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(BaseModel<T> newObj) {

    }

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public boolean update(BaseModel<T> updatedObj) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    private StringBuilder replacePeriods(StringBuilder builder) {
        int index = builder.indexOf(".");

        while(index != -1) {
            builder.replace(index, index+1, "_");
            index = builder.indexOf(".");
        }

        return builder;
    }
}
