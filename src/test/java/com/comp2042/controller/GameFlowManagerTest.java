package com.comp2042.controller;

import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.view.data.ViewData;
import com.comp2042.view.scenes.GameOverPanel;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFlowManagerTest {

    @BeforeAll
    static void startJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    private GameFlowManager createManager() {
        GridPane panel = new GridPane();
        GameOverPanel over = new GameOverPanel();

        InputEventListener listener = new InputEventListener() {
            @Override
            public MovingDownData onDownEvent(MoveEvent event) {
                return null;
            }

            @Override
            public ViewData onLeftEvent(MoveEvent event) {
                return null;
            }

            @Override
            public ViewData onRightEvent(MoveEvent event) {
                return null;
            }

            @Override
            public ViewData onRotateEvent(MoveEvent event) {
                return null;
            }

            @Override
            public void createNewGame() {
            }
        };

        return new GameFlowManager(panel, over, listener);
    }

    private GameFlowManager startManager() {
        GameFlowManager manager = createManager();
        manager.createTimeline(() -> {});
        manager.start();
        return manager;
    }

    @Test
    void timelineStarts() {
        GameFlowManager manager = startManager();

        assertEquals(javafx.animation.Animation.Status.RUNNING,
                manager.timeline.getStatus());
    }

    @Test
    void pauseStopsTimeline() {
        GameFlowManager manager = startManager();

        manager.pauseGame(null);

        assertEquals(javafx.animation.Animation.Status.STOPPED,
                manager.timeline.getStatus());
    }

    @Test
    void pauseTwiceRestartsTimeline() {
        GameFlowManager manager = startManager();

        manager.pauseGame(null); // pause
        manager.pauseGame(null); // resume

        assertEquals(javafx.animation.Animation.Status.RUNNING,
                manager.timeline.getStatus());
    }

    @Test
    void newGameRestartsTimeline() {
        GameFlowManager manager = startManager();

        manager.gameOver();
        manager.newGame(null);

        assertEquals(javafx.animation.Animation.Status.RUNNING,
                manager.timeline.getStatus());
    }
}
