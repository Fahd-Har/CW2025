package com.comp2042.model.brickShapeGenerator;

import com.comp2042.model.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the S-shaped Tetris brick.
 * This brick has 2 rotation states and uses color code 5.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Concrete Product (Factory Method)**: Implements the {@code Brick} interface.</li>
 * </ul>
 */
final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs the SBrick and initializes its two rotation matrices.
     */
    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Returns a deep copy of the list containing the S-brick's rotation matrices.
     *
     * @return A list of 2D integer arrays (the shape matrix in all rotations).
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}