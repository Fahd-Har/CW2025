package com.comp2042.view.scenes;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class AboutScene {
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> {
            try {
                // Navigate back to the MainMenuScreen
                loadBack();
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load main menu.", ex);
            }
        });
    }

    private void loadBack() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/mainMenuScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent menuRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), menuRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(menuRoot);
        stage.setTitle("TetrisJFX");
        fadeIn.play();
    }
}
