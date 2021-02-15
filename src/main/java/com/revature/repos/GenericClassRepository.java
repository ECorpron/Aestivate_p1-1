package com.revature.repos;

import com.revature.model.SQLConstraints;
import com.revature.util.ColumnField;
import com.revature.util.SessionManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GenericClassRepository<T> implements CrudRepository<T> {

    String select = "SELECT ? "+
                    "FROM ?";

    String drop = "DROP TABLE ?";

    //String makeTable = "CREATE TABLE "++"" (\n";



    @Override
    public void createTable(Class<T> tClass) throws NoSuchFieldException, SQLException {

        Field field = tClass.getField("columns");
        ColumnField[] columns = null;

        try {
            columns = (ColumnField[]) field.get(null);
        } catch (IllegalAccessException e) {
            System.out.println("Illegal Access exception to the columns field in the class");
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder("CREATE TABLE "+tClass.getName()+" (\n");

        int index = builder.indexOf(".");

        while(index != -1) {
            builder.replace(index, index+1, "_");
            index = builder.indexOf(".");
        }


        assert columns != null;
        for (ColumnField column : columns) {
            String line = column.getRowAsString()+"\n";
            builder.append(line);
        }

        builder.deleteCharAt(builder.lastIndexOf(","));

        builder.append(");");
        System.out.println(builder.toString());

        Connection conn = SessionManager.getConnection();
        //System.out.println(conn.isClosed());
        PreparedStatement pstmt = conn.prepareStatement(builder.toString());
        //pstmt.setString(1, tClass.getName());

        pstmt.execute();
    }

    @Override
    public ResultSet getAll(String tableName) throws SQLException {

        StringBuilder builder = new StringBuilder(tableName);

        Connection conn = SessionManager.getConnection();

        PreparedStatement pstmt = conn.prepareStatement(select);
        pstmt.setString(1, "*");
        pstmt.setString(2, tableName);

        return pstmt.executeQuery();
    }

    public void dropTable(String tableName) throws SQLException {

        StringBuilder builder = new StringBuilder(tableName);
        builder = replacePeriods(builder);

        Connection conn = SessionManager.getConnection();

        PreparedStatement pstmt = conn.prepareStatement(drop);
        pstmt.setString(1, builder.toString());
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
