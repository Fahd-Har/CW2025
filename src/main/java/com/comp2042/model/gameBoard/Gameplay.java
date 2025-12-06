package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.ClearFullRow;

public interface Gameplay {
    void mergeBrickToBackground();

    ClearFullRow clearRows();

    void addRisingRow(int level);

    boolean createNewBrick();

    void newGame();
}
