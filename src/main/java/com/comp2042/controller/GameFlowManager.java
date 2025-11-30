package com.comp2042.controller;

import com.comp2042.events.InputEventListener;
import com.comp2042.model.logic.GameTime;
import com.comp2042.view.scenes.GameOverPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GameFlowManager {

    public Timeline timeline; // Now managed directly
    private final GridPane gamePanel;
    private final GameOverPanel gameOverPanel;
    private InputEventListener eventListener;
    private GameTime gameTime;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    public GameFlowManager(GridPane gamePanel, GameOverPanel gameOverPanel, InputEventListener eventListener) {
        this.gamePanel = gamePanel;
        this.gameOverPanel = gameOverPanel;
        this.eventListener = eventListener;
    }

    public void setGameTimer(GameTime gameTime) {
        this.gameTime = gameTime;
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void createTimeline(Runnable action) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(400), e -> action.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
        if (gameTime != null) {
            gameTime.start();
        }
    }

    private void stop() {
        timeline.stop();
        if (gameTime != null) {
            gameTime.stop();
        }
    }

    public void gameOver() {
        stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
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
