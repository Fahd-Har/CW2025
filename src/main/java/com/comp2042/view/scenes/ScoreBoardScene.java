package com.comp2042.view.scenes;

import com.comp2042.model.logic.GameMode;
import com.comp2042.model.scoreBoard.HighScoreEntry;
import com.comp2042.model.scoreBoard.ScoreBoardManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Scoreboard scene ({@code scoreBoard.fxml}).
 * Manages the display of high scores, allowing the user to toggle between scores for
 * Normal, Hard, and Extreme modes.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**View Controller (MVC)**: Handles mode selection events and dynamically updates the
 * {@code GridPane} content based on data retrieved from the {@code ScoreBoardManager}.</li>
 * </ul>
 */
public class ScoreBoardScene {

    @FXML private GridPane scoreList;
    @FXML private Button homeButton;
    @FXML private Button normalButton, hardButton, extremeButton;
    private final ScoreBoardManager scoreboardManager = new ScoreBoardManager();
    private GameMode selectedMode = GameMode.NORMAL_MODE;

    /**
     * Initializes the controller, setting the initial display mode to Normal and loading
     * the corresponding high scores.
     */
    @FXML
    public void initialize() {
        updateButtonStyles(normalButton);
        loadHighScores(selectedMode);
    }

    // Mode selection handlers

    /**
     * Switches the scoreboard to display high scores for Normal Mode.
     */
    @FXML
    private void selectNormalMode() {
        selectedMode = GameMode.NORMAL_MODE;
        updateButtonStyles(normalButton);
        loadHighScores(selectedMode);
    }

    /**
     * Switches the scoreboard to display high scores for Hard Mode.
     */
    @FXML
    private void selectHardMode() {
        selectedMode = GameMode.HARD_MODE;
        updateButtonStyles(hardButton);
        loadHighScores(selectedMode);
    }

    /**
     * Switches the scoreboard to display high scores for Extreme Mode.
     */
    @FXML
    private void selectExtremeMode() {
        selectedMode = GameMode.EXTREME_MODE;
        updateButtonStyles(extremeButton);
        loadHighScores(selectedMode);
    }

    /**
     * Manages the visual state of the mode selection buttons, applying the 'selected'
     * CSS style only to the currently chosen button.
     *
     * @param selectedButton The button corresponding to the currently selected mode.
     */
    private void updateButtonStyles(Button selectedButton) {
        normalButton.getStyleClass().remove("selected");
        hardButton.getStyleClass().remove("selected");
        extremeButton.getStyleClass().remove("selected");

        selectedButton.getStyleClass().add("selected");
    }

    /**
     * Fetches high scores for the specified mode from {@code ScoreBoardManager} and
     * dynamically populates the {@code scoreList} {@code GridPane}.
     *
     * @param gameMode The mode whose top scores should be displayed.
     */
    private void loadHighScores(GameMode gameMode) {
        List<HighScoreEntry> highScores = scoreboardManager.loadScores(gameMode);

        // Removes gridPane children from row 2 and above, so to retain the style for the header
        scoreList.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex >= 2;
        });

        if (highScores.isEmpty()) {
            Label noScores = new Label("No scores saved yet!");
            noScores.getStyleClass().add("scoreboard-entry");
            scoreList.add(noScores, 0, 2, 5, 1); // Add to row 2, spanning 5 columns
            return;
        }

        for (int i = 0; i < highScores.size(); i++) {
            HighScoreEntry entry = highScores.get(i);
            int entryRow = i + 2;

            Label rankLabel = new Label(String.valueOf(i + 1));
            rankLabel.getStyleClass().add("scoreboard-entry");
            scoreList.add(rankLabel, 0, entryRow);

            Label scoreLabel = new Label(String.valueOf(entry.getScore()));
            scoreLabel.getStyleClass().add("scoreboard-entry");
            scoreList.add(scoreLabel, 1, entryRow);

            Label levelLabel = new Label(String.valueOf(entry.getLevel()));
            levelLabel.getStyleClass().add("scoreboard-entry");
            scoreList.add(levelLabel, 2, entryRow);

            Label linesLabel = new Label(String.valueOf(entry.getLines()));
            linesLabel.getStyleClass().add("scoreboard-entry");
            scoreList.add(linesLabel, 3, entryRow);

            Label timeLabel = new Label(entry.getTime());
            timeLabel.getStyleClass().add("scoreboard-entry");
            scoreList.add(timeLabel, 4, entryRow);
        }
    }

    /**
     * Handles the action to return to the Main Menu scene.
     *
     * @throws IOException If the FXML for the main menu cannot be loaded.
     */
    @FXML
    private void loadHome() throws IOException {
        SceneSwitch.loadMainMenu(homeButton);
    }
}