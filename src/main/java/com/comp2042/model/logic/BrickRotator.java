package com.comp2042.model.logic;

import com.comp2042.model.brickShapeGenerator.Brick;

/**
 * Manages the current brick's rotation state by tracking the active rotation index
 * and providing the next shape and position index upon request.
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**State Manager**: Maintains the state (`currentShape` index) of the brick's rotation.</li>
 * </ul>
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Calculates the next rotation index and retrieves the corresponding shape matrix.
     * The rotation index cycles back to 0 when the last rotation state is reached.
     *
     * @return A {@code NextShapeInfo} object containing the shape matrix for the next rotation and its index.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Retrieves the 2D matrix of the currently active brick rotation.
     *
     * @return The current shape matrix.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Retrieves the currently set {@code Brick} object.
     *
     * @return The active brick.
     */
    public Brick getBrick() {
        return brick;
    }

    /**
     * Sets the index of the current rotation shape.
     *
     * @param currentShape The new rotation index.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets a new brick for the rotator to manage and resets the rotation index to 0.
     *
     * @param brick The new brick piece.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
}