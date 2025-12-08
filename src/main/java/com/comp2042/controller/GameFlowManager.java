package com.comp2042.controller;

import com.comp2042.events.InputEventListener;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.logic.GameTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Manages the core flow and state of the game, including game timing, pause/resume,
 * and speed control.
 *
 * <p>This class controls two separate JavaFX {@code Timeline}s:
 * one for the periodic falling of the brick ({@code timeline})
 * and one for the periodic addition of rising rows ({@code risingRowTimeline}).</p>
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Single Responsibility Principle (SRP)**: Decouples the timing and state management logic from the {@code GuiController} and {@code GameController}, centralizing control over the game loop.</li>
 * <li>**Command Pattern**: It accepts {@code Runnable} actions (like {@code gameAction} and {@code risingRowAction}) which represent commands to be executed periodically by the JavaFX {@code Timeline} scheduling mechanism.</li>
 * <li>**Timer Pattern**: Uses JavaFX {@code Timeline}s to coordinate time-based events, managing their start, stop, and dynamic speed updates.</li>
 * </ul>
 */
public class GameFlowManager {

    public Timeline timeline;
    private Timeline risingRowTimeline;
    private final GridPane gamePanel;
    private InputEventListener eventListener;
    private GameTime gameTime;
    private GameMode gameMode;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private Runnable gameAction;
    private Runnable risingRowAction;
    int currentDropRate = 400;
    int currentRisingRowInterval = 20;

    /**
     * Constructs the GameFlowManager.
     *
     * @param gamePanel The main game GridPane, used for requesting focus.
     * @param eventListener The listener for game events (typically the GameController).
     */
    public GameFlowManager(GridPane gamePanel, InputEventListener eventListener) {
        this.gamePanel = gamePanel;
        this.eventListener = eventListener;
    }

    /**
     * Sets the current game mode.
     * @param gameMode The selected game mode.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Sets the game time object for tracking elapsed time.
     * @param gameTime The game timer instance.
     */
    public void setGameTimer(GameTime gameTime) {
        this.gameTime = gameTime;
    }

    /**
     * Sets the listener for game events.
     * @param eventListener The input event listener.
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Creates and configures the main game timeline for brick dropping at a rate of {@code currentDropRate}.
     *
     * @param action The runnable to execute on each timer tick (i.e., moving the brick down).
     */
    public void createTimeline(Runnable action) {
        this.gameAction = action;
        timeline = new Timeline(
                new KeyFrame(Duration.millis(currentDropRate), e -> action.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Updates the brick dropping speed based on the new game level, but only if the current
     * {@code GameMode} affects falling speed (Normal and Extreme Mode).
     * If the speed changes, the internal timeline is recreated and restarted.
     *
     * <p>The speed starts at 400ms and decreases by 70ms per level up to level 5.</p>
     *
     * @param newLevel The current level of the game.
     */
    public void updateSpeed(int newLevel) {
        if (gameMode.affectsFallingSpeed()) {
            if (newLevel <= 0) {
                newLevel = 1;
            } else if (newLevel > 5) {
                newLevel = 5;
            }

            int newDropRate = 400 - (70 * (newLevel - 1));

            if (newDropRate != currentDropRate) {
                currentDropRate = newDropRate;

                boolean wasRunning = (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING);

                if (timeline != null) {
                    timeline.stop();
                }

                if (this.gameAction != null) {
                    createTimeline(this.gameAction);
                }

                if (wasRunning && timeline != null) {
                    timeline.play();
                }
            }
        } else {
            // For Hard mode that does not require falling brick speed
            if (currentDropRate != 400) {
                currentDropRate = 400;

                boolean wasRunning = (timeline.getStatus() == Timeline.Status.RUNNING);

                if (timeline != null) {
                    timeline.stop();
                }

                if (this.gameAction != null) {
                    createTimeline(this.gameAction);
                }

                if (wasRunning && timeline != null) {
                    timeline.play();
                }

            }
        }
        updateRisingRowSpeed(newLevel);
    }

    /**
     * Creates and configures the timeline for the periodic addition of rising rows.
     *
     * @param action The runnable to execute on each timer tick (i.e., adding a new rising row).
     */
    public void createRisingRowTimeline(Runnable action) {
        this.risingRowAction = action;
        risingRowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(currentRisingRowInterval), e -> action.run())
        );
        risingRowTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Updates the interval for rising rows based on the current level.
     * The interval decreases from 20 seconds at Level 1 to 10 seconds at Level 5.
     *
     * @param newLevel The current level of the game.
     */
    private void updateRisingRowSpeed(int newLevel) {

        int newInterval;

        if (risingRowTimeline == null || risingRowAction == null) {
            return;
        }

        if (newLevel < 1) {
            risingRowTimeline.stop();
            return;
        } else if (newLevel == 1) {
            newInterval = 20;
        } else if (newLevel == 2) {
            newInterval = 18;
        } else if (newLevel == 3) {
            newInterval = 15;
        } else if (newLevel == 4) {
            newInterval = 12;
        } else {
            newInterval = 10;
        }

        if (newInterval != currentRisingRowInterval) {
            currentRisingRowInterval = newInterval;

            boolean wasRunning = (risingRowTimeline.getStatus() == Timeline.Status.RUNNING);
            risingRowTimeline.stop();

            // Recreate timeline with the new duration
            risingRowTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(currentRisingRowInterval), e -> risingRowAction.run())
            );
            risingRowTimeline.setCycleCount(Timeline.INDEFINITE);

            if (wasRunning) {
                risingRowTimeline.play();
            }
        }

        if (risingRowTimeline.getStatus() != Timeline.Status.RUNNING && !isPause.get()) {
            risingRowTimeline.play();
        }
    }

    /**
     * Starts the game: begins the main timeline, the rising row timeline (for hard and extreme mode), and the game timer.
     */
    public void start() {
        timeline.play();
        if (risingRowTimeline != null) {
            risingRowTimeline.play();
        }
        if (gameTime != null) {
            gameTime.start();
        }
    }

    /**
     * Stops the game: halts the main timeline, the rising row timeline, and pauses the game timer.
     */
    private void stop() {
        timeline.stop();
        if (risingRowTimeline != null) {
            risingRowTimeline.stop();
        }
        if (gameTime != null) {
            gameTime.stop();
        }
    }

    /**
     * Stops the game flow and sets the game over state to true.
     */
    public void gameOver() {
        stop();
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Resets the game flow for a new game:
     * 1. Stops all timelines.
     * 2. Calls {@code eventListener.createNewGame()} to reset the game board.
     * 3. Resets and recreates falling brick timeline for initial speed.
     * 4. Resets the rising row interval and updates speed.
     * 5. Starts the flow and resets state flags.
     */
    public void newGame() {
        stop();
        eventListener.createNewGame();
        gamePanel.requestFocus();
        currentDropRate = 400;

        // recreates the falling brick timeline for every new game
        if (this.gameAction != null) {
            createTimeline(this.gameAction);
        }

        currentRisingRowInterval = 20;
        updateSpeed(1);
        start();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    /**
     * Toggles the paused state of the game, stopping or starting the timelines and timer as needed.
     */
    public void pauseGame() {
        if(isPause.get()) {
            start();
        } else {
            stop();
        }
        isPause.set(!isPause.get());
        gamePanel.requestFocus();
    }

    /**
     * Returns the property indicating if the game is currently paused.
     * @return The boolean property for the pause state.
     */
    public BooleanProperty isPause() {
        return isPause;
    }

    /**
     * Returns the property indicating if the game is currently over.
     * @return The boolean property for the game over state.
     */
    public BooleanProperty isGameOver() {
        return isGameOver;
    }
}