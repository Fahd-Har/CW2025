package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.gameLogic.MovingDownData;
import com.comp2042.gameLogic.ViewData;
import com.comp2042.panelScenes.GameOverPanel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML private GridPane gamePanel;
    @FXML private Group groupNotification;
    @FXML private GridPane brickPanel;
    @FXML private GameOverPanel gameOverPanel;
    @FXML private Text scoreValue;

    private InputEventListener eventListener;
    private GameRenderer gameRenderer;
    private GameFlowManager gameFlow;
    private Notifications notification;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);

        gameRenderer = new GameRenderer(brickPanel, gamePanel);
        gameFlow = new GameFlowManager(gamePanel, gameOverPanel, null);
        notification = new Notifications(groupNotification);

        initializeKeyControls();
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    private void initializeKeyControls() {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(this::handleAllKeys);
    }

    private void handleAllKeys(KeyEvent keyEvent) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            initializeMovementKeys(keyEvent);
        }
        handleGlobalKeys(keyEvent);
    }

    private void handleGlobalKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case N -> newGame(null);
            case ESCAPE -> pauseGame(null);
        }
    }

    private void initializeMovementKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT, A -> refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
            case RIGHT, D -> refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
            case UP, W -> refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
            case DOWN, S -> moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
        }
        keyEvent.consume();
    }

    public void initializeGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initializeGameBoard(boardMatrix);
        gameRenderer.initializeBrick(brick);
        gameRenderer.updateBrickPosition(brick);
        
        gameFlow.createTimeline(() -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameFlow.start();
    }

    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            gameRenderer.refreshBrick(brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
        gameRenderer.refreshGameBackground(board);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            MovingDownData movingDownData = eventListener.onDownEvent(event);
            if (movingDownData.getClearRow() != null && movingDownData.getClearRow().getLinesRemoved() > 0) {
                notification.showScore(movingDownData.getClearRow().getScoreBonus());
            }
            refreshBrick(movingDownData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
    }

    public void gameOver() {
        gameFlow.gameOver();
    }

    public void newGame(ActionEvent actionEvent) {
        gameFlow.newGame(null);
    }

    public void pauseGame(ActionEvent actionEvent) {
        gameFlow.pauseGame(null);
    }
}
