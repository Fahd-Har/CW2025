package com.comp2042.model.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CountClearedRows {

    private final IntegerProperty countRows = new SimpleIntegerProperty(0);

    public IntegerProperty countRowsProperty() {
        return countRows;
    }

    public void add(int i){
        countRows.setValue(countRows.getValue() + i);
    }

    public void reset() {
        countRows.setValue(0);
    }

}
