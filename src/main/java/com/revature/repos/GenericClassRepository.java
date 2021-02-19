package com.revature.repos;

import com.revature.model.SQLConstraints;
import com.revature.util.ColumnField;
import com.revature.util.SessionManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        boolean created;

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(builder.toString());
            pstmt.execute();
        } catch (SQLException throwables) {
            System.out.println("Class already exists!");
            created = false;
        }

        created =  true;

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return created;
    }

    @Override
    public ResultSet getAll() {
        Connection conn = SessionManager.getConnection();
        ResultSet table = null;

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, "*");
            pstmt.setString(2, classTableName);

            table = pstmt.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return table;
    }

    /**
     * A method that always drops the class table, whether or not it exists.
     */
    public void dropClassTableAlways(){
        Connection conn = SessionManager.getConnection();

        String sql = "DROP TABLE IF EXISTS "+classTableName;
        //System.out.println(sql);

        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveNewToClassTable(T newObj) {
        String sql = getInsertString();

        try {
            Field field = tClass.getField("columns");
            ColumnField[] columns = (ColumnField[]) field.get(null);

            Connection conn = SessionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int count = 1;

            for (ColumnField column : columns) {
                String fieldName = column.getColumnName();

                Field fieldToStore = newObj.getClass().getDeclaredField(fieldName);

                if (Modifier.isPrivate(fieldToStore.getModifiers())) {
                    fieldToStore.setAccessible(true);
                }

                pstmt.setObject(count, fieldToStore.get(newObj));
                count++;
            }

            pstmt.execute();

            conn.close();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Missing a field");
        } catch (SQLException e) {
            System.out.println("Class is already saved");
        }
    }

    @Override
    public boolean updateByPrimaryKey(T updatedObj) {
        try {
            Field pk = getPkField();

            if (Modifier.isPrivate(pk.getModifiers())) {
                pk.setAccessible(true);
            }

            Object identifier = pk.get(updatedObj);

            if (findByPrimaryKey(identifier) == null) return false;

        } catch (NoSuchFieldException | IllegalAccessException | SQLSyntaxErrorException e) {
            e.printStackTrace();
        }

        Connection conn = SessionManager.getConnection();
        try {
            String sql = getUpdateString();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt = getPreparedUpdate(pstmt, updatedObj);
            pstmt.execute();

            conn.close();

            return true;

        } catch (SQLSyntaxErrorException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private String getUpdateString() throws SQLSyntaxErrorException {
        StringBuilder builder = new StringBuilder("Update "+classTableName+" SET ");
        StringBuilder qualifier = new StringBuilder("WHERE ");

        ColumnField[] columns = getColumns();

        for (ColumnField column: columns){

            String columnName = column.getColumnName();
            if (!isColumnNameSafe(columnName)) throw new SQLSyntaxErrorException("Column name contains invalid characters!");

            if (column.getConstraint() == SQLConstraints.PRIMARY_KEY) {
                qualifier.append(columnName).append(" = ?");
            } else {
                builder.append(columnName).append(" = ?, ");
            }
        }

        int index = builder.lastIndexOf(", ");
        builder.delete(index, index+2);
        builder.append(qualifier);

        return builder.toString();
    }

    private PreparedStatement getPreparedUpdate(PreparedStatement pstmt, T updatedObject) throws SQLException {
        ColumnField[] columns = getColumns();

        int count = 1;

        for (ColumnField column: columns) {

            Object insert = null;

            try {
                Field fieldToInsert = tClass.getDeclaredField(column.getColumnName());

                if (Modifier.isPrivate(fieldToInsert.getModifiers())) {
                    fieldToInsert.setAccessible(true);
                }

                insert = fieldToInsert.get(updatedObject);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (column.getConstraint() == SQLConstraints.PRIMARY_KEY) {
                pstmt.setObject(columns.length, insert);
            } else {
                pstmt.setObject(count, insert);
                count++;
            }
        }

        return pstmt;
    }

    private String getInsertString() {
        StringBuilder builder = new StringBuilder("INSERT INTO "+classTableName+"\n VALUES (");

        try {
            Field field = tClass.getField("columns");
            ColumnField[] columns = (ColumnField[]) field.get(null);

            for (ColumnField column : columns) {
                builder.append("?, ");
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int index = builder.lastIndexOf(", ");
        builder.delete(index, index+2);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public T findByPrimaryKey(Object primaryKey) throws SQLSyntaxErrorException {
        Field pk = null;
        try {
            pk = getPkField();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (!isColumnNameSafe(pk.getName())) throw new SQLSyntaxErrorException("Name contains invalid characters");

        String sql = "SELECT * FROM "+classTableName+" WHERE "+pk.getName()+" = ?";

        Connection conn = SessionManager.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, primaryKey);

            //System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();



            ArrayList<T> objects =  getTObjects(rs);

            conn.close();

            if (objects.size() > 0) {
                return objects.get(0);
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteByPrimaryKey(Object primaryKey) throws SQLSyntaxErrorException {
        Field pk = null;
        try {
            pk = getPkField();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (!isColumnNameSafe(pk.getName())) throw new SQLSyntaxErrorException("Name contains invalid characters");
        String sql = "DELETE FROM "+classTableName+" WHERE "+pk.getName()+" = ?";

        Connection conn = SessionManager.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, primaryKey);

            boolean executed = pstmt.execute();

            conn.close();

            return executed;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    /**
     * A convenience method that returns the primary key field of tClass
     * @return the field of the primary key
     * @throws NoSuchFieldException if there isn't a column labelled as a primary key
     */
    public Field getPkField() throws NoSuchFieldException {
        ColumnField[] columns = getColumns();

        for (ColumnField column : columns) {
            if (column.getConstraint().equals(SQLConstraints.PRIMARY_KEY)) {
                try {
                    return tClass.getDeclaredField(column.getColumnName());
                } catch (NoSuchFieldException e) {
                    System.out.println("Can't find primary key in column");
                    e.printStackTrace();
                    return null;
                }
            }
        }
        throw new NoSuchFieldException("This class does not have a Primary Key Constraint");
    }

    private ArrayList<T> getTObjects(ResultSet rs) throws SQLException {
        ColumnField[] columns = getColumns();

        ArrayList<T> objects = new ArrayList<>();

        while (rs.next()) {
            T t = null;

            try {
                try {
                    t = tClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            for (int i = 1; i <= columns.length; i++) {
                //System.out.println("On iteration: "+i);
                Field field = null;
                String columnName = null;

                try {
                    columnName = columns[i-1].getColumnName();

                    field = tClass.getDeclaredField(columnName);
                    //System.out.println("On field: "+columnName);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    return null;
                }

                if (Modifier.isPrivate(field.getModifiers())) {
                    field.setAccessible(true);
                }

                try {
                    field.set(t, rs.getObject(columnName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            objects.add(t);
        }

        return objects;
    }

    private boolean isColumnNameSafe(String name) {

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private ColumnField[] getColumns(){
        try {
            Field dbColumns = tClass.getField("columns");
            return (ColumnField[]) dbColumns.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            throw new NoSuchFieldException("Missing a column field");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
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
