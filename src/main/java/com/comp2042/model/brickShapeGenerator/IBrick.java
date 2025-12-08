package com.comp2042.model.brickShapeGenerator;

import com.comp2042.model.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the I-shaped Tetris brick (Straight or Line piece).
 * This brick has 2 rotation states and uses color code 1.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Concrete Product (Factory Method)**: Implements the {@code Brick} interface to define a specific type of product.</li>
 * </ul>
 */
final class IBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs the IBrick and initializes its two rotation matrices.
     */
    public IBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    /**
     * Returns a deep copy of the list containing the I-brick's rotation matrices.
     *
     * @return A list of 2D integer arrays (the shape matrix in all rotations).
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}