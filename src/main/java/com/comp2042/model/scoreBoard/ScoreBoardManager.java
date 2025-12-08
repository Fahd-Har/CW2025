package com.comp2042.model.scoreBoard;

import com.comp2042.model.logic.GameMode;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the persistence of high scores by handling file I/O, sorting, and score filtering.
 * It stores high scores separately for each {@code GameMode}.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Singleton Pattern (Implied)**: This class manages a singular, application-wide resource (the high score file), making it function as a resource manager.</li>
 * </ul>
 */
public class ScoreBoardManager implements HighScoreSaver{

    private static final String HIGH_SCORE_FILE = "highScores.dat";
    private static final int SHOW_MAX_HIGH_SCORES = 5;
    private static final Logger LOGGER = Logger.getLogger(ScoreBoardManager.class.getName());

    /**
     * Saves a new high score to the persistence file (`highScores.dat`).
     *
     * <p>The process involves: loading all existing scores, adding the new score to the
     * list for its specific game mode, sorting the list in descending order, trimming
     * the list to the top 5 scores, and writing the entire score map back to the file.</p>
     *
     * @param newEntry The {@code HighScoreEntry} to save.
     * @see HighScoreSaver#saveScore(HighScoreEntry)
     */
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

    /**
     * Attempts to deserialize the map of all high scores (grouped by {@code GameMode})
     * from the persistence file.
     *
     * <p>Handles cases where the file does not exist, is empty, or encounters I/O errors
     * during deserialization.</p>
     *
     * @return A map containing all high score lists, keyed by {@code GameMode}. Returns an
     * empty map on failure or if the file is empty.
     */
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

    /**
     * Loads and returns the filtered list of high scores specifically for the given game mode.
     *
     * @param mode The {@code GameMode} to retrieve scores for.
     * @return A {@code List} of the top 5 {@code HighScoreEntry} objects for that mode, or an empty list if none exist.
     */
    public List<HighScoreEntry> loadScores(GameMode mode) {
        Map<GameMode, List<HighScoreEntry>> allScores = loadAllScores();
        return allScores.getOrDefault(mode, new ArrayList<>());
    }
}