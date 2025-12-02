package com.comp2042.model.gameBoard;

import com.comp2042.model.brickShapeGenerator.Brick;
import com.comp2042.model.brickShapeGenerator.BrickGenerator;
import com.comp2042.model.logic.BrickRotator;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.NextShapeInfo;

import java.awt.*;

public class CurrentBrickController {

    private Point currentOffset;
    private final BrickRotator brickRotator;
    private final TetrisBoard board;
    private final BrickGenerator brickGenerator;
    private Brick heldBrick = null; // ADDED
    private boolean hasSwapped = false; // ADDED

    public CurrentBrickController(BrickRotator brickRotator, TetrisBoard board, BrickGenerator brickGenerator) {
        this.brickRotator = brickRotator;
        this.board = board;
        this.brickGenerator = brickGenerator;
        this.currentOffset = new Point(0, 0);
    }

    public void setInitialPosition(int x, int y) {
        this.currentOffset = new Point(x, y);
    }

    public boolean moveDown() {
        return offsetMovement(0, 1);
    }

    public boolean moveLeft() {
        return offsetMovement(-1, 0);
    }

    public boolean moveRight() {
        return offsetMovement(1, 0);
    }

    public boolean rotateLeft() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean canRotate = checkConflict(nextShape.getShape(), currentOffset);
        if (canRotate) {
            // update the shape by applying rotation, if can rotate
            brickRotator.setCurrentShape(nextShape.getPosition());
        }
        return canRotate;
    }

    public void moveUp(int y) {
        currentOffset.translate(0, -y);
    }

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

    private boolean checkConflict(int[][] shape, Point newOffset) {
        // create a copy of the current game matrix so that the original matrix will not be modified
        int[][] currentMatrix = board.getBoardMatrix();
        boolean conflict = MatrixOperations.intersect(currentMatrix, shape, (int) newOffset.getX(), (int) newOffset.getY());
        return !conflict;
    }

    // Getter Methods for ViewData

    public int getX() {
        return (int) currentOffset.getX();
    }

    public int getY() {
        return (int) currentOffset.getY();
    }

    public int[][] getCurrentShape() {
        return brickRotator.getCurrentShape();
    }

    public Point getShadowPosition() {
        Point shadowOffset = new Point(currentOffset);

        // Simulate falling until moving one step down *further* causes a conflict.
        while (checkConflict(brickRotator.getCurrentShape(), new Point(shadowOffset.x, shadowOffset.y + 1))) {
            shadowOffset.translate(0, 1);
        }
        return shadowOffset;
    }

    public boolean attemptHold() {
        if (hasSwapped || brickRotator.getBrick() == null) {
            return false;
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
        int[][] initialShape = brickToSpawn.getShapeMatrix().get(0);
        if (!checkConflict(initialShape, spawnOffset)) {
            // Cannot spawn the piece without conflict. Cancel the hold/swap.
            if (heldBrick != currentBrick) {
                // If it was a swap, revert the swap in the controller state.
                heldBrick = brickToSpawn;
            }
            // If it was the first hold, the generator advanced, but we just fail the operation.
            return false;
        }

        // 4. Perform the actual swap/spawn
        brickRotator.setBrick(brickToSpawn);
        setInitialPosition(spawnOffset.x, spawnOffset.y);
        this.hasSwapped = true;

        return true;
    }

    // ADDED: Getter for held brick
    public int[][] getHeldBrickShape() {
        return heldBrick != null ? heldBrick.getShapeMatrix().get(0) : null;
    }

    // ADDED: Setter for hasSwapped (used by TetrisBoard when a new brick is spawned)
    public void setHasSwapped(boolean hasSwapped) {
        this.hasSwapped = hasSwapped;
    }

}
