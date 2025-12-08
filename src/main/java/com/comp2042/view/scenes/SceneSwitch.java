package com.comp2042.view.scenes; // Recommended package

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

/**
 * A utility class providing common, reusable methods for navigating back to the Main Menu
 * with a standard transition effect.
 */
public final class SceneSwitch {

    /**
     * Loads the Main Menu scene ({@code mainMenuScreen.fxml}) and replaces the current scene
     * on the stage with a 1.0 second fade-in transition.
     *
     * @param controlNode A node from the current scene (e.g., a Button) used to reference
     * the current {@code Scene} and {@code Stage}.
     * @throws IOException If the FXML file for the main menu cannot be loaded.
     */
    public static void loadMainMenu(Node controlNode) throws IOException {
        URL location = com.comp2042.view.scenes.SceneSwitch.class.getClassLoader().getResource("scenes_FXML/mainMenuScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent menuRoot = fxmlLoader.load();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), menuRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) controlNode.getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(menuRoot);
        stage.setTitle("TetrisJFX");
        fadeIn.play();
    }
}