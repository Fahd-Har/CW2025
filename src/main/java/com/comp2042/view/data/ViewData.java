package com.comp2042.view.data;

import com.comp2042.model.logic.MatrixOperations;

/**
 * A final data class that bundles all necessary information for the {@code GameRenderer}
 * to draw the dynamic elements of the game screen (the current falling brick, its shadow,
 * and the preview panels).
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final int shadowXPosition;
    private final int shadowYPosition;
    private final int[][] heldBrickData;

    /**
     * Constructs the ViewData container.
     *
     * @param brickData The 2D matrix of the current falling brick.
     * @param xPosition The X position of the current falling brick.
     * @param yPosition The Y position of the current falling brick.
     * @param nextBrickData The 2D matrix of the next brick.
     * @param shadowXPosition The X position of the ghost piece.
     * @param shadowYPosition The Y position of the ghost piece.
     * @param heldBrickData The 2D matrix of the held brick, or null if none is held.
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int shadowXPosition, int shadowYPosition, int[][] heldBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.shadowXPosition = shadowXPosition;
        this.shadowYPosition = shadowYPosition;
        this.heldBrickData = heldBrickData;
    }

    /**
     * Gets a copy of the current falling brick's data.
     * @return A deep copy of the brick matrix.
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the X position of the current falling brick.
     * @return The X coordinate.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the Y position of the current falling brick.
     * @return The Y coordinate.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets a copy of the next brick's data.
     * @return A deep copy of the next brick matrix.
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Gets the X position of the ghost piece.
     * @return The shadow X coordinate.
     */
    public int getShadowXPosition() {
        return shadowXPosition;
    }

    /**
     * Gets the Y position of the ghost piece.
     * @return The shadow Y coordinate.
     */
    public int getShadowYPosition() {
        return shadowYPosition;
    }

    /**
     * Gets a copy of the held brick's data.
     * @return A deep copy of the held brick matrix, or null if no brick is held.
     */
    public int[][] getHeldBrickData() {
        return heldBrickData != null ? MatrixOperations.copy(heldBrickData) : null;
    }
}