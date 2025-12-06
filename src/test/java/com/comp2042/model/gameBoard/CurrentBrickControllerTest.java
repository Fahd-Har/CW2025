package com.comp2042.model.gameBoard;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.Arrays.deepEquals;
import static org.junit.jupiter.api.Assertions.*;

class CurrentBrickControllerTest {

    @BeforeAll
    static void startJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    private TetrisBoard board;

    @BeforeEach
    void setUp() {
        board = new TetrisBoard(10, 20);
        board.createNewBrick();
    }

    private void clearBoard() {
        int[][] matrix = board.getBoardMatrix();
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }
    }

    @Test
    void testHoldBrick_PerformsSwapAndPreventsDoubleSwap() {
        // 1. Setup: Get initial brick data
        clearBoard();
        board.createNewBrick(); // Spawn a fresh piece
        int[][] initialBrickShape = board.getViewData().getBrickData();

        // 2. First Hold (Swap with null)
        board.holdBrick();

        // Assert: Held piece is the original piece
        assertNotNull(board.getViewData().getHeldBrickData(), "Held brick should not be null after first hold.");
        assertTrue(deepEquals(initialBrickShape, board.getViewData().getHeldBrickData()), "Held brick should be the original piece.");

        // Assert: Current piece is a new, randomly generated one, and its position is reset
        assertFalse(deepEquals(initialBrickShape, board.getViewData().getBrickData()), "Current piece should be different from the initial held piece.");
        assertEquals(4, board.getViewData().getxPosition(), "New brick should spawn at X=4.");
        assertEquals(1, board.getViewData().getyPosition(), "New brick should spawn at Y=1.");

        // 3. Attempt Double Swap in the same turn (should fail due to hasSwapped flag)
        int[][] swappedBrickShape = board.getViewData().getBrickData();
        board.holdBrick();

        // Assert: No change should have occurred (one swap per turn rule)
        assertTrue(deepEquals(swappedBrickShape, board.getViewData().getBrickData()), "Double hold should be prevented. Current piece should not have changed.");

        // 4. End turn (simulate landing, which triggers TetrisBoard.createNewBrick() and resets the swap flag)
        board.mergeBrickToBackground();
        board.clearRows();
        board.createNewBrick();

    }
}