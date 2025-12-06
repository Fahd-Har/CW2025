package com.comp2042.controller;

import com.comp2042.events.InputEventListener;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.logic.GameTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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

    public GameFlowManager(GridPane gamePanel, InputEventListener eventListener) {
        this.gamePanel = gamePanel;
        this.eventListener = eventListener;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setGameTimer(GameTime gameTime) {
        this.gameTime = gameTime;
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void createTimeline(Runnable action) {
        this.gameAction = action;
        timeline = new Timeline(
                new KeyFrame(Duration.millis(currentDropRate), e -> action.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

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

    public void createRisingRowTimeline(Runnable action) {
        this.risingRowAction = action;
        risingRowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(currentRisingRowInterval), e -> action.run())
        );
        risingRowTimeline.setCycleCount(Timeline.INDEFINITE);
    }

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

    public void start() {
        timeline.play();
        if (risingRowTimeline != null) {
            risingRowTimeline.play();
        }
        if (gameTime != null) {
            gameTime.start();
        }
    }

    private void stop() {
        timeline.stop();
        if (risingRowTimeline != null) {
            risingRowTimeline.stop();
        }
        if (gameTime != null) {
            gameTime.stop();
        }
    }

    public void gameOver() {
        stop();
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
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

    public void pauseGame(ActionEvent actionEvent) {
        if(isPause.get()) {
            start();
        } else {
            stop();
        }
        isPause.set(!isPause.get());
        gamePanel.requestFocus();
    }

    // ADD these public getters
    public BooleanProperty isPause() {
        return isPause;
    }

    public BooleanProperty isGameOver() {
        return isGameOver;
    }
}
