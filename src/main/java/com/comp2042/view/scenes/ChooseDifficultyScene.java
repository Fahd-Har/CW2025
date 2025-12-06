package com.comp2042.view.scenes;

import com.comp2042.controller.GameController;
import com.comp2042.controller.GuiController;
import com.comp2042.model.logic.GameMode;
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

public class ChooseDifficultyScene {

    @FXML private Button normalButton;
    @FXML private Button hardButton;
    @FXML private Button extremeButton;
    @FXML private Button startButton;
    @FXML private Button backButton;

    // ADDED: Field to track the selected mode, default to Normal
    private GameMode selectedMode = GameMode.NORMAL_MODE;

    @FXML
    public void initialize() {

        updateButtonStyles(normalButton);
        normalButton.setOnAction(event -> selectNormalMode());
        hardButton.setOnAction(event -> selectHardMode());
        extremeButton.setOnAction(event -> selectExtremeMode());

        startButton.setOnAction(event -> {
            try {
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.setOnAction(event -> {
            try {
                loadHome();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void selectNormalMode() {
        selectedMode = GameMode.NORMAL_MODE;
        updateButtonStyles(normalButton);
    }

    @FXML
    private void selectHardMode() {
        selectedMode = GameMode.HARD_MODE;
        updateButtonStyles(hardButton);
    }

    @FXML
    private void selectExtremeMode() {
        selectedMode = GameMode.EXTREME_MODE;
        updateButtonStyles(extremeButton);
    }

    private void updateButtonStyles(Button selectedButton) {
        // Remove the 'selected' style class from ALL difficulty buttons
        normalButton.getStyleClass().remove("selected");
        hardButton.getStyleClass().remove("selected");
        extremeButton.getStyleClass().remove("selected");

        // Add the 'selected' style class to the currently chosen button
        selectedButton.getStyleClass().add("selected");
    }

    private void startGame() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent gameRoot = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), gameRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(new Scene(gameRoot, 1280, 800));
        stage.setTitle("TetrisJFX");
        stage.setResizable(false);
        new GameController(c, selectedMode);
        fadeIn.play();
    }

    private void loadHome() throws IOException {
        SceneSwitch.loadMainMenu(backButton);
    }
}
