package com.comp2042.controller;

import com.comp2042.controller.keyInput.KeyInputHandler;
import com.comp2042.events.*;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.view.data.ViewData;
import com.comp2042.view.scenes.GameOverPanel;
import javafx.beans.property.IntegerProperty;
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
    @FXML private GridPane nextBrick;
    @FXML private GridPane shadowPanel;

    private InputEventListener eventListener;
    private GameRenderer gameRenderer;
    private GameFlowManager gameFlow;
    private Notifications notification;
    private KeyInputHandler keyHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);

        gameRenderer = new GameRenderer(brickPanel, gamePanel, nextBrick, shadowPanel);
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
        if (gameFlow.isPause().getValue() == Boolean.FALSE && gameFlow.isGameOver().getValue() == Boolean.FALSE) {
            keyHandler.handleMovementKeys(keyEvent, this::moveDown, this::refreshBrick, this::hardDrop);
        }
        keyHandler.handleGlobalKeys(keyEvent);
    }


    public void initializeGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initializeRenderingState(boardMatrix, brick);
        
        gameFlow.createTimeline(() -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameFlow.start();
    }

    private void refreshBrick(ViewData brick) {
        if (gameFlow.isPause().getValue() == Boolean.FALSE) {
            gameRenderer.refreshBrick(brick);
            gameRenderer.generateNextBrickInPreviewPanel(brick.getNextBrickData());
        }
    }

    public void refreshGameBackground(int[][] board) {
        gameRenderer.refreshGameBackground(board);
    }

    private void moveDown(MoveEvent event) {
        if (gameFlow.isPause().getValue() == Boolean.FALSE) {
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
        this.keyHandler = new KeyInputHandler(eventListener, this::newGame, this::pauseGame);
        gameFlow.setEventListener(eventListener);
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
    }

    public void gameOver() {
        gameFlow.gameOver();
    }

    private void newGame(ActionEvent actionEvent) {
        gameFlow.newGame(null);
    }

    private void pauseGame(ActionEvent actionEvent) {
        gameFlow.pauseGame(null);
    }

    public void hardDrop(MoveEvent event) {
        if (gameFlow.isPause().getValue() == Boolean.FALSE) {
            MovingDownData movingDownData;
            ViewData previousState;
            ViewData currentState = null;

            do {
                previousState = currentState;
                movingDownData = eventListener.onDownEvent(event);
                currentState = movingDownData.getViewData();
            } while (previousState == null || currentState.getyPosition() > previousState.getyPosition());

            if (movingDownData.getClearRow() != null && movingDownData.getClearRow().getLinesRemoved() > 0) {
                notification.showScore(movingDownData.getClearRow().getScoreBonus());
            }
            refreshBrick(movingDownData.getViewData());
        }
        gamePanel.requestFocus();
    }
}
