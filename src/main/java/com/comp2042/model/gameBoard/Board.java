package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.model.logic.Score;
import com.comp2042.view.data.ViewData;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickLeft();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearFullRow clearRows();

    Score getScore();

    void newGame();
}
