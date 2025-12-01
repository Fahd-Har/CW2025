package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.*;
import com.comp2042.view.data.ViewData;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickLeft();

    boolean createNewBrick();

    void holdBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearFullRow clearRows();

    Score getScore();

    GameTime getGameTime();

    CountClearedRows getCountRows();

    LevelUp getLevelUp();

    void newGame();
}
