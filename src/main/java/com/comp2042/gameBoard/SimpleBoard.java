package com.comp2042.gameBoard;

import com.comp2042.brickGen.Brick;
import com.comp2042.brickGen.BrickGenerator;
import com.comp2042.brickGen.RandomBrickGenerator;
import com.comp2042.gameLogic.*;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        return offsetMovement(0, 1);
    }


    @Override
    public boolean moveBrickLeft() {
        return offsetMovement(-1, 0);
    }

    @Override
    public boolean moveBrickRight() {
        return offsetMovement(1, 0);
    }

    @Override
    public boolean rotateBrickLeft() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean canRotate = checkConflict(nextShape.getShape(), currentOffset, false);
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
        // return check if moving brick to new position will cause a collision
        return checkConflict(brickRotator.getCurrentShape(), newOffset, true);
    }

    private boolean checkConflict(int[][] shape, Point newOffset, boolean updatePosition) {
        // create a copy of the current game matrix so that the original matrix will not be modified
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) newOffset.getX(), (int) newOffset.getY());
        if (conflict) {
            // returns a false if there is a collision
            return false;
        } else {
            // updates the new position of brick to the new point if no collision occurs
            currentOffset = newOffset;
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        // randomly generate one of the 7 brick shapes
        Brick currentBrick = brickGenerator.getBrick();
        // set the newly generated brick as the active brick for rotation and movement
        brickRotator.setBrick(currentBrick);
        // manually set the starting position of the new brick
        currentOffset = new Point(4, 0);
        // Check if the new brick immediately conflicts with existing blocks in the game matrix
        // If this returns true, means the game is over
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        // Return the current state of the game board matrix
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        // create and return view-related data including:
        // current brick shape
        // current brick X position
        // current brick Y position
        // next brick preview shape
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        // merge the current falling brick into the background game matrix so it becomes a fixed part of the board
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearFullRow clearRows() {
        // Check for any complete rows and remove them if found
        ClearFullRow clearFullRow = MatrixOperations.checkRemoving(currentGameMatrix);
        // Update the game matrix with the new matrix after clearing rows
        currentGameMatrix = clearFullRow.getNewMatrix();
        // Return information about the cleared rows
        return clearFullRow;
    }

    @Override
    public Score getScore() {
        // Return the current score object
        return score;
    }

    @Override
    public void newGame() {
        // Reset the game board by creating a new empty matrix
        currentGameMatrix = new int[height][width];
        // Reset the player's score
        score.reset();
        // Create and spawn a new brick to start the game
        createNewBrick();
    }
}
