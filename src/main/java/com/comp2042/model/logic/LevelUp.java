package com.comp2042.model.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class LevelUp {

    private final IntegerProperty level = new SimpleIntegerProperty(1);
    private int linesToNextLevel = 10;
    private static final int LINES_PER_LEVEL = 10;
    private static final int INITIAL_LINES_TO_NEXT_LEVEL = 10;

    public IntegerProperty levelProperty() {
        return level;
    }

    public int getLevel() {
        return level.get();
    }

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

    public void reset() {
        level.set(1);
        linesToNextLevel = INITIAL_LINES_TO_NEXT_LEVEL;
    }
}