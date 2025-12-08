package com.comp2042.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller for the 'About' scene ({@code aboutScreen.fxml}), which displays the
 * game rules, gameplay information, and details about the difficulty modes.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**View Controller (MVC)**: Handles user interaction on the About screen, primarily
 * delegating the navigation action to {@code SceneSwitch}.</li>
 * </ul>
 */
public class AboutScene {
    @FXML private Button backButton;

    /**
     * Initializes the controller, setting up the action handler for the 'Back' button
     * to navigate to the Main Menu.
     */
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

    /**
     * Delegates the scene switching operation to the shared utility class.
     *
     * @throws IOException If the FXML for the main menu cannot be loaded.
     */
    private void loadHome() throws IOException {
        SceneSwitch.loadMainMenu(backButton);
    }
}