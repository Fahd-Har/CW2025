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

/**
 * Controller for the Main Menu scene ({@code mainMenuScreen.fxml}).
 * This class manages navigation to the different main sections of the application:
 * Choose Difficulty, Scoreboard, and About.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**View Controller (MVC)**: Handles user input on the menu and orchestrates the transition to other scenes.</li>
 * </ul>
 */
public class MainMenuScene {
    @FXML private Button startButton;
    @FXML private Button scoreBoardButton;
    @FXML private Button aboutButton;

    /**
     * Initializes the controller, setting up event handlers for all main menu buttons.
     */
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

        if (aboutButton != null) {
            aboutButton.setOnAction(ae -> {
                try {
                    showAbout();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    /**
     * Loads the Scoreboard scene ({@code scoreBoard.fxml}) with a fade transition.
     *
     * @throws IOException If the scoreboard FXML cannot be loaded.
     */
    private void showHighScores() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/scoreBoard.fxml");
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

    /**
     * Loads the Choose Difficulty scene ({@code chooseDifficulty.fxml}) with a fade transition.
     *
     * @throws IOException If the difficulty selection FXML cannot be loaded.
     */
    private void startGame() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/chooseDifficulty.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
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

    /**
     * Loads the About scene ({@code aboutScreen.fxml}) with a fade transition.
     *
     * @throws IOException If the About FXML cannot be loaded.
     */
    private void showAbout() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/aboutScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent rulesRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), rulesRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) aboutButton.getScene().getWindow();
        stage.setScene(new Scene(rulesRoot, 1280, 800));
        stage.setTitle("TetrisJFX - About");
        stage.setResizable(false);
        fadeIn.play();
    }
}