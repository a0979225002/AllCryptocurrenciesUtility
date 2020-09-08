package com.lipin.Utils;

import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * 改寫SimpleObjectProperty
 * 讓他可以放入BigDecimal屬性的值
 */
public class SimpleBigDecimalProperty extends SimpleObjectProperty {

    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;

    @Override
    public Object getBean() {
        return super.getBean();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public SimpleBigDecimalProperty (){
        this(DEFAULT_BEAN,DEFAULT_NAME);
    }
    public SimpleBigDecimalProperty (BigDecimal initValue){
        this(DEFAULT_BEAN,DEFAULT_NAME,initValue);
    }
    public SimpleBigDecimalProperty (Object bean,String name){
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
    public SimpleBigDecimalProperty (Object bean,String name,BigDecimal initValue){
        super(initValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
}
