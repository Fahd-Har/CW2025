package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyMovementEventHandler {

    private final GuiController guiController;
    private final GameView gameView;
    private final BooleanProperty isPause;
    private final BooleanProperty isGameOver;

    public KeyMovementEventHandler(
            GuiController guiController,
            GameView gameView,
            BooleanProperty isPause,
            BooleanProperty isGameOver) {

        this.guiController = guiController;
        this.gameView = gameView;
        this.isPause = isPause;
        this.isGameOver = isGameOver;
    }

    public void setupKeyBrickMovements(KeyEvent keyEvent) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {

            InputEventListener listener = guiController.getEventListener();

            if (listener == null) {
                return;
            }

            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                gameView.refreshBrick(listener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                gameView.refreshBrick(listener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                gameView.refreshBrick(listener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                guiController.moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                keyEvent.consume();
            }
        }
    }
}