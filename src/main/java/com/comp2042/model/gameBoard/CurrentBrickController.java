package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.BrickRotator;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.NextShapeInfo;

import java.awt.*;

public class CurrentBrickController {

    private Point currentOffset;
    private final BrickRotator brickRotator;
    private final TetrisBoard board;

    public CurrentBrickController(BrickRotator brickRotator, TetrisBoard board) {
        this.brickRotator = brickRotator;
        this.board = board;
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

}
