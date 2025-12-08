package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Manages the loading, positioning, and display of the Game Over panel ({@code gameOverPanel.fxml}).
 */
public class GameOverPanel {

    private final Pane rootPane;
    private Parent gameOverPanel;
    private Button homeButton;

    /**
     * Constructs the panel manager, requiring the root container pane of the game scene.
     *
     * @param rootPane The parent pane where the Game Over menu overlay will be added.
     */
    public GameOverPanel(Pane rootPane) {
        this.rootPane = rootPane;
    }

    /**
     * Loads the Game Over FXML, centers it on the screen, and adds it as a hidden
     * child to the root pane.
     */
    public void loadGameOverPanel() {
        try {
            URL gameOverLocation = getClass().getClassLoader().getResource("scenes_FXML/gameOverPanel.fxml");
            if (gameOverLocation != null) {
                FXMLLoader gameOverLoader = new FXMLLoader(gameOverLocation);
                gameOverPanel = gameOverLoader.load();

                gameOverPanel.setLayoutX((1280 - 650) / 2.0);
                gameOverPanel.setLayoutY((800 - 360) / 2.0);

                if (rootPane != null) {
                    rootPane.getChildren().add(gameOverPanel);
                    gameOverPanel.setVisible(false);
                }
            }

            homeButton = (Button) gameOverPanel.lookup("#homeButton");
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
     * Controls the visibility of the Game Over panel, typically bound to the game's
     * {@code isGameOver} state.
     *
     * @param gameFlow The {@code GameFlowManager} used to check the {@code isGameOver} state.
     */
    public void showPanel(GameFlowManager gameFlow) {
        if (gameOverPanel != null) {
            gameOverPanel.setVisible(gameFlow.isGameOver().get());
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