package com.comp2042.model.logic;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * A utility class containing static methods for all 2D integer matrix manipulations,
 * including collision detection, merging, line clearing, and deep copying.
 */
public class MatrixOperations {

    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    /**
     * Checks if the falling {@code brick} conflicts with the existing {@code matrix} at the given (x, y) offset.
     * A conflict occurs if any non-zero block in the brick overlaps a non-zero block on the matrix
     * or is placed out of bounds.
     *
     * @param matrix The fixed game board matrix.
     * @param brick The falling brick matrix.
     * @param x The horizontal offset (column index) where the brick starts.
     * @param y The vertical offset (row index) where the brick starts.
     * @return true if an intersection (collision) is found, false otherwise.
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + j;
                int targetY = y + i;
                if (brick[i][j] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given target coordinates fall outside the boundaries of the {@code matrix}.
     *
     * @param matrix The matrix defining the boundaries.
     * @param targetX The target column index.
     * @param targetY The target row index.
     * @return true if the coordinates are out of bounds, false otherwise.
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        return targetX < 0 || targetY >= matrix.length || targetX >= matrix[targetY].length;
    }

    /**
     * Creates and returns a true deep copy of a 2D integer matrix.
     *
     * @param original The 2D integer array to copy.
     * @return A new, independent 2D integer array with the same contents.
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a {@code brick} into the {@code filledFields} (game board) at the given (x, y) offset.
     * This operation returns a new matrix without modifying the original {@code filledFields}.
     *
     * @param filledFields The current fixed game board.
     * @param brick The brick to merge.
     * @param x The starting column index.
     * @param y The starting row index.
     * @return A new matrix representing the board after the merge.
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + j;
                int targetY = y + i;
                if (brick[i][j] != 0) {
                    copy[targetY][targetX] = brick[i][j];
                }
            }
        }
        return copy;
    }

    /**
     * Checks the given {@code matrix} for any full rows, removes them, and shifts the remaining
     * rows down to fill the gaps.
     *
     * <p>A score bonus is calculated as: 50 * (lines removed)^2.</p>
     *
     * @param matrix The game board matrix to check.
     * @return A {@code ClearFullRow} object containing the number of lines removed, the new matrix, and the score bonus.
     */
    public static ClearFullRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearFullRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Performs a deep copy of a list of 2D integer matrices. Used primarily for copying
     * brick rotation matrices to ensure immutability.
     *
     * @param list The list of 2D arrays to copy.
     * @return A new list containing independent deep copies of the matrices.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

    /**
     * Creates a new matrix where all rows are shifted up by one, and a new "garbage" row
     * is added to the bottom.
     *
     * <p>The garbage row is filled with a specific color (8) and a random number of holes
     * (value 0) defined by {@code numHoles}.</p>
     *
     * @param matrix The original game board matrix.
     * @param numHoles The number of empty slots (holes) to place in the rising row.
     * @return The new matrix with the rising row implemented.
     */
    public static int[][] implementRisingRow(final int[][] matrix, int numHoles) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] newMatrix = new int[height][width];

        for (int i = 1; i < height; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i - 1], 0, width);
        }

        int[] risingRow = new int[width];
        int garbageColor = 8;
        Arrays.fill(risingRow, garbageColor);

        int holesPlaced = 0;
        while (holesPlaced < numHoles) {
            int holeIndex = ThreadLocalRandom.current().nextInt(width);

            if (risingRow[holeIndex] != 0) {
                risingRow[holeIndex] = 0;
                holesPlaced++;
            }
        }

        newMatrix[height - 1] = risingRow;

        return newMatrix;
    }

}