package com.revature.repos;

import com.revature.util.ColumnField;
import com.revature.util.SessionManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericClassRepository<T> implements CrudRepository<T> {

    Class<T> tClass;
    String classTableName;
    String select = "SELECT ? "+
                    "FROM ?";

    public GenericClassRepository(Class<T> tClass) {
        this.tClass = tClass;
        classTableName = replacePeriods(new StringBuilder(tClass.getName())).toString();
    }

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
        //System.out.println(builder.toString());

        Connection conn = SessionManager.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(builder.toString());

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
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, "*");
            pstmt.setString(2, classTableName);
            //System.out.println(pstmt.toString());

            return pstmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void dropClassTableAlways(){
        Connection conn = SessionManager.getConnection();

        String sql = "DROP TABLE IF EXISTS "+classTableName;
        System.out.println(sql);

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    private StringBuilder replacePeriods(StringBuilder builder) {
        int index = builder.indexOf(".");

        while(index != -1) {
            builder.replace(index, index+1, "_");
            index = builder.indexOf(".");
        }

        return builder;
    }
}
