package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.gameBoard.Board;
import com.comp2042.model.gameBoard.TetrisBoard;
import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.model.scoreBoard.HighScoreEntry;
import com.comp2042.model.scoreBoard.HighScoreSaver;
import com.comp2042.view.data.ViewData;

public class GameController implements InputEventListener {

    private Board board = new TetrisBoard(10, 25);

    private final GuiController viewGui;
    private final GameMode gameMode;
    private final HighScoreSaver scoreSaver;

    public GameController(GuiController c, GameMode mode, HighScoreSaver scoreSaver) {
        viewGui = c;
        gameMode = mode;
        this.scoreSaver = scoreSaver;
        board.newGame();
        setupViewsAndBindings(c);
    }

    private void setupViewsAndBindings(GuiController viewGui) {
        this.viewGui.setGameMode(gameMode);
        this.viewGui.setEventListener(this);
        this.viewGui.setGameTimer(board.getGameTime());
        this.viewGui.bindLevel(board.getLevelUp().levelProperty());
        this.viewGui.initializeGameView(board.getBoardMatrix(), board.getViewData());
        this.viewGui.bindScore(board.getScore().scoreProperty());
        this.viewGui.bindTimer(board.getGameTime().timeStringProperty());
        this.viewGui.bindLines(board.getCountRows().countRowsProperty());
    }

    public void onRisingRowEvent() {

        if (gameMode.affectsRisingRows()) {
            int currentLevel = board.getLevelUp().getLevel();

            board.addRisingRow(currentLevel);
            if (MatrixOperations.intersect(
                    board.getBoardMatrix(),
                    board.getViewData().getBrickData(),
                    board.getViewData().getxPosition(),
                    board.getViewData().getyPosition()
            )) {
                board.getGameTime().stop();
                viewGui.gameOver();
                return;
            }
            viewGui.refreshGameBackground(board.getBoardMatrix());
            viewGui.updateView(board.getViewData());
        }
    }
    @Override
    public MovingDownData onDownEvent(MoveEvent event) {
        int previousLevel = board.getLevelUp().getLevel();
        boolean canMove = board.moveBrickDown();
        ClearFullRow clearFullRow = null;

        if (!canMove) {
            // if brick cannot move further down, call the handleBrickCannotMove method to determine if brick placement
            // fills row hence deletion or game over
            clearFullRow = handleBrickLandingTasks();

            // check for level up
            int currentLevel = board.getLevelUp().getLevel();
            if (currentLevel > previousLevel) {
                viewGui.updateGameSpeed(currentLevel);
                viewGui.showLevelUpNotification(currentLevel);
            }

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new MovingDownData(clearFullRow, board.getViewData());
    }

    private ClearFullRow handleBrickLandingTasks() {
        board.mergeBrickToBackground();
        ClearFullRow clearFullRow = board.clearRows();
        // if there exists lines that were removed, update the score
        if (clearFullRow.getLinesRemoved() > 0) {
            board.getScore().add(clearFullRow.getScoreBonus());
        }
        // if there exists a clash with initial spawn of brick, implement game over
        if (board.createNewBrick()) {
            board.getGameTime().stop();
            viewGui.gameOver();
        }

        viewGui.refreshGameBackground(board.getBoardMatrix());
        return clearFullRow;
    }

    // Method to retrieve final game statistics for the scoreboard
    public void getFinalGameStats() {
        int finalScore = board.getScore().scoreProperty().get();
        int finalLevel = board.getLevelUp().getLevel();
        int finalLines = board.getCountRows().countRowsProperty().get();
        String finalTime = board.getGameTime().timeStringProperty().get();

        HighScoreEntry entry = new HighScoreEntry(finalScore, finalLevel, finalLines, finalTime, gameMode);
        scoreSaver.saveScore(entry);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        board.holdBrick();
        return board.getViewData();
    }

    @Override
    public MovingDownData onSlamEvent(MoveEvent event) {
        MovingDownData movingDownData;
        ViewData previousState;
        ViewData currentState = null;

        do {
            previousState = currentState;
            movingDownData = onDownEvent(new MoveEvent(EventType.SLAM, EventSource.USER));
            currentState = movingDownData.getViewData();
        } while (previousState == null || currentState.getyPosition() > previousState.getyPosition());

        return movingDownData;
    }

    @Override
    public void createNewGame() {
        board.newGame();
        viewGui.refreshGameBackground(board.getBoardMatrix());
    }
}
