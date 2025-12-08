package com.comp2042.model.scoreBoard;

/**
 * Defines the abstraction for saving high scores.
 */
public interface HighScoreSaver {
    /**
     * Saves a new high score entry, processing it according to the persistence rules.
     *
     * @param entry The new {@code HighScoreEntry} to be saved.
     */
    void saveScore(HighScoreEntry entry);
}