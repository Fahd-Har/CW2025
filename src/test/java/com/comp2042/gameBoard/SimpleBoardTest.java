package com.comp2042.gameBoard;

import com.comp2042.gameLogic.ClearFullRow;
import com.comp2042.gameLogic.ViewData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.deepEquals;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    private SimpleBoard board;

    @BeforeEach
    void setUp() {
        board = new SimpleBoard(10,20);
        board.createNewBrick();
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

        // Assert
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

        // Assert
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
    void testRotateLeftCollision() {
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
        assertFalse(rotated);
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
        int[][] matrix = board.getBoardMatrix();

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = 0;
            }
        }

        boolean collided = board.createNewBrick();

        assertFalse(collided, "New brick should not collide on an empty board");
    }

    @Test
    void testCreateNewBrickShouldCollide() {
        int[][] matrix = board.getBoardMatrix();
        for (int i = 0; i < 20; i++) {
            matrix[i][2] = 1;
            matrix[i][3] = 1;
            matrix[i][4] = 1;
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
    void testClearRows() {
        int[][] matrix = board.getBoardMatrix();

        matrix[0] = new int[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0};
        matrix[1] = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        ClearFullRow result = board.clearRows();

        int[][] expectedMatrix = new int[20][10];

        expectedMatrix[0] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        expectedMatrix[1] = new int[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0};

        assertArrayEquals(expectedMatrix, board.getBoardMatrix());

        assertEquals(1, result.getLinesRemoved());
    }

    @Test
    void testResetsBrickPositionAndCreatesNewBrick() {
        // Simulate non-empty board
        board.getBoardMatrix()[0][0] = 5;

        // Reset the game
        board.newGame();

        // Verify matrix is reset
        for (int[] row : board.getBoardMatrix()) {
            for (int cell : row) {
                assertEquals(0, cell);
            }
        }

        // Verify score is reset via public getter
        assertEquals(0, board.getScore().scoreProperty().get());
    }

}
