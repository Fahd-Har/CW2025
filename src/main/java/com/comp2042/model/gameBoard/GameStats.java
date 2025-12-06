package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.CountClearedRows;
import com.comp2042.model.logic.GameTime;
import com.comp2042.model.logic.LevelUp;
import com.comp2042.model.logic.Score;
import com.comp2042.view.data.ViewData;

public interface GameStats {
    int[][] getBoardMatrix();

    ViewData getViewData();

    Score getScore();

    GameTime getGameTime();

    CountClearedRows getCountRows();

    LevelUp getLevelUp();
}
