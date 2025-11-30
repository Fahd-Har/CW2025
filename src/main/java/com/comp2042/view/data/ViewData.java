package com.comp2042.view.data;

import com.comp2042.model.logic.MatrixOperations;

public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final int shadowXPosition;
    private final int shadowYPosition;
    private final int[][] heldBrickData;

    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int shadowXPosition, int shadowYPosition, int[][] heldBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.shadowXPosition = shadowXPosition;
        this.shadowYPosition = shadowYPosition;
        this.heldBrickData = heldBrickData;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    public int getShadowXPosition() {
        return shadowXPosition;
    }

    public int getShadowYPosition() {
        return shadowYPosition;
    }

    public int[][] getHeldBrickData() { // ADDED
        return heldBrickData != null ? MatrixOperations.copy(heldBrickData) : null;
    }
}
