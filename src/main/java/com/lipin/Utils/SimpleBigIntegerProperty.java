package com.lipin.Utils;

import javafx.beans.property.SimpleObjectProperty;

import java.math.BigInteger;

public class SimpleBigIntegerProperty extends SimpleObjectProperty {
    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The constructor of {@code BigIntegerProperty}
     */
    public SimpleBigIntegerProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }
    /**
     * The constructor of {@code BigIntegerProperty}
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public SimpleBigIntegerProperty(BigInteger initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    /**
     * The constructor of {@code BigIntegerProperty}
     *
     * @param bean
     *            the bean of this {@code BigIntegerProperty}
     * @param name
     *            the name of this {@code BigIntegerProperty}
     */
    public SimpleBigIntegerProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    /**
     * The constructor of {@code BigIntegerProperty}
     *
     * @param bean
     *            the bean of this {@code BigIntegerProperty}
     * @param name
     *            the name of this {@code BigIntegerProperty}
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public SimpleBigIntegerProperty(Object bean, String name, BigInteger initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
}
