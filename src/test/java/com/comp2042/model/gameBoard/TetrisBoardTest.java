package com.comp2042.model.gameBoard;

import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.view.data.ViewData;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.Arrays.deepEquals;
import static org.junit.jupiter.api.Assertions.*;

class TetrisBoardTest {

    // Initialize the JavaFX platform once before all tests to prevent NullPointerException with GameTime/Timeline
    @BeforeAll
    static void startJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
        }
    }

    private TetrisBoard board;

    @BeforeEach
    void setUp() {
        board = new TetrisBoard(10,25);
        board.createNewBrick();
    }

    private void clearBoard() {
        int[][] matrix = board.getBoardMatrix();
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }
    }

    @Test
    void testMoveBrickDown_byOneWhenNoCollision() {

        // Get initial X and Y position
        ViewData beforeMove = board.getViewData();
        int initialX = beforeMove.getxPosition();
        int initialY = beforeMove.getyPosition();

        // Act
        boolean moved = board.moveBrickDown();

        // Get new X and Y position
        ViewData afterMove = board.getViewData();
        int newX = afterMove.getxPosition();
        int newY = afterMove.getyPosition();

        // Ensure brick falls within the blue box
        assertTrue(moved, "Brick is not moving down successfully");
        // Ensure X position is always constant
        assertEquals(initialX, newX, "Brick X position should not change");
        // Ensure Y position only increases by one
        assertEquals(initialY + 1, newY, "Brick Y offset should increase by 1");
    }

    @Test
    void testMoveBrickDown_returnsFalseWhenCollision() {

        // Move brick down until it can’t move anymore
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickDown();
        }

        // Act: try moving once more
        boolean result = board.moveBrickDown();

        // Assert
        assertFalse(result, "Brick should not move further when blocked");
    }

    @Test
    void testMoveBrickRight_byOneWhenNoCollision() {

        // Get initial X and Y position
        ViewData beforeMove = board.getViewData();
        int initialX = beforeMove.getxPosition();
        int initialY = beforeMove.getyPosition();

        // Act
        boolean moved = board.moveBrickRight();

        // Get new X and Y position
        ViewData afterMove = board.getViewData();
        int newX = afterMove.getxPosition();
        int newY = afterMove.getyPosition();

        // Ensure brick falls within the blue box
        assertTrue(moved, "Brick is not moving down successfully");
        // Ensure X position is always constant
        assertEquals(initialX + 1, newX, "Brick X offset should increase by 1");
        // Ensure Y position only increases by one
        assertEquals(initialY, newY, "Brick Y offset should not change");
    }

    @Test
    void testMoveBrickRight_returnsFalseWhenCollision() {

        // Move brick down until it can’t move anymore
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickRight();
        }

        // Act: try moving once more
        boolean result = board.moveBrickRight();

        // Assert
        assertFalse(result, "Brick should not move further when blocked");
    }

    @Test
    void testMoveBrickLeft_byOneWhenNoCollision() {

        // Get initial X and Y position
        ViewData beforeMove = board.getViewData();
        int initialX = beforeMove.getxPosition();
        int initialY = beforeMove.getyPosition();

        // Act
        boolean moved = board.moveBrickLeft();

        // Get new X and Y position
        ViewData afterMove = board.getViewData();
        int newX = afterMove.getxPosition();
        int newY = afterMove.getyPosition();

        // Assert
        // Ensure brick falls within the blue box
        assertTrue(moved, "Brick is not moving down successfully");
        // Ensure X position is always constant
        assertEquals(initialX - 1, newX, "Brick X position should decrease by 1");
        // Ensure Y position only increases by one
        assertEquals(initialY, newY, "Brick Y offset should not change");
    }

    @Test
    void testMoveBrickLeft_returnsFalseWhenCollision() {

        // Move brick down until it can’t move anymore
        boolean moved = true;
        while (moved) {
            moved = board.moveBrickLeft();
        }

        // Act: try moving once more
        boolean result = board.moveBrickLeft();

        // Assert
        assertFalse(result, "Brick should not move further when blocked");
    }

    @Test
    void testRotateLeftSuccessWhenNoCollision() {
        clearBoard();

        // Create a new brick in free space
        board.createNewBrick();

        // Act
        boolean rotated = board.rotateBrickLeft();

        // Assert: rotation should succeed
        assertTrue(rotated, "Brick should be able to rotate when there isn't collision");
    }

    @Test
    void testRotateLeftNotPossibleWhenCollision() {
        int[][] matrix = board.getBoardMatrix();
        for(int y = 0;  y < 4; y++) {
            for (int x = 0; x < 10; x++) {
                if (y >= 2) {
                    matrix[y][x] = 1;
                }
            }
        }
        board.createNewBrick();
        boolean rotated = board.rotateBrickLeft();
        assertFalse(rotated, "Brick shouldn't rotate if there is collision");
    }

    @Test
    void testCreatesBrick() {
        // Act
        board.createNewBrick();

        // Assert
        ViewData view = board.getViewData();

        assertNotNull(view, "ViewData should not be null");
        assertNotNull(view.getBrickData(), "Brick should be created");
    }

    @Test
    void testCreateNewBrickShouldNotCollide() {
        clearBoard();

        boolean collided = board.createNewBrick();

        assertFalse(collided, "New brick should not collide on an empty board");
    }

    @Test
    void testCreateNewBrickShouldCollide() {
        int[][] matrix = board.getBoardMatrix();
        for (int i = 0; i < 25; i++) {
            matrix[i][4] = 1;
            matrix[i][5] = 1;
            matrix[i][6] = 1;
        }
        boolean collided = board.createNewBrick();
        assertTrue(collided, "New brick should collide when there is another brick at spawn position");
    }

    @Test
    void testMergeBrickToBackground() {

        // Get pre-merge brick and position
        ViewData before = board.getViewData();
        int[][] brick = before.getBrickData();
        int x = before.getxPosition();
        int y = before.getyPosition();

        // Build expected matrix
        int[][] expected = board.getBoardMatrix();
        for (int row = 0; row < brick.length; row++) {
            for (int col = 0; col < brick[0].length; col++) {
                if (brick[row][col] != 0) {
                    expected[y + row][x + col] = brick[row][col];
                }
            }
        }

        // Act
        board.mergeBrickToBackground();

        // Capture matrix after merge
        int[][] matrixAfter = board.getBoardMatrix();

        // Assert
        assertTrue(deepEquals(expected, matrixAfter),
                "Brick should be merged into the background matrix");
    }

    @Test
    void testClearFullRow() {
        int[][] matrix = board.getBoardMatrix();
        for (int col = 0; col < 10; col++) {
            matrix[19][col] = 1;
        }
        ClearFullRow result = board.clearRows();
        assertEquals(1, result.getLinesRemoved());
        int[][] updated = board.getBoardMatrix();
        for (int col = 0; col < 10; col++) {
            assertEquals(0, updated[19][col]);
        }
    }

    @Test
    void testNewGameResetsGameBoard() {
        // Simulate non-empty board
        board.getBoardMatrix()[0][0] = 5;

        // Reset the game
        board.newGame();

        // Verify matrix is reset
        for (int[] row : board.getBoardMatrix()) {
            for (int cell : row) {
                assertEquals(0, cell, "A new game should reset to an empty game board");
            }
        }
    }

    @Test
    void testScoreResetsInNewGame() {
        board.getScore().add(50);

        // Act: start a new game
        board.newGame();

        // Assert: score is reset
        assertEquals(0, board.getScore().scoreProperty().get(), "Score should reset when a new game happens");
    }

    @Test
    void testHardDrop_MovesBrickToShadowPositionAndLands() {
        // Arrange: Clear the board to ensure max drop distance for predictable landing
        clearBoard();
        board.createNewBrick(); // Spawn a piece at (4, 1)

        // Get the expected landing position (shadow position)
        int expectedY = board.getViewData().getShadowYPosition();

        // Act: Simulate Hard Drop logic (repeated move down until collision)
        // This simulates the behavior found in GameController.onSlamEvent()
        int hardDropMoves = 0;
        while (board.moveBrickDown()) {
            hardDropMoves++;
        }

        // Act: Merge the brick after it lands
        board.mergeBrickToBackground();

        // The piece moved a substantial distance
        assertTrue(hardDropMoves > 0, "Hard drop should have resulted in multiple moves down.");

        // The bottom row of the board matrix must contain blocks,
        // confirming the piece landed at the maximum depth on an empty board.
        int[][] boardMatrix = board.getBoardMatrix();
        boolean blocksFoundAtBottom = false;
        // Check the row where the piece should have landed based on its shadow position
        // We only check rows from the shadowY position downwards to avoid checking rows that are above the piece
        for(int row = expectedY; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[0].length; col++) {
                if (boardMatrix[row][col] != 0) {
                    blocksFoundAtBottom = true;
                    break;
                }
            }
            if (blocksFoundAtBottom) break;
        }

        assertTrue(blocksFoundAtBottom, "Piece should have landed and merged at the correct depth on the empty board.");

        // The action automatically triggers creation of a new brick
        ViewData finalState = board.getViewData();
        assertNotNull(finalState.getBrickData(), "A new brick should be ready after the previous one lands and merges.");
    }

    @Test
    void testRisingRow_ImplementationByLevel() {
        // Arrange
        clearBoard();
        int initialY = board.getViewData().getyPosition(); // Current brick Y position should be 1
        int boardHeight = board.getBoardMatrix().length;

        // Level 1 (Expected 1 hole)
        board.getLevelUp().checkAndAdvance(0); // Ensure level 1
        board.addRisingRow(board.getLevelUp().getLevel());

        // Board shifted up (Row 0 should now be Row 1)
        assertEquals(initialY - 1, board.getViewData().getyPosition(), "Brick Y position should move up by 1.");

        // Check new bottom row for content
        int holeCountLevel1 = (int) Arrays.stream(board.getBoardMatrix()[boardHeight - 1]).filter(cell -> cell == 0).count();
        int garbageCountLevel1 = (int) Arrays.stream(board.getBoardMatrix()[boardHeight - 1]).filter(cell -> cell == 8).count();
        assertEquals(1, holeCountLevel1, "Level 1 rising row must have exactly 1 hole.");
        assertEquals(10 - 1, garbageCountLevel1, "Level 1 rising row must have 9 garbage blocks.");

        // Level 2 (Expected 2 holes)
        board.getLevelUp().checkAndAdvance(10); // Advance to Level 2
        board.addRisingRow(board.getLevelUp().getLevel());

        // Board shifted up again
        assertEquals(initialY - 2, board.getViewData().getyPosition(), "Brick Y position should move up by 1 again.");

        // Check new bottom row (row 19) for content (should be color 8 garbage blocks with 2 holes)
        int holeCountLevel2 = (int) Arrays.stream(board.getBoardMatrix()[boardHeight - 1]).filter(cell -> cell == 0).count();
        assertEquals(2, holeCountLevel2, "Level 2 rising row must have exactly 2 holes.");

        // Level 5 (Maximum expected 3 holes)
        board.getLevelUp().checkAndAdvance(30); // Advance to Level 5
        assertEquals(3, board.getLevelUp().getLevel(), "Must be at Level 5 before adding row.");
        board.addRisingRow(board.getLevelUp().getLevel());

        // Board shifted up again
        assertEquals(initialY - 3, board.getViewData().getyPosition(), "Brick Y position should move up by 1 again.");

        // Check new bottom row (row 19) for content (should be color 8 garbage blocks with 3 holes)
        int holeCountLevel5 = (int) Arrays.stream(board.getBoardMatrix()[boardHeight - 1]).filter(cell -> cell == 0).count();
        // The implementation caps the number of holes to 3 for levels > 3
        assertEquals(3, holeCountLevel5, "Level 5 rising row must have the capped maximum of 3 holes.");
    }
}