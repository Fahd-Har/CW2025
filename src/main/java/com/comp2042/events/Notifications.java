package com.comp2042.events;

import com.comp2042.view.scenes.NotificationPanel;
import javafx.scene.Group;

/**
 * Manages the creation and display of transient, animated pop-up notifications for
 * in-game events, such as scoring bonuses and level advancements.
 *
 */
public class Notifications {

    private final Group scoreNotification;
    private final Group levelUpNotifications;

    /**
     * Constructs the Notifications manager, specifying the JavaFX parent groups where
     * the score and level-up notifications will be added.
     *
     * @param scoreNotification The JavaFX {@code Group} dedicated to score/bonus notifications.
     * @param levelUpNotifications The JavaFX {@code Group} dedicated to level-up notifications.
     */
    public Notifications(Group scoreNotification, Group levelUpNotifications) {
        this.scoreNotification = scoreNotification;
        this.levelUpNotifications = levelUpNotifications;
    }

    /**
     * Creates and displays an animated notification for a score bonus.
     * The notification is styled using the "bonusStyle" CSS class.
     *
     * @param bonus The score amount to display (e.g., 100).
     */
    public void showScore(int bonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + bonus, "bonusStyle");

        scoreNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(scoreNotification.getChildren());
    }

    /**
     * Creates and displays an animated notification for a level-up event.
     * The notification is styled using the "levelUpStyle" CSS class.
     *
     * @param level The new level number to display.
     */
    public void showLevelUp(int level) {
        String text = "LEVEL " + level + "!";
        // Use the new constructor and specify the new style class
        NotificationPanel notificationPanel = new NotificationPanel(text, "levelUpStyle");

        levelUpNotifications.getChildren().add(notificationPanel);
        // The animation logic is the same, so we reuse showScore
        notificationPanel.showScore(levelUpNotifications.getChildren());
    }
}