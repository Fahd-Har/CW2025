package com.comp2042.view.scenes;

import javafx.fxml.FXML;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuScreen {
    @FXML private Button startButton;
    @FXML private Button scoreBoardButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(ae -> {
            try {
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        scoreBoardButton.setOnAction(ae -> {
            try {
                showHighScores();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void showHighScores() throws IOException {
        URL location = getClass().getClassLoader().getResource("scoreBoard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent scoreboardRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), scoreboardRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) scoreBoardButton.getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(scoreboardRoot);
        stage.setTitle("High Scores - TetrisJFX");
        fadeIn.play();
    }

    private void startGame() throws IOException {
        URL location = getClass().getClassLoader().getResource("chooseDifficulty.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent gameRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), gameRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(new Scene(gameRoot, 1280, 800));
        stage.setTitle("TetrisJFX");
        stage.setResizable(false);
        fadeIn.play();
    }
}
