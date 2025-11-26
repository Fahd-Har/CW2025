package com.comp2042.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameTimeline {

    private final Timeline timeline;

    public GameTimeline(Runnable action) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(400), e -> action.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
