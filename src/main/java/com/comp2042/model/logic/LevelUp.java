package com.comp2042.model.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages the game level, which increases for every 10 lines removed, up to a maximum of Level 5.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**State Manager**: Maintains the current level and the lines needed for the next level.</li>
 * <li>**Observable/Listener Pattern (via JavaFX Property)**: Exposes the level state as an {@code IntegerProperty} for GUI binding.</li>
 * </ul>
 */
public final class LevelUp {

    private final IntegerProperty level = new SimpleIntegerProperty(1);
    private int linesToNextLevel = 10;
    private static final int LINES_PER_LEVEL = 10;
    private static final int INITIAL_LINES_TO_NEXT_LEVEL = 10;

    /**
     * Returns the observable property for the current game level.
     *
     * @return The {@code IntegerProperty} containing the current level.
     */
    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * Gets the current game level as a primitive integer.
     * @return The current level.
     */
    public int getLevel() {
        return level.get();
    }

    /**
     * Checks if the game needs to advance a level based on the number of lines recently removed.
     * Advancement stops once Level 5 is reached.
     *
     * @param linesRemoved The number of lines cleared in the last operation.
     */
    public void checkAndAdvance(int linesRemoved) {
        if (getLevel() >= 5) {
            return;
        }

        linesToNextLevel -= linesRemoved;
        if (linesToNextLevel <= 0) {
            level.set(level.get() + 1);
            // Use the remaining cleared lines for the next level calculation
            linesToNextLevel += LINES_PER_LEVEL;
        }
    }

    /**
     * Resets the game level to 1 and resets the lines required to advance to the next level.
     */
    public void reset() {
        level.set(1);
        linesToNextLevel = INITIAL_LINES_TO_NEXT_LEVEL;
    }
}