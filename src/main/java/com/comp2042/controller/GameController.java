package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.gameBoard.Board;
import com.comp2042.model.gameBoard.TetrisBoard;
import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.view.data.ViewData;

public class GameController implements InputEventListener {

    private Board board = new TetrisBoard(10, 25);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.setGameTimer(board.getGameTime());
        viewGuiController.initializeGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindTimer(board.getGameTime().timeStringProperty());
        board.newGame();
    }

    @Override
    public MovingDownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearFullRow clearFullRow = null;

        if (!canMove) {
            // if brick cannot move further down, call the handleBrickCannotMove method to determine if brick placement
            // fills row hence deletion or game over
            clearFullRow = handleBrickLandingTasks();

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
            viewGuiController.gameOver();
        }

        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        return clearFullRow;
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
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
