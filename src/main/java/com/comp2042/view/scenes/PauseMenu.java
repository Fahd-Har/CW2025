package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class PauseMenu {

    private final Pane rootPane;
    private Parent pauseMenu;

    public PauseMenu(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public void loadPauseScreen() {
        try {
            URL pauseLocation = getClass().getClassLoader().getResource("scenes_FXML/pauseScreen.fxml");
            if (pauseLocation != null) {
                FXMLLoader pauseLoader = new FXMLLoader(pauseLocation);
                pauseMenu = pauseLoader.load();

                // Set position to center the 650x360 pause screen on the 1280x800 window
                pauseMenu.setLayoutX((1280 - 650) / 2.0);
                pauseMenu.setLayoutY((800 - 360) / 2.0);

                if (rootPane != null) {
                    rootPane.getChildren().add(pauseMenu);
                    pauseMenu.setVisible(false);
                }
            }

            Button homeButton = (Button) pauseMenu.lookup("#homeButton");
            if (homeButton != null) {
                homeButton.setOnAction(event -> {
                    try {
                        handleHome();
                    } catch (IOException ex) {
                        throw new RuntimeException("Failed to load main menu.", ex);
                    }
                });
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showPanel(GameFlowManager gameFlow) {
        if (pauseMenu != null) {
            pauseMenu.setVisible(gameFlow.isPause().get());
        }
    }

    private void handleHome() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/mainMenuScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent menuRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), menuRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Get the current stage from the root pane's scene
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(menuRoot);
        stage.setTitle("TetrisJFX");
        fadeIn.play();
    }
}
