package com.comp2042.controller.keyInput;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.view.data.ViewData;
import javafx.scene.input.KeyEvent;

import javafx.event.ActionEvent;
import java.util.function.Consumer;

public class KeyInputHandler {

    private final InputEventListener eventListener;

    private final Consumer<ActionEvent> newGameAction;
    private final Consumer<ActionEvent> pauseGameAction;

    public KeyInputHandler(
            InputEventListener eventListener,
            Consumer<ActionEvent> newGameAction,
            Consumer<ActionEvent> pauseGameAction
    ) {
        this.eventListener = eventListener;
        this.newGameAction = newGameAction;
        this.pauseGameAction = pauseGameAction;
    }

    public void handleGlobalKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case N -> newGameAction.accept(null);
            case ESCAPE -> pauseGameAction.accept(null);
        }
    }

    public void handleBrickControlKeys(KeyEvent keyEvent, Consumer<MoveEvent> moveDownHandler, Consumer<ViewData> refreshHandler,
                                       Consumer<MoveEvent> hardDropHandler) {

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
            case SPACE -> hardDropHandler.accept(new MoveEvent(EventType.SLAM, EventSource.USER));
            case C ->
                    refreshHandler.accept(
                            eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER))
                    );
        }
    }
}
