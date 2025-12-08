package com.comp2042.model.brickShapeGenerator;

/**
 * Defines the contract for any class responsible for generating and managing Tetris bricks.
 * It provides methods to retrieve the next available brick and to peek at the subsequent brick
 * for preview purposes.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Creator Interface (Factory Method)**: This interface declares the method(s) for creating or retrieving product objects ({@code Brick}s), allowing subclasses to define the actual generation logic.</li>
 * </ul>
 */
public interface BrickGenerator {

    /**
     * Retrieves and removes the next available brick from the generator's queue,
     * typically triggering the creation or retrieval of a subsequent piece to maintain the supply.
     *
     * @return The next {@code Brick} object to be spawned into the game board.
     */
    Brick getBrick();

    /**
     * Retrieves the shape of the brick coming after the one returned by {@code getBrick()},
     * without removing it from the internal queue (preview function).
     *
     * @return The {@code Brick} object next in line.
     */
    Brick getNextBrick();
}