package com.comp2042.model.gameBoard;

import com.comp2042.model.brickShapeGenerator.Brick;
import com.comp2042.model.brickShapeGenerator.BrickGenerator;
import com.comp2042.model.brickShapeGenerator.RandomBrickGenerator;
import com.comp2042.model.logic.*;
import com.comp2042.view.data.ViewData;

/**
 * The core model class representing the Tetris game board and state.
 * It implements the composite {@code Board} interface, which delegates to the
 * segregated interfaces: {@code BrickMovement}, {@code Gameplay}, and {@code GameStats}.
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Composition**: Delegates brick movement logic to the {@code CurrentBrickController} to uphold SRP.</li>
 * </ul>
 */
public class TetrisBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private final Score score;
    private final CurrentBrickController brickController;
    private final GameTime gameTime;
    private final CountClearedRows countRows;
    private final LevelUp levelUp;

    /**
     * Constructs the TetrisBoard, initializing the game matrix and all supporting components
     * (generators, controllers, and stats trackers).
     *
     * @param width The width of the game board (number of columns).
     * @param height The height of the game board (number of rows).
     */
    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        this.brickController = new CurrentBrickController(brickRotator, this, brickGenerator);
        gameTime = new GameTime();
        this.countRows = new CountClearedRows();
        this.levelUp = new LevelUp();
    }

    /**
     * @see BrickMovement#moveBrickDown()
     */
    @Override
    public boolean moveBrickDown() {
        return brickController.moveDown();
    }

    /**
     * @see BrickMovement#moveBrickLeft()
     */
    @Override
    public boolean moveBrickLeft() {
        return brickController.moveLeft();
    }

    /**
     * @see BrickMovement#moveBrickRight()
     */
    @Override
    public boolean moveBrickRight() {
        return brickController.moveRight();
    }

    /**
     * @see BrickMovement#rotateBrickLeft()
     */
    @Override
    public boolean rotateBrickLeft() {
        return brickController.rotateLeft();
    }

    /**
     * @see BrickMovement#holdBrick()
     */
    @Override
    public void holdBrick() { // ADDED Implementation
        brickController.attemptHold();
    }

    /**
     * Generates a new random brick, spawns it at (4, 1), resets the hold-swap state,
     * and checks for immediate game over.
     *
     * @return true if the new brick immediately collides (Game Over), false otherwise.
     * @see Gameplay#createNewBrick()
     */
    @Override
    public boolean createNewBrick() {
        // randomly generate one of the 7 brick shapes
        Brick currentBrick = brickGenerator.getBrick();
        //Reset hold-swap state for the new brick/turn
        brickController.setSwapped();
        // set the newly generated brick as the active brick for rotation and movement
        brickRotator.setBrick(currentBrick);
        // manually set the starting position of the new brick
        brickController.setInitialPosition(4, 1);
        // Check if the new brick immediately conflicts with existing blocks in the game matrix
        // If this returns true, means the game is over
        return MatrixOperations.intersect(currentGameMatrix, brickController.getCurrentShape(), brickController.getX(), brickController.getY());
    }

    /**
     * Implements the Rising Row feature.
     * <ol>
     * <li>Determines the number of holes (max 3) based on the current level.</li>
     * <li>Uses {@code MatrixOperations.implementRisingRow} to add a garbage row at the bottom and shift all other rows up.</li>
     * <li>Moves the current falling brick up by 1 position to maintain its relative position to the board.</li>
     * </ol>
     *
     * @param level The current game level, which determines the complexity (number of holes)
     * of the rising row.
     */
    @Override
    public void addRisingRow(int level) {
        int numHoles = level;
        if (numHoles > 3) {
            numHoles = 3;
        }
        // 1. Add the row to the bottom of the matrix, shifting all rows up.
        currentGameMatrix = MatrixOperations.implementRisingRow(currentGameMatrix, numHoles);

        // 2. Move the current falling brick up by 1 position.
        brickController.moveUp(1);
    }

    /**
     * @see Gameplay#mergeBrickToBackground()
     */
    @Override
    public void mergeBrickToBackground() {
        // merge the current falling brick into the background game matrix so it becomes a fixed part of the board
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickController.getCurrentShape(), brickController.getX(), brickController.getY());
    }

    /**
     * @see Gameplay#clearRows()
     */
    @Override
    public ClearFullRow clearRows() {
        // Check for any complete rows and remove them if found
        ClearFullRow clearFullRow = MatrixOperations.checkRemoving(currentGameMatrix);
        // Update the game matrix with the new matrix after clearing rows
        currentGameMatrix = clearFullRow.getNewMatrix();
        countRows.add(clearFullRow.getLinesRemoved());
        levelUp.checkAndAdvance(clearFullRow.getLinesRemoved());
        // Return information about the cleared rows
        return clearFullRow;
    }

    /**
     * @see Gameplay#newGame()
     */
    @Override
    public void newGame() {
        resetStats();
        gameTime.start();
        // Create and spawn a new brick to start the game
        createNewBrick();
    }

    /**
     * Resets all mutable game state variables in preparation for a new game.
     * This includes clearing the matrix, score, timer, level, and held brick.
     */
    private void resetStats() {
        // Reset the game board by creating a new empty matrix
        currentGameMatrix = new int[height][width];
        // Reset the player's score
        score.reset();
        gameTime.reset();
        countRows.reset();
        levelUp.reset();
        brickController.setSwapped();
        brickController.resetHold();
    }

    /**
     * Compiles all necessary rendering data into a single {@code ViewData} object for the GUI.
     * This includes the current brick's shape and position, its shadow position, the next brick's
     * shape from the generator, and the held brick's shape.
     *
     * @return A {@code ViewData} object with the current game view state.
     * @see GameStats#getViewData()
     */
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
                brickGenerator.getNextBrick().getShapeMatrix().getFirst(),
                brickController.getShadowPosition().x,
                brickController.getShadowPosition().y,
                brickController.getHeldBrickShape());
    }

    /**
     * @see GameStats#getBoardMatrix()
     */
    @Override
    public int[][] getBoardMatrix() {
        // Return the current state of the game board matrix
        return currentGameMatrix;
    }

    /**
     * @see GameStats#getScore()
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * @see GameStats#getGameTime()
     */
    @Override
    public GameTime getGameTime() {
        return gameTime;
    }

    /**
     * @see GameStats#getCountRows()
     */
    @Override
    public CountClearedRows getCountRows() {
        return countRows;
    }

    /**
     * @see GameStats#getLevelUp()
     */
    @Override
    public LevelUp getLevelUp() {
        return levelUp;
    }
}
