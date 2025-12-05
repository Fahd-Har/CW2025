package com.comp2042.model.scoreBoard;
import com.comp2042.model.logic.GameMode;

import java.io.Serial;
import java.io.Serializable;

public class HighScoreEntry implements Serializable, Comparable<HighScoreEntry> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int score;
    private final int level;
    private final int lines;
    private final String time;
    private final GameMode gameMode;

    public HighScoreEntry(int score, int level, int lines, String time, GameMode gameMode) {
        this.score = score;
        this.level = level;
        this.lines = lines;
        this.time = time;
        this.gameMode = gameMode;
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

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        // Sort in descending order by score
        return Integer.compare(other.score, this.score);
    }
}
