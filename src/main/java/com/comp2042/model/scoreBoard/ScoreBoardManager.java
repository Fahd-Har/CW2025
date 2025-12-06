package com.comp2042.model.scoreBoard;

import com.comp2042.model.logic.GameMode;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreBoardManager implements HighScoreSaver{

    private static final String HIGH_SCORE_FILE = "highScores.dat";
    private static final int SHOW_MAX_HIGH_SCORES = 5;
    private static final Logger LOGGER = Logger.getLogger(ScoreBoardManager.class.getName());

    @Override
    public void saveScore(HighScoreEntry newEntry) {
        // Load all scores, grouped by mode
        Map<GameMode, List<HighScoreEntry>> allScores = loadAllScores();

        // Get the list for the current mode, or create a new one if there isn't any
        GameMode mode = newEntry.getGameMode();
        List<HighScoreEntry> highScoresForMode = allScores.getOrDefault(mode, new ArrayList<>());

        highScoresForMode.add(newEntry);

        // Sort in descending order by score
        Collections.sort(highScoresForMode);

        // Keep only the top 5 high scores
        if (highScoresForMode.size() > SHOW_MAX_HIGH_SCORES) {
            highScoresForMode = highScoresForMode.subList(0, SHOW_MAX_HIGH_SCORES);
        }

        allScores.put(mode, highScoresForMode);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(allScores);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Could not save highScores.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<GameMode, List<HighScoreEntry>> loadAllScores() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                return (Map<GameMode, List<HighScoreEntry>>) obj;
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "HighScores file not found: " + HIGH_SCORE_FILE);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading highScores. Returning empty list.", e);
        }
        return new HashMap<>();
    }

    // Public method to load scores for a specific game mode.
    public List<HighScoreEntry> loadScores(GameMode mode) {
        Map<GameMode, List<HighScoreEntry>> allScores = loadAllScores();
        return allScores.getOrDefault(mode, new ArrayList<>());
    }
}
