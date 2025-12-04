package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class GameOverPanel {

    private final Pane rootPane;
    private Parent gameOverPanel;

    public GameOverPanel(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public void loadGameOverPanel() {
        try {
            URL pauseLocation = getClass().getClassLoader().getResource("gameOverPanel.fxml");
            if (pauseLocation != null) {
                FXMLLoader gameOverLoader = new FXMLLoader(pauseLocation);
                gameOverPanel = gameOverLoader.load();

                gameOverPanel.setLayoutX((1280 - 650) / 2.0);
                gameOverPanel.setLayoutY((800 - 360) / 2.0);

                if (rootPane != null) {
                    rootPane.getChildren().add(gameOverPanel);
                    gameOverPanel.setVisible(false);
                }
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

}
