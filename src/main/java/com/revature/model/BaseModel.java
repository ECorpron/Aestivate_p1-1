package com.revature.model;

import com.revature.annotations.Column;
import com.revature.util.ColumnField;

import java.util.List;

/**
 * Annotation base class
 * @param <T>
 */
public class BaseModel<T> {

    private Class<T> tClass;

    public static <T> BaseModel<T> of(Class<T> tClass) {
        return new BaseModel<>(tClass);
    }

    public BaseModel(Class<T> tClass) {
        this.tClass = tClass;
    }

    //public List<ColumnField>
}
