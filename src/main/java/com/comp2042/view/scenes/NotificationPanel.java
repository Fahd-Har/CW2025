package com.comp2042.view.scenes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * A custom JavaFX component extending {@code BorderPane} used to display transient,
 * animated notifications for score bonuses or level ups.
 *
 * <p>The panel animates by floating upwards while fading out, automatically removing itself
 * from its parent container upon completion.</p>
 */
public class NotificationPanel extends BorderPane {

    /**
     * Constructs a NotificationPanel with specified text and a CSS style class.
     *
     * @param text The text content (e.g., "+50" or "LEVEL 2!").
     * @param classStyle The CSS style class to apply for styling the text.
     */
    public NotificationPanel(String text, String classStyle) {
        setMinHeight(200);
        setMinWidth(220);
        final Label score = new Label(text);
        score.getStyleClass().add(classStyle);
        setCenter(score);

    }

    /**
     * Executes the combined fade and translate animation for the notification.
     * The panel is automatically removed from the parent list upon the animation's completion.
     *
     * @param list The {@code ObservableList} of nodes (children of the parent {@code Group})
     * from which this panel should be removed.
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }
}