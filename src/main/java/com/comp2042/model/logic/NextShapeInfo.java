package com.comp2042.model.logic;

/**
 * A final data class used by {@code BrickRotator} to store the matrix and index corresponding
 * to a calculated future rotation state.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs the NextShapeInfo container.
     *
     * @param shape The 2D matrix of the rotation shape.
     * @param position The integer index of that rotation state.
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the shape matrix.
     *
     * @return A deep copy of the shape matrix.
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the index of the rotation state.
     *
     * @return The rotation index.
     */
    public int getPosition() {
        return position;
    }
}