package com.comp2042.model.logic;

public enum GameMode {
    NORMAL_MODE(true, false),
    HARD_MODE(false, true),
    EXTREME_MODE(true, true);

    private final boolean affectsFallingSpeed;
    private final boolean affectsRisingRows;

    GameMode(boolean affectsFallingSpeed, boolean affectsRisingRows) {
        this.affectsFallingSpeed = affectsFallingSpeed;
        this.affectsRisingRows = affectsRisingRows;
    }

    public boolean affectsFallingSpeed() {
        return affectsFallingSpeed;
    }

    public boolean affectsRisingRows() {
        return affectsRisingRows;
    }
}