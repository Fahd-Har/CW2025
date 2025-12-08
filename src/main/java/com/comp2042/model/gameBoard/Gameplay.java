package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.ClearFullRow;

/**
 * Defines the contract for core game mechanics and board state modification actions
 * that are separate from simple brick movement.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Interface Segregation Principle (ISP)**: Dedicated solely to structural game actions
 * such as merging, clearing rows, spawning, and resetting the game.</li>
 * </ul>
 */
public interface Gameplay {
    /**
     * Merges the current falling brick into the fixed background matrix.
     */
    void mergeBrickToBackground();

    /**
     * Checks the board for any complete rows, removes them, shifts remaining blocks down,
     * updates the level, and calculates the score bonus.
     *
     * @return A {@code ClearFullRow} object containing the number of lines removed and the new matrix.
     */
    ClearFullRow clearRows();

    /**
     * Adds a new "garbage" row from the bottom, shifting all existing rows (including the
     * current falling brick) up by one unit.
     *
     * @param level The current game level, which determines the complexity (number of holes)
     * of the rising row.
     */
    void addRisingRow(int level);

    /**
     * Generates a new random brick, spawns it at the starting position, and sets it as the
     * active piece.
     *
     * @return true if the newly spawned brick immediately conflicts with existing blocks
     * (indicating Game Over), false otherwise.
     */
    boolean createNewBrick();

    /**
     * Resets the entire game state, including the board matrix, score, timer, and level.
     */
    void newGame();
}