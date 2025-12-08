package com.comp2042.model.brickShapeGenerator;

import java.util.List;

/**
 * Defines the contract for all Tetris brick shapes.
 * Every concrete brick type must implement this interface
 * to provide its shape and rotation matrices.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Product Interface (Factory Method/Abstract Factory)**: This interface defines the product that concrete factory classes (like {@code RandomBrickGenerator}) are responsible for creating.</li>
 * </ul>
 */
public interface Brick {

    /**
     * Returns a list containing all possible rotation states of the brick shape.
     * Each shape is represented as a 2D integer matrix.
     *
     * @return A list of 2D integer arrays representing the brick's shape matrices for all rotations.
     */
    List<int[][]> getShapeMatrix();
}