package com.comp2042.gameBoard;

import com.comp2042.gameLogic.ClearRow;
import com.comp2042.gameLogic.Score;
import com.comp2042.gameLogic.ViewData;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();
}
