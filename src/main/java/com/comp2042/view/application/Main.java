package com.comp2042.view.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * The main entry point for the TetrisJFX application, extending the JavaFX {@code Application} class.
 */
public class Main extends Application {

    /**
     * The primary entry point for all JavaFX applications. This method loads the
     * {@code mainMenuScreen.fxml}, sets up the scene dimensions (1280x800), and displays
     * the stage.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception if the FXML file fails to load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("scenes_FXML/mainMenuScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}