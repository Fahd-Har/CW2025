package com.comp2042.view.scenes;

import com.comp2042.model.logic.GameMode;
import com.comp2042.model.scoreBoard.HighScoreEntry;
import com.comp2042.model.scoreBoard.ScoreBoardManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ScoreBoardScene {

    @FXML private GridPane scoreList;
    @FXML private Button homeButton;
    @FXML private Button normalButton, hardButton, extremeButton;
    private final ScoreBoardManager scoreboardManager = new ScoreBoardManager();
    private GameMode selectedMode = GameMode.NORMAL_MODE;

    @FXML
    public void initialize() {
        updateButtonStyles(normalButton);
        loadHighScores(selectedMode);
    }

    // Mode selection handlers
    @FXML
    private void selectNormalMode() {
        selectedMode = GameMode.NORMAL_MODE;
        updateButtonStyles(normalButton);
        loadHighScores(selectedMode);
    }

    @FXML
    private void selectHardMode() {
        selectedMode = GameMode.HARD_MODE;
        updateButtonStyles(hardButton);
        loadHighScores(selectedMode);
    }

    @FXML
    private void selectExtremeMode() {
        selectedMode = GameMode.EXTREME_MODE;
        updateButtonStyles(extremeButton);
        loadHighScores(selectedMode);
    }

    private void updateButtonStyles(Button selectedButton) {
        normalButton.getStyleClass().remove("selected");
        hardButton.getStyleClass().remove("selected");
        extremeButton.getStyleClass().remove("selected");

        selectedButton.getStyleClass().add("selected");
    }

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

    @FXML
    private void loadHome() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/mainMenuScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent menuRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), menuRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) homeButton.getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(menuRoot);
        stage.setTitle("TetrisJFX");
        fadeIn.play();
    }
}