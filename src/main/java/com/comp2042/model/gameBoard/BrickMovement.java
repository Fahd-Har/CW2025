package com.comp2042.model.gameBoard;

/**
 * Defines the contract for controlling the movement and manipulation of the current
 * falling Tetris brick.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Interface Segregation Principle (ISP)**: Dedicated solely to actions related to the
 * active brick's position and orientation (move, rotate, hold), separating it from general
 * gameplay mechanics.</li>
 * </ul>
 */
public interface BrickMovement {
    /**
     * Attempts to move the current falling brick down by one unit.
     *
     * @return true if the move was successful (no collision), false otherwise.
     */
    boolean moveBrickDown();

    /**
     * Attempts to move the current falling brick left by one unit.
     *
     * @return true if the move was successful (no collision), false otherwise.
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the current falling brick right by one unit.
     *
     * @return true if the move was successful (no collision), false otherwise.
     */
    boolean moveBrickRight();

    /**
     * Attempts to rotate the current falling brick (typically one step left).
     *
     * @return true if the rotation was successful (no collision), false otherwise.
     */
    boolean rotateBrickLeft();

    /**
     * Attempts to swap the current falling brick with the piece in the hold queue.
     */
    void holdBrick();
}