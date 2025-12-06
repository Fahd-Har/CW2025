package com.comp2042.model.logic;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

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

    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

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

    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

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
