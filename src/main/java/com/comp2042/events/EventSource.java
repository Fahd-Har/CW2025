package com.comp2042.events;

/**
 * An enumeration defining the source or originator of a game event.
 * This is used to differentiate between actions initiated by the player
 * and those initiated automatically by the game system (e.g., the game loop).
 */
public enum EventSource {
    /**
     * The event was initiated directly by the player (e.g., a key press for movement).
     */
    USER,
    /**
     * The event was initiated by the game logic thread (e.g., the periodic dropping brick).
     */
    THREAD
}