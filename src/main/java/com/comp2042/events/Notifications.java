package com.comp2042.events;

import com.comp2042.view.scenes.NotificationPanel;
import javafx.scene.Group;

public class Notifications {

    private final Group scoreNotification;
    private final Group levelUpNotifications;

    public Notifications(Group scoreNotification, Group levelUpNotifications) {
        this.scoreNotification = scoreNotification;
        this.levelUpNotifications = levelUpNotifications;
    }

    public void showScore(int bonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + bonus, "bonusStyle");

        scoreNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(scoreNotification.getChildren());
    }

    public void showLevelUp(int level) {
        String text = "LEVEL " + level + "!";
        // Use the new constructor and specify the new style class
        NotificationPanel notificationPanel = new NotificationPanel(text, "levelUpStyle");

        levelUpNotifications.getChildren().add(notificationPanel);
        // The animation logic is the same, so we reuse showScore
        notificationPanel.showScore(levelUpNotifications.getChildren());
    }
}
