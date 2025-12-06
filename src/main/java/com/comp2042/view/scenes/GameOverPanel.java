package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class GameOverPanel {

    private final Pane rootPane;
    private Parent gameOverPanel;
    private Button homeButton;

    public GameOverPanel(Pane rootPane) {
        this.rootPane = rootPane;
    }

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

    public void showPanel(GameFlowManager gameFlow) {
        if (gameOverPanel != null) {
            gameOverPanel.setVisible(gameFlow.isGameOver().get());
        }
    }

    private void handleHome() throws IOException {
        SceneSwitch.loadMainMenu(homeButton);
    }

}
