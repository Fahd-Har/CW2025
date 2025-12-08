package com.comp2042.events;

/**
 * An enumeration defining the specific type of action or move requested in the game.
 * This is used within a {@code MoveEvent} to specify the intended effect on the game state.
 */
public enum EventType {
    /**
     * Move the current brick down one unit (soft drop).
     */
    DOWN,
    /**
     * Move the current brick left one unit.
     */
    LEFT,
    /**
     * Move the current brick right one unit.
     */
    RIGHT,
    /**
     * Rotate the current brick (typically counter-clockwise).
     */
    ROTATE,
    /**
     * Move the current brick instantly to the lowest possible position (hard drop or slam).
     */
    SLAM,
    /**
     * Swap the current falling brick with the piece in the hold queue.
     */
    HOLD
}