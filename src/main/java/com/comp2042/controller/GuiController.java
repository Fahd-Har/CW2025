package com.comp2042.controller;

import com.comp2042.controller.keyInput.KeyInputHandler;
import com.comp2042.events.*;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.logic.GameTime;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.view.data.ViewData;
import com.comp2042.view.scenes.GameOverPanel;
import com.comp2042.view.scenes.PauseMenu;
import com.comp2042.view.soundBoard.Sound;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML private GridPane gamePanel;
    @FXML private Group groupNotification;
    @FXML private Group levelUpNotification;
    @FXML private GridPane brickPanel;
    @FXML private GameOverPanel gameOverPanel;
    @FXML private Text scoreValue;
    @FXML private GridPane nextBrick;
    @FXML private GridPane shadowPanel;
    @FXML private Text gameTime;
    @FXML private Text countRowsValue;
    @FXML private GridPane holdBrick;
    @FXML private Text levelValue;
    @FXML private Pane rootPane;

    private InputEventListener eventListener;
    private GameRenderer gameRenderer;
    private GameFlowManager gameFlow;
    private Notifications notification;
    private KeyInputHandler keyHandler;
    private PauseMenu pauseMenu;

    private final Sound bgm = new Sound();
    private final Sound sfx = new Sound();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);

        gameRenderer = new GameRenderer(brickPanel, gamePanel, nextBrick, shadowPanel, holdBrick);
        gameFlow = new GameFlowManager(gamePanel, null);
        notification = new Notifications(groupNotification, levelUpNotification);
        pauseMenu = new PauseMenu(rootPane);
        gameOverPanel = new GameOverPanel(rootPane);

        pauseMenu.loadPauseScreen();

        initializeKeyControls();
        gameOverPanel.loadGameOverPanel();

        bgm.bgMusic();

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
            switch (keyEvent.getCode()) {
                case UP, W -> sfx.soundEffects(1);
            }
            keyHandler.handleBrickControlKeys(keyEvent, this::moveDown, this::refreshBrick, this::hardDrop);
        }
        keyHandler.handleGlobalKeys(keyEvent);
    }

    public void initializeGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initializeRenderingState(boardMatrix, brick);
        
        gameFlow.createTimeline(() -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameFlow.createRisingRowTimeline(this::handleRisingRowAddition);
        gameFlow.start();
    }

    private void handleRisingRowAddition() {
        if (gameFlow.isPause().getValue() == Boolean.FALSE && gameFlow.isGameOver().getValue() == Boolean.FALSE) {
            ((GameController) eventListener).onRisingRowEvent();
        }
    }

    private void refreshBrick(ViewData brick) {
        if (gameFlow.isPause().getValue() == Boolean.FALSE) {
            gameRenderer.refreshBrick(brick);
            gameRenderer.generateNextBrickInPreviewPanel(brick.getNextBrickData());
            gameRenderer.generateHoldBrickInPanel(brick.getHeldBrickData());
        }
    }

    public void updateView(ViewData brick) {
        refreshBrick(brick);
    }

    public void refreshGameBackground(int[][] board) {
        gameRenderer.refreshGameBackground(board);
    }

    private void moveDown(MoveEvent event) {
        if (gameFlow.isPause().getValue() == Boolean.FALSE) {
            MovingDownData movingDownData = eventListener.onDownEvent(event);
            if (movingDownData.getClearRow() != null && movingDownData.getClearRow().getLinesRemoved() > 0) {
                sfx.soundEffects(2);
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

    // New setter to pass GameTimer to GameFlowManager
    public void setGameTimer(GameTime gameTime) {
        gameFlow.setGameTimer(gameTime);
    }

    public void updateGameSpeed(int newLevel) {
        gameFlow.updateSpeed(newLevel);
    }

    public void setGameMode(GameMode gameMode) { // ADDED: Setter to pass mode to GameFlowManager
        if (gameFlow != null) {
            gameFlow.setGameMode(gameMode);
        }
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
    }

    // New method to bind the timer property
    public void bindTimer(StringProperty stringProperty) {
        gameTime.textProperty().bind(stringProperty);
    }

    // New method to bind the counted lines properly
    public void bindLines(IntegerProperty integerProperty) {
        countRowsValue.textProperty().bind(integerProperty.asString("%03d"));
    }

    public void bindLevel(IntegerProperty integerProperty) { // ADDED
        levelValue.textProperty().bind(integerProperty.asString());
    }

    public void gameOver() {
        gameFlow.gameOver();
        gameOverPanel.showPanel(gameFlow);
        bgm.stop();
        sfx.soundEffects(3);
    }

    private void newGame(ActionEvent actionEvent) {
        gameFlow.newGame(null);
        pauseMenu.showPanel(gameFlow);
        gameOverPanel.showPanel(gameFlow);
        gamePanel.requestFocus();
        bgm.bgMusic();
    }

    private void pauseGame(ActionEvent actionEvent) {
        gameFlow.pauseGame(null);

        if (gameFlow.isPause().getValue()) {
            bgm.stop();
        } else {
            bgm.resume();
        }

        pauseMenu.showPanel(gameFlow);
        gamePanel.requestFocus();
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
                sfx.soundEffects(2);
                notification.showScore(movingDownData.getClearRow().getScoreBonus());
            }
            refreshBrick(movingDownData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void showLevelUpNotification(int level) {
        notification.showLevelUp(level);
    }
}
