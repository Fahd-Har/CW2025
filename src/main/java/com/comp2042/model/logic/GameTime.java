package com.comp2042.model.logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

/**
 * Manages the elapsed time for the current game session, providing accurate time tracking
 * that accounts for time spent while the game is paused.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Observable/Listener Pattern (via JavaFX Property)**: Exposes the elapsed time as a bindable {@code StringProperty}
 * (MM:SS), allowing the GUI to update automatically.</li>
 * </ul>
 */
public final class GameTime {

    private final StringProperty timeString = new SimpleStringProperty("00:00");
    private final Timeline timeline;
    private long startTime;
    private long pauseDuration; // Stores time in seconds accumulated before a pause

    /**
     * Constructs the GameTime object, initializing the periodic {@code Timeline}.
     */
    public GameTime() {
        // Timeline to update the time every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.pauseDuration = 0; // Initialize accumulated time
    }

    /**
     * Calculates the total elapsed time in seconds (running time + accumulated paused duration)
     * and updates the {@code timeString} property in MM:SS format.
     */
    private void updateTime() {
        // Calculate elapsed time in seconds by adding the running time to the accumulated pauseDuration
        long elapsedSeconds = ((System.currentTimeMillis() - startTime) / 1000) + pauseDuration;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        // Format the time as MM:SS
        timeString.set(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Returns the observable property for the formatted time string.
     * @return The {@code StringProperty} displaying "MM:SS".
     */
    public StringProperty timeStringProperty() {
        return timeString;
    }

    /**
     * Starts or resumes the game timer. If the timer is not running, it records the new
     * start time and begins the {@code Timeline} animation.
     */
    public void start() {
        if (timeline.getStatus() != Timeline.Status.RUNNING) {
            // Set the new start time now, the running time will be calculated relative to this point
            startTime = System.currentTimeMillis();
            timeline.play();
        }
    }

    /**
     * Stops (pauses) the game timer. It calculates the time elapsed since the last
     * start and adds it to the {@code pauseDuration} for later resumption.
     */
    public void stop() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.stop();
            // Calculate time elapsed since last start and add it to the accumulated duration
            pauseDuration += (System.currentTimeMillis() - startTime) / 1000;
        }
    }

    /**
     * Resets the timer state for a new game, stopping the timeline and clearing all
     * time counters.
     */
    public void reset() {
        stop();
        startTime = 0;
        pauseDuration = 0; // Reset accumulated time
        timeString.set("00:00");
    }
}