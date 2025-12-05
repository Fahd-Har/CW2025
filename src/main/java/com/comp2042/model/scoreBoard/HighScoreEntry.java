package com.comp2042.model.scoreBoard;
import java.io.Serial;
import java.io.Serializable;

public class HighScoreEntry implements Serializable, Comparable<HighScoreEntry> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int score;
    private final int level;
    private final int lines;
    private final String time;

    public HighScoreEntry(int score, int level, int lines, String time) {
        this.score = score;
        this.level = level;
        this.lines = lines;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getLines() {
        return lines;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        // Sort in descending order by score
        return Integer.compare(other.score, this.score);
    }
}
