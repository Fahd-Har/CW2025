package com.comp2042.model.brickShapeGenerator;

import com.comp2042.model.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the L-shaped Tetris brick.
 * This brick has 4 rotation states and uses color code 3.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Concrete Product (Factory Method)**: Implements the {@code Brick} interface.</li>
 * </ul>
 */
final class LBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs the LBrick and initializes its four rotation matrices.
     */
    public LBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 3},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 3, 0},
                {0, 0, 3, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Returns a deep copy of the list containing the L-brick's rotation matrices.
     *
     * @return A list of 2D integer arrays (the shape matrix in all rotations).
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}