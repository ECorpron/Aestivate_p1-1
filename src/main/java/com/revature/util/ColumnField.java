package com.revature.util;

import com.revature.model.SQLConstraints;

import java.util.Objects;

public class ColumnField {

    private String columnName;
    private String columnType;
    private SQLConstraints constraint;

    public ColumnField(String columnName, String columnType, SQLConstraints constraint) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.constraint = constraint;
    }

    public String getRowAsString() {
        String line = columnName+" "+columnType;
        if (constraint != null) {
            line = line+" "+ SQLConstraints.stringReprestation(constraint)+",";
        } else {
            line += ",";
        }
        return line;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnField setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnType() {
        return columnType;
    }

    public ColumnField setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public SQLConstraints getConstraint() {
        return constraint;
    }

    public ColumnField setConstraint(SQLConstraints constraint) {
        this.constraint = constraint;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnField that = (ColumnField) o;
        return Objects.equals(columnName, that.columnName) && Objects.equals(columnType, that.columnType) && constraint == that.constraint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, columnType, constraint);
    }

    @Override
    public String toString() {
        return "ColumnField{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", constraint=" + constraint +
                '}';
    }
}
