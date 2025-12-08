package com.comp2042.model.logic;

/**
 * A final data class that encapsulates the result of a line clearing operation on the game board.
 *
 */
public final class ClearFullRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Constructs a new ClearFullRow object.
     *
     * @param linesRemoved The total number of lines cleared in the operation.
     * @param newMatrix The game matrix after the lines have been removed and blocks shifted down.
     * @param scoreBonus The calculated score bonus for the lines cleared.
     */
    public ClearFullRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of lines that were removed.
     * @return The lines removed count.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the game matrix after the lines have been cleared.
     * @return A deep copy of the new matrix.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus awarded for the cleared lines.
     * @return The score bonus.
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}