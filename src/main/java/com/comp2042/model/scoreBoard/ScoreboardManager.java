package com.comp2042.model.scoreBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreboardManager {

    private static final String HIGH_SCORE_FILE = "highScores.dat";
    private static final int SHOW_MAX_HIGH_SCORES = 5;
    private static final Logger LOGGER = Logger.getLogger(ScoreboardManager.class.getName());

    public void saveScore(HighScoreEntry newEntry) {
        List<HighScoreEntry> highScores = loadScores();
        highScores.add(newEntry);

        // Sort in descending order by score
        Collections.sort(highScores);

        // Keep only the top 5 high scores
        if (highScores.size() > SHOW_MAX_HIGH_SCORES) {
            highScores = highScores.subList(0, SHOW_MAX_HIGH_SCORES);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Could not save highScores.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<HighScoreEntry> loadScores() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> rawList) {
                if (!rawList.isEmpty() && rawList.get(0) instanceof HighScoreEntry) {
                    return (List<HighScoreEntry>) obj;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "HighScores file not found: " + HIGH_SCORE_FILE);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading highScores. Returning empty list.", e);
        }
        return new ArrayList<>();
    }
}
