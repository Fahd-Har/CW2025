package com.comp2042.model.brickShapeGenerator;

import com.comp2042.model.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Z-shaped Tetris brick.
 * This brick has 2 rotation states and uses color code 7.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Concrete Product (Factory Method)**: Implements the {@code Brick} interface.</li>
 * </ul>
 */
final class ZBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs the ZBrick and initializes its two rotation matrices.
     */
    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Returns a deep copy of the list containing the Z-brick's rotation matrices.
     *
     * @return A list of 2D integer arrays (the shape matrix in all rotations).
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}