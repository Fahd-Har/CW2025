package com.comp2042.model.scoreBoard;
import com.comp2042.model.logic.GameMode;

import java.io.Serial;
import java.io.Serializable;

/**
 * A data class that stores the permanent statistics of a single game session.
 *
 * <p>This class is {@code Serializable} to allow records to be written to and read from a
 * file (`highScores.dat`).</p>
 *
 */
public class HighScoreEntry implements Serializable, Comparable<HighScoreEntry> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int score;
    private final int level;
    private final int lines;
    private final String time;
    private final GameMode gameMode;

    /**
     * Constructs a HighScoreEntry with the final game details.
     *
     * @param score The final score achieved.
     * @param level The final level reached.
     * @param lines The total number of lines cleared.
     * @param time The elapsed game time in "MM:SS" format.
     * @param gameMode The difficulty mode under which the score was achieved.
     */
    public HighScoreEntry(int score, int level, int lines, String time, GameMode gameMode) {
        this.score = score;
        this.level = level;
        this.lines = lines;
        this.time = time;
        this.gameMode = gameMode;
    }

    /**
     * Gets the score.
     * @return The final score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the level.
     * @return The final level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the total lines cleared.
     * @return The total lines cleared.
     */
    public int getLines() {
        return lines;
    }

    /**
     * Gets the elapsed time.
     * @return The elapsed time string ("MM:SS").
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets the game mode.
     * @return The {@code GameMode} for this score.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Compares this entry with another based on score for sorting.
     * Scores are sorted in **descending** order (higher score first).
     *
     * @param other The other entry to compare against.
     * @return A negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(HighScoreEntry other) {
        // Sort in descending order by score
        return Integer.compare(other.score, this.score);
    }
}