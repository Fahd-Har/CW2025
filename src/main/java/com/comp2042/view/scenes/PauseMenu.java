package com.comp2042.view.scenes;
import com.comp2042.controller.GameFlowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

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
            URL pauseLocation = getClass().getClassLoader().getResource("pauseScreen.fxml");
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showPanel(GameFlowManager gameFlow) {
        if (pauseMenu != null) {
            pauseMenu.setVisible(gameFlow.isPause().get());
        }
    }

}
