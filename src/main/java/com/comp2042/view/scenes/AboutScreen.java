package com.comp2042.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AboutScreen {
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> {
            try {
                // Navigate back to the MainMenuScreen
                loadHome();
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load main menu.", ex);
            }
        });
    }

    private void loadHome() throws IOException {
        SceneSwitch.loadMainMenu(backButton);
    }
}
