package com.comp2042.events;

import com.comp2042.view.scenes.NotificationPanel;
import javafx.scene.Group;

public class Notifications {

    private final Group groupNotification;

    public Notifications(Group groupNotification) {
        this.groupNotification = groupNotification;
    }

    public void showScore(int bonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + bonus);

        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

}
