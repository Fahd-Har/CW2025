package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.gameLogic.MovingDownData;
import com.comp2042.gameLogic.ViewData;
import com.comp2042.panelScenes.GameOverPanel;
import com.comp2042.panelScenes.NotificationPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private Text scoreValue;

    private InputEventListener eventListener;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private GameView gameView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        setupKeyEventHandler();
        gameOverPanel.setVisible(false);

        gameView = new GameView(gamePanel, brickPanel, scoreValue);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    private void setupKeyEventHandler() {
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                setupBrickMovementEventHandler(keyEvent);
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    pauseGame(null);
                }
            }
        });
    }

    private void setupBrickMovementEventHandler(KeyEvent keyEvent) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                gameView.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                gameView.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                gameView.refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                keyEvent.consume();
            }
        }
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gameView.initializeBoard(boardMatrix);
        gameView.initializeBrick(brick);
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            MovingDownData movingDownData = eventListener.onDownEvent(event);
            if (movingDownData.getClearRow() != null && movingDownData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + movingDownData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            gameView.refreshBrick(movingDownData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        if(isPause.get()) {
            timeLine.play();
        } else {
            timeLine.stop();
        }
        isPause.set(!isPause.get());
        gamePanel.requestFocus();
    }

    // getter for GameController to access GameView
    public GameView getGameView() {
        return gameView;
    }
}
