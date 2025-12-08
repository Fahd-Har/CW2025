package com.comp2042.model.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages and tracks the total number of lines cleared by the player throughout a single game session.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Observable/Listener Pattern**: Uses an {@code IntegerProperty} to allow the GUI
 * (View) to bind directly to the line count for automatic, real-time updates.</li>
 * </ul>
 */
public class CountClearedRows {

    private final IntegerProperty countRows = new SimpleIntegerProperty(0);

    /**
     * Returns the observable property for the line count.
     *
     * @return The {@code IntegerProperty} containing the total lines cleared.
     */
    public IntegerProperty countRowsProperty() {
        return countRows;
    }

    /**
     * Adds a specified number of lines to the total count.
     *
     * @param i The number of lines cleared.
     */
    public void add(int i){
        countRows.setValue(countRows.getValue() + i);
    }

    /**
     * Resets the total lines cleared count to zero.
     */
    public void reset() {
        countRows.setValue(0);
    }

}