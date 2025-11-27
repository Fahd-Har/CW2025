package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.gameLogic.ViewData;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

public class KeyInputHandler {

    private final InputEventListener eventListener;

    private final Runnable newGameAction;
    private final Runnable pauseGameAction;

    public KeyInputHandler(
            InputEventListener eventListener,
            Runnable newGameAction,
            Runnable pauseGameAction
    ) {
        this.eventListener = eventListener;
        this.newGameAction = newGameAction;
        this.pauseGameAction = pauseGameAction;
    }

    public void handleGlobalKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case N -> newGameAction.run();
            case ESCAPE -> pauseGameAction.run();
        }
    }

    public void handleMovementKeys(KeyEvent keyEvent, Consumer<MoveEvent> moveDownHandler, Consumer<ViewData> refreshHandler) {

        switch (keyEvent.getCode()) {
            case LEFT, A ->
                    refreshHandler.accept(
                            eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER))
                    );
            case RIGHT, D ->
                    refreshHandler.accept(
                            eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER))
                    );
            case UP, W ->
                    refreshHandler.accept(
                            eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER))
                    );
            case DOWN, S ->
                    moveDownHandler.accept(
                            new MoveEvent(EventType.DOWN, EventSource.USER)
                    );
        }
    }
}
