package com.comp2042.view.scenes;

import com.comp2042.controller.GameController;
import com.comp2042.controller.GuiController;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.scoreBoard.ScoreBoardManager;
import javafx.fxml.FXML;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Controller for the 'Choose Difficulty' scene ({@code chooseDifficulty.fxml}).
 * This scene allows the player to select a {@code GameMode} before starting the main game.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**View Controller (MVC)**: Manages UI interactions for mode selection and orchestrates the transition to the main
 * game scene, injecting the selected mode into the {@code GameController}.</li>
 * </ul>
 */
public class ChooseDifficultyScene {

    @FXML private Button normalButton;
    @FXML private Button hardButton;
    @FXML private Button extremeButton;
    @FXML private Button startButton;
    @FXML private Button backButton;

    // ADDED: Field to track the selected mode, default to Normal
    private GameMode selectedMode = GameMode.NORMAL_MODE;

    private final ScoreBoardManager scoreBoardManager = new ScoreBoardManager();

    /**
     * Initializes the controller, sets up default styles, and configures event handlers for
     * mode selection, starting the game, and navigating back.
     */
    @FXML
    public void initialize() {

        updateButtonStyles(normalButton);
        normalButton.setOnAction(event -> selectNormalMode());
        hardButton.setOnAction(event -> selectHardMode());
        extremeButton.setOnAction(event -> selectExtremeMode());

        startButton.setOnAction(event -> {
            try {
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.setOnAction(event -> {
            try {
                loadHome();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Sets the selected mode to Normal and updates button styles.
     */
    @FXML
    private void selectNormalMode() {
        selectedMode = GameMode.NORMAL_MODE;
        updateButtonStyles(normalButton);
    }

    /**
     * Sets the selected mode to Hard and updates button styles.
     */
    @FXML
    private void selectHardMode() {
        selectedMode = GameMode.HARD_MODE;
        updateButtonStyles(hardButton);
    }

    /**
     * Sets the selected mode to Extreme and updates button styles.
     */
    @FXML
    private void selectExtremeMode() {
        selectedMode = GameMode.EXTREME_MODE;
        updateButtonStyles(extremeButton);
    }

    /**
     * Manages the visual state of the difficulty buttons by ensuring only the
     * currently selected button has the 'selected' CSS class.
     *
     * @param selectedButton The button corresponding to the newly selected game mode.
     */
    private void updateButtonStyles(Button selectedButton) {
        // Remove the 'selected' style class from ALL difficulty buttons
        normalButton.getStyleClass().remove("selected");
        hardButton.getStyleClass().remove("selected");
        extremeButton.getStyleClass().remove("selected");

        // Add the 'selected' style class to the currently chosen button
        selectedButton.getStyleClass().add("selected");
    }

    /**
     * Loads the main game layout scene ({@code gameLayout.fxml}), initializes a new
     * {@code GameController} with the selected {@code GameMode}, and transitions the stage.
     *
     * @throws IOException If the game layout FXML cannot be loaded.
     */
    private void startGame() throws IOException {
        URL location = getClass().getClassLoader().getResource("scenes_FXML/gameLayout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent gameRoot = fxmlLoader.load();
        GuiController controller = fxmlLoader.getController();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), gameRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(new Scene(gameRoot, 1280, 800));
        stage.setTitle("TetrisJFX");
        stage.setResizable(false);
        new GameController(controller, selectedMode, scoreBoardManager);
        fadeIn.play();
    }

    /**
     * Loads the Main Menu scene using the shared scene switching utility.
     *
     * @throws IOException If the Main Menu FXML cannot be loaded.
     */
    private void loadHome() throws IOException {
        SceneSwitch.loadMainMenu(backButton);
    }
}