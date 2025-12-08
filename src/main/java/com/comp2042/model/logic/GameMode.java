package com.comp2042.model.logic;

/**
 * An enumeration defining the three supported difficulty modes for the game.
 * Each mode carries boolean flags that determine how the game flow is modified.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Strategy Pattern**: The boolean flags (`affectsFallingSpeed`, `affectsRisingRows`)
 * act as simplified strategies, allowing the {@code GameController} and {@code GameFlowManager} to conditionally
 * execute difficulty-specific logic based on the selected mode.</li>
 * </ul>
 */
public enum GameMode {
    /**
     * Normal difficulty: Falling speed increases by level, no rising rows.
     */
    NORMAL_MODE(true, false),
    /**
     * Hard difficulty: Fixed falling speed, but includes periodic rising rows that increase in frequency.
     */
    HARD_MODE(false, true),
    /**
     * Extreme difficulty: Combines increasing falling speed and periodic rising rows.
     */
    EXTREME_MODE(true, true);

    private final boolean affectsFallingSpeed;
    private final boolean affectsRisingRows;

    /**
     * Constructs a GameMode with flags defining its characteristics.
     *
     * @param affectsFallingSpeed If true, the brick drop rate speeds up with level increases.
     * @param affectsRisingRows If true, garbage rows will be periodically added from the bottom.
     */
    GameMode(boolean affectsFallingSpeed, boolean affectsRisingRows) {
        this.affectsFallingSpeed = affectsFallingSpeed;
        this.affectsRisingRows = affectsRisingRows;
    }

    /**
     * Checks if this game mode enables increasing brick falling speed based on level.
     *
     * @return true if falling speed is level-dependent, false otherwise.
     */
    public boolean affectsFallingSpeed() {
        return affectsFallingSpeed;
    }

    /**
     * Checks if this game mode enables periodic rising rows from the bottom.
     *
     * @return true if rising rows are active, false otherwise.
     */
    public boolean affectsRisingRows() {
        return affectsRisingRows;
    }
}