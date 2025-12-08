package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.CountClearedRows;
import com.comp2042.model.logic.GameTime;
import com.comp2042.model.logic.LevelUp;
import com.comp2042.model.logic.Score;
import com.comp2042.view.data.ViewData;

/**
 * Defines the contract for read-only access to all critical game state information
 * and statistics.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Interface Segregation Principle (ISP)**: Provides retrieval methods exclusively,
 * allowing external components (like the GUI) to access data without having the ability
 * to mutate the game state.</li>
 * </ul>
 */
public interface GameStats {
    /**
     * Retrieves the current 2D integer matrix representing the fixed blocks on the game board.
     *
     * @return The game board matrix.
     */
    int[][] getBoardMatrix();

    /**
     * Retrieves a container object holding the transient information needed for rendering
     * the current falling brick, its ghost piece, the next brick, and the held brick.
     *
     * @return The {@code ViewData} object.
     */
    ViewData getViewData();

    /**
     * Retrieves the object managing the player's score.
     *
     * @return The {@code Score} object.
     */
    Score getScore();

    /**
     * Retrieves the object tracking the elapsed time of the current game.
     *
     * @return The {@code GameTime} object.
     */
    GameTime getGameTime();

    /**
     * Retrieves the object tracking the total number of lines cleared by the player.
     *
     * @return The {@code CountClearedRows} object.
     */
    CountClearedRows getCountRows();

    /**
     * Retrieves the object managing the current game level and advancement logic.
     *
     * @return The {@code LevelUp} object.
     */
    LevelUp getLevelUp();
}