package com.comp2042.model.logic;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.util.Arrays.deepEquals;

class MatrixOperationsTest {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 25;
    private final int GARBAGE_COLOR = 8;
    private final int BOTTOM_ROW_INDEX = BOARD_HEIGHT - 1; // 24
    private final int SECOND_TO_BOTTOM_ROW_INDEX = BOARD_HEIGHT - 2; // 23


    @Test
    void testCheckRemoving_RemovesSingleFullRowAndShifts() {
        // Arrange
        int[][] matrix = new int[BOARD_HEIGHT][BOARD_WIDTH];
        // Fill the bottom row - this is the row to be cleared
        Arrays.fill(matrix[BOTTOM_ROW_INDEX], 1);
        // Leave row 23 partially filled - this row should shift to row 24
        matrix[SECOND_TO_BOTTOM_ROW_INDEX][0] = 2;

        // Act
        ClearFullRow result = MatrixOperations.checkRemoving(matrix);

        // Assert
        assertEquals(1, result.getLinesRemoved(), "Should remove exactly 1 line.");
        assertEquals(50, result.getScoreBonus(), "Score should be 50 * 1^2 = 50.");

        int[][] newMatrix = result.getNewMatrix();

        // Assert: Original row 23 content (the '2') should shift down to new row 24
        assertEquals(2, newMatrix[BOTTOM_ROW_INDEX][0], "Partial row content should shift down to the bottom (row 24).");
        assertTrue(Arrays.stream(newMatrix[SECOND_TO_BOTTOM_ROW_INDEX]).allMatch(cell -> cell == 0), "Row 23 should now be empty (shifted down).");
    }

    @Test
    void testCheckRemoving_NoRowsCleared() {
        // Arrange
        int[][] matrix = new int[BOARD_HEIGHT][BOARD_WIDTH];
        matrix[BOTTOM_ROW_INDEX][5] = 1; // Partial bottom row

        // Act
        ClearFullRow result = MatrixOperations.checkRemoving(matrix);

        // Assert
        assertEquals(0, result.getLinesRemoved(), "Should remove 0 lines.");
        assertEquals(0, result.getScoreBonus(), "Score should be 0.");

        int[][] newMatrix = result.getNewMatrix();

        // Assert: Matrix should be unchanged (deepEquals)
        assertTrue(deepEquals(matrix, newMatrix), "Matrix should be identical if no rows are cleared.");
    }

    @Test
    void testImplementRisingRow_CorrectlyShiftsAndAddsGarbage() {
        // Arrange
        int[][] matrix = new int[BOARD_HEIGHT][BOARD_WIDTH];
        matrix[BOTTOM_ROW_INDEX][0] = 1; // Mark bottom-left cell (shifts to row 23)
        matrix[SECOND_TO_BOTTOM_ROW_INDEX][0] = 2; // Mark second-to-bottom-left cell (shifts to row 22)

        // Act: Test with 1 hole expected (simulating Level 1)
        int[][] newMatrix = MatrixOperations.implementRisingRow(matrix, 1);

        // Assert 1: The old bottom row (24) is now at row 23
        assertEquals(1, newMatrix[SECOND_TO_BOTTOM_ROW_INDEX][0], "Original row 24 content (1) should shift up to row 23.");

        // Assert 2: The old second-to-bottom row (23) is now at row 22
        assertEquals(2, newMatrix[BOARD_HEIGHT - 3][0], "Original row 23 content (2) should shift up to row 22.");

        // Assert 3: The top row (0) of the new matrix should be empty (shifted off-screen)
        assertTrue(Arrays.stream(newMatrix[0]).allMatch(cell -> cell == 0), "The top row (0) should be empty/shifted off-screen.");

        // Assert 4: The new bottom row (24) is garbage with 1 hole
        int holeCount = (int) Arrays.stream(newMatrix[BOTTOM_ROW_INDEX]).filter(cell -> cell == 0).count();
        int garbageCount = (int) Arrays.stream(newMatrix[BOTTOM_ROW_INDEX]).filter(cell -> cell == GARBAGE_COLOR).count();
        assertEquals(1, holeCount, "New bottom row must have 1 hole.");
        assertEquals(BOARD_WIDTH - 1, garbageCount, "New bottom row must have 9 garbage blocks.");
    }
}