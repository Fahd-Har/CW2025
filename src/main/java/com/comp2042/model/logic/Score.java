package com.comp2042.model.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages and tracks the player's current score in the game.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**State Manager**: Maintains and controls the value of the game score.</li>
 * <li>**Observable/Listener Pattern (via JavaFX Property)**: Uses an {@code IntegerProperty} to allow the GUI (View)
 * to bind directly to the score value for automatic, real-time updates.</li>
 * </ul>
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Returns the observable property for the current score.
     *
     * @return The {@code IntegerProperty} containing the score.
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds a specified integer amount to the current score.
     *
     * @param i The value to add to the score.
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score.setValue(0);
    }
}