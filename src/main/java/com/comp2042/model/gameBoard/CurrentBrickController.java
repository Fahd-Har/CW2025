package com.comp2042.model.gameBoard;

import com.comp2042.model.brickShapeGenerator.Brick;
import com.comp2042.model.brickShapeGenerator.BrickGenerator;
import com.comp2042.model.logic.BrickRotator;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.NextShapeInfo;

import java.awt.*;

/**
 * Manages the position, movement, rotation, and hold/swap logic for the *current* * falling Tetris brick, isolating this functionality from the main {@code TetrisBoard}.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Single Responsibility Principle (SRP)**: Isolates all logic related to the active piece's control and state, delegating board interaction to the {@code TetrisBoard} and rotation state to the {@code BrickRotator}.</li>
 * </ul>
 */
public class CurrentBrickController {

    private Point currentOffset;
    private final BrickRotator brickRotator;
    private final TetrisBoard board;
    private final BrickGenerator brickGenerator;
    private Brick heldBrick = null; // ADDED
    private boolean hasSwapped = false; // ADDED

    /**
     * Constructs the CurrentBrickController, requiring dependencies for rotation, board state, and brick generation.
     *
     * @param brickRotator The handler for rotating the current brick shape.
     * @param board The main game board (used to get the matrix for collision checks).
     * @param brickGenerator The generator used to fetch new bricks after a successful hold/swap.
     */
    public CurrentBrickController(BrickRotator brickRotator, TetrisBoard board, BrickGenerator brickGenerator) {
        this.brickRotator = brickRotator;
        this.board = board;
        this.brickGenerator = brickGenerator;
        this.currentOffset = new Point(0, 0);
    }

    /**
     * Sets the initial position of the current brick.
     *
     * @param x The starting X coordinate.
     * @param y The starting Y coordinate.
     */
    public void setInitialPosition(int x, int y) {
        this.currentOffset = new Point(x, y);
    }

    /**
     * Attempts to move the brick down by delegating to {@code offsetMovement} with dy=1.
     *
     * @return true if the move was successful, false otherwise.
     */
    public boolean moveDown() {
        return offsetMovement(0, 1);
    }

    /**
     * Attempts to move the brick left by delegating to {@code offsetMovement} with dx=-1.
     *
     * @return true if the move was successful, false otherwise.
     */
    public boolean moveLeft() {
        return offsetMovement(-1, 0);
    }

    /**
     * Attempts to move the brick right by delegating to {@code offsetMovement} with dx=1.
     *
     * @return true if the move was successful, false otherwise.
     */
    public boolean moveRight() {
        return offsetMovement(1, 0);
    }

    /**
     * Calculates the next rotation state and checks for collision. If safe, the rotation is applied to the {@code BrickRotator}.
     *
     * @return true if the rotation was successful, false otherwise.
     */
    public boolean rotateLeft() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean canRotate = checkConflict(nextShape.getShape(), currentOffset);
        if (canRotate) {
            // update the shape by applying rotation, if can rotate
            brickRotator.setCurrentShape(nextShape.getPosition());
        }
        return canRotate;
    }

    /**
     * Moves the current brick up by a specified number of rows. This is used when a rising row is added from the bottom.
     *
     * @param y The number of rows to move up.
     */
    public void moveUp(int y) {
        currentOffset.translate(0, -y);
    }

    /**
     * The core logic for movement. Calculates the new offset and calls {@code checkConflict}.
     * If the new position is safe, the {@code currentOffset} is updated.
     *
     * @param dx The change in the X coordinate.
     * @param dy The change in the Y coordinate.
     * @return true if the movement was executed, false otherwise.
     */
    private boolean offsetMovement(int dx, int dy) {
        // create a new point/offset based on the current brick position
        Point newOffset = new Point(currentOffset);
        newOffset.translate(dx, dy);
        boolean noConflict = checkConflict(brickRotator.getCurrentShape(), newOffset);
        // return check if moving brick to new position will cause a collision
        if (noConflict) {
            currentOffset = newOffset;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks for collision by delegating to {@code MatrixOperations.intersect}.
     * A result of `true` means NO conflict, as the method returns the negation of `MatrixOperations.intersect`.
     *
     * @param shape The 2D matrix of the brick shape to check.
     * @param newOffset The target position (x, y) for the check.
     * @return true if there is no conflict, false if a collision occurs.
     */
    private boolean checkConflict(int[][] shape, Point newOffset) {
        // create a copy of the current game matrix so that the original matrix will not be modified
        int[][] currentMatrix = board.getBoardMatrix();
        boolean conflict = MatrixOperations.intersect(currentMatrix, shape, (int) newOffset.getX(), (int) newOffset.getY());
        return !conflict;
    }

    /**
     * Gets the current X position of the brick.
     *
     * @return The X coordinate.
     */
    public int getX() {
        return (int) currentOffset.getX();
    }

    /**
     * Gets the current Y position of the brick.
     *
     * @return The Y coordinate.
     */
    public int getY() {
        return (int) currentOffset.getY();
    }

    /**
     * Gets the current rotation matrix of the brick.
     *
     * @return The current brick shape as a 2D integer array.
     */
    public int[][] getCurrentShape() {
        return brickRotator.getCurrentShape();
    }

    /**
     * Calculates the final landing position (shadow position) of the current brick by simulating its fall.
     *
     * @return The Point representing the final landing position of the shadow piece.
     */
    public Point getShadowPosition() {
        Point shadowOffset = new Point(currentOffset);

        // Simulate falling until moving one step down *further* causes a conflict.
        while (checkConflict(brickRotator.getCurrentShape(), new Point(shadowOffset.x, shadowOffset.y + 1))) {
            shadowOffset.translate(0, 1);
        }
        return shadowOffset;
    }

    /**
     * Implements the Hold/Swap functionality.
     * <ol>
     * <li>Prevents swapping if {@code hasSwapped} is true (one swap per turn).</li>
     * <li>Handles the logic for the first hold (hold current, spawn new from queue) or a subsequent swap (swap current with held).</li>
     * <li>Checks for collision at the spawn point (4, 1) with the piece being spawned. If collision occurs, the operation is canceled and state is reverted.</li>
     * </ol>
     */
    public void attemptHold() {
        if (hasSwapped || brickRotator.getBrick() == null) {
            return;
        }

        Brick currentBrick = brickRotator.getBrick();
        Brick brickToSpawn;

        if (heldBrick == null) {
            // 1. Current piece goes to hold.
            heldBrick = currentBrick;
            // 2. New piece comes from the generator's next queue (consuming it).
            brickToSpawn = brickGenerator.getBrick();
        } else {
            // 1. Swap: current piece goes to hold.
            brickToSpawn = heldBrick;
            heldBrick = currentBrick;
        }

        // 3. Check for collision at spawn point (4, 1) with the piece to spawn
        Point spawnOffset = new Point(4, 1);
        int[][] initialShape = brickToSpawn.getShapeMatrix().getFirst();
        if (!checkConflict(initialShape, spawnOffset)) {
            // Cannot spawn the piece without conflict. Cancel the hold/swap.
            if (heldBrick != currentBrick) {
                // If it was a swap, revert the swap in the controller state.
                heldBrick = brickToSpawn;
            }
            // If it was the first hold, the generator advanced, but we just fail the operation.
            return;
        }

        // 4. Perform the actual swap/spawn
        brickRotator.setBrick(brickToSpawn);
        setInitialPosition(spawnOffset.x, spawnOffset.y);
        this.hasSwapped = true;
    }

    /**
     * Gets the shape matrix of the brick currently in the hold queue.
     *
     * @return The 2D matrix of the held brick's first rotation state, or null if no brick is held.
     */
    public int[][] getHeldBrickShape() {
        return heldBrick != null ? heldBrick.getShapeMatrix().getFirst() : null;
    }

    /**
     * Resets the {@code hasSwapped} flag, allowing a new hold/swap in the next turn.
     */
    public void setSwapped() {
        this.hasSwapped = false;
    }

    /**
     * Resets the held brick to null, clearing the hold panel when a new game starts.
     */
    public void resetHold() {
        this.heldBrick = null;
    }
}