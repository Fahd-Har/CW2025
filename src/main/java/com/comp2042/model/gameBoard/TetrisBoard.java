package com.comp2042.model.gameBoard;

import com.comp2042.model.brickShapeGenerator.Brick;
import com.comp2042.model.brickShapeGenerator.BrickGenerator;
import com.comp2042.model.brickShapeGenerator.RandomBrickGenerator;
import com.comp2042.model.logic.BrickRotator;
import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.Score;
import com.comp2042.view.data.ViewData;

public class TetrisBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private final Score score;
    private final CurrentBrickController brickController;

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        this.brickController = new CurrentBrickController(brickRotator, this);
    }

    @Override
    public boolean moveBrickDown() {
        return brickController.moveDown();
    }

    @Override
    public boolean moveBrickLeft() {
        return brickController.moveLeft();
    }

    @Override
    public boolean moveBrickRight() {
        return brickController.moveRight();
    }

    @Override
    public boolean rotateBrickLeft() {
        return brickController.rotateLeft();
    }

    @Override
    public boolean createNewBrick() {
        // randomly generate one of the 7 brick shapes
        Brick currentBrick = brickGenerator.getBrick();
        // set the newly generated brick as the active brick for rotation and movement
        brickRotator.setBrick(currentBrick);
        // manually set the starting position of the new brick
        brickController.setInitialPosition(4, 1);
        // Check if the new brick immediately conflicts with existing blocks in the game matrix
        // If this returns true, means the game is over
        return MatrixOperations.intersect(currentGameMatrix, brickController.getCurrentShape(), brickController.getX(), brickController.getY());
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
        return new ViewData(brickController.getCurrentShape(),
                brickController.getX(),
                brickController.getY(),
                brickGenerator.getNextBrick().getShapeMatrix().get(0),
                brickController.getShadowPosition().x,
                brickController.getShadowPosition().y);
    }

    @Override
    public void mergeBrickToBackground() {
        // merge the current falling brick into the background game matrix so it becomes a fixed part of the board
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickController.getCurrentShape(), brickController.getX(), brickController.getY());
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
