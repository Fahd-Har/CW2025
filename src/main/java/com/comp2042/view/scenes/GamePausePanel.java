package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Manages the loading, positioning, and display of the pause menu panel ({@code pauseScreen.fxml})
 */
public class GamePausePanel {

    private final Pane rootPane;
    private Parent pauseMenu;
    private Button homeButton;

    /**
     * Constructs the panel manager, requiring the root container pane of the game scene.
     *
     * @param rootPane The parent pane where the pause menu overlay will be added.
     */
    public GamePausePanel(Pane rootPane) {
        this.rootPane = rootPane;
    }

    /**
     * Loads the pause menu FXML, centers it on the screen, and adds it as a hidden
     * child to the root pane.
     */
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

            homeButton = (Button) pauseMenu.lookup("#homeButton");
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

    /**
     * Controls the visibility of the pause panel, typically bound to the game's pause state.
     *
     * @param gameFlow The {@code GameFlowManager} used to check the {@code isPause} state.
     */
    public void showPanel(GameFlowManager gameFlow) {
        if (pauseMenu != null) {
            pauseMenu.setVisible(gameFlow.isPause().get());
        }
    }

    /**
     * Handles the action to return to the Main Menu scene.
     *
     * @throws IOException If the FXML for the main menu cannot be loaded.
     */
    private void handleHome() throws IOException {
        SceneSwitch.loadMainMenu(homeButton);
    }
}