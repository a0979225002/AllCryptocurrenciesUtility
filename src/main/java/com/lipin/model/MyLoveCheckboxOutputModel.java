package com.lipin.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyLoveCheckboxOutputModel {
    private SimpleObjectProperty booleanProperty = new SimpleObjectProperty();
    private StringProperty name = new SimpleStringProperty();


    public MyLoveCheckboxOutputModel(){

    }

    public MyLoveCheckboxOutputModel(Object myLove, String name) {
        this.booleanProperty = new SimpleObjectProperty(myLove);
        this.name = new SimpleStringProperty(name);
    }

    public Object getBooleanProperty() {
        return booleanProperty.get();
    }

    public SimpleObjectProperty booleanPropertyProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(Object booleanProperty) {
        this.booleanProperty.set(booleanProperty);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
