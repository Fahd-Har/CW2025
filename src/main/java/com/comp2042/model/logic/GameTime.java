package com.comp2042.model.logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public final class GameTime {

    private final StringProperty timeString = new SimpleStringProperty("00:00");
    private final Timeline timeline;
    private long startTime;
    private long pauseDuration; // Stores time in seconds accumulated before a pause

    public GameTime() {
        // Timeline to update the time every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.pauseDuration = 0; // Initialize accumulated time
    }

    private void updateTime() {
        // Calculate elapsed time in seconds by adding the running time to the accumulated pauseDuration
        long elapsedSeconds = ((System.currentTimeMillis() - startTime) / 1000) + pauseDuration;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        // Format the time as MM:SS
        timeString.set(String.format("%02d:%02d", minutes, seconds));
    }

    public StringProperty timeStringProperty() {
        return timeString;
    }

    public void start() {
        if (timeline.getStatus() != Timeline.Status.RUNNING) {
            // Set the new start time now, the running time will be calculated relative to this point
            startTime = System.currentTimeMillis();
            timeline.play();
        }
    }

    public void stop() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.stop();
            // Calculate time elapsed since last start and add it to the accumulated duration
            pauseDuration += (System.currentTimeMillis() - startTime) / 1000;
        }
    }

    public void reset() {
        stop();
        startTime = 0;
        pauseDuration = 0; // Reset accumulated time
        timeString.set("00:00");
    }
}