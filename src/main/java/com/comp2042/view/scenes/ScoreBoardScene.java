package com.comp2042.view.scenes;

import com.comp2042.model.scoreBoard.HighScoreEntry;
import com.comp2042.model.scoreBoard.ScoreboardManager;
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
    private final ScoreboardManager scoreboardManager = new ScoreboardManager();

    @FXML
    public void initialize() {
        loadHighScores();
    }

    private void loadHighScores() {
        List<HighScoreEntry> highScores = scoreboardManager.loadScores();

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
    private void handleHome() throws IOException {
        URL location = getClass().getClassLoader().getResource("mainMenuScreen.fxml");
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