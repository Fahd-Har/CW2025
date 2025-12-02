package com.comp2042.controller;

import com.comp2042.view.data.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int MAGIC_NUM = -42;
    public static final int SET_GAMEPANEL_LAYOUT_X = 525;
    public static final int SET_GAMEPANEL_LAYOUT_Y = 147;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    private Rectangle[][] shadowRectangles;

    private final GridPane brickPanel, gamePanel, nextBrick, shadowPanel, holdBrick;

    public GameRenderer(GridPane brickPanel, GridPane gamePanel, GridPane nextBrick, GridPane shadowPanel, GridPane holdBrick) {
        this.brickPanel = brickPanel;
        this.gamePanel = gamePanel;
        this.nextBrick = nextBrick;
        this.shadowPanel = shadowPanel;
        this.holdBrick = holdBrick;
    }

    public void initializeRenderingState(int[][] boardMatrix, ViewData brick) {
        initializeGameBoard(boardMatrix);
        initializeBrick(brick);
        initializeShadowBrick(brick);
        updateBrickPosition(brick);
        updateShadowBrickPosition(brick);
        generateNextBrickInPreviewPanel(brick.getNextBrickData());
        generateHoldBrickInPanel(brick.getHeldBrickData());
    }

    private void initializeGameBoard(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }
    }

    private void initializeBrick(ViewData brick) {
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
    }

    private void initializeShadowBrick(ViewData brick) {
        shadowRectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                // Initialize with shadow style
                rectangle.setFill(getShadowColor(brick.getBrickData()[i][j]));
                rectangle.setOpacity(0.4); // Set opacity for the ghost effect
                shadowRectangles[i][j] = rectangle;
                shadowPanel.add(rectangle, j, i);
            }
        }
    }

    public void refreshBrick(ViewData brick) {
        updateBrickPosition(brick);
        updateShadowBrickPosition(brick);
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                setShadowRectangleData(brick.getBrickData()[i][j], shadowRectangles[i][j]);
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void updateBrickPosition(ViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + SET_GAMEPANEL_LAYOUT_X + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(MAGIC_NUM + gamePanel.getLayoutY() + SET_GAMEPANEL_LAYOUT_Y + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
    }

    private void updateShadowBrickPosition(ViewData brick) {
        shadowPanel.setLayoutX(gamePanel.getLayoutX() + SET_GAMEPANEL_LAYOUT_X + brick.getShadowXPosition() * shadowPanel.getVgap() + brick.getShadowXPosition() * BRICK_SIZE);
        shadowPanel.setLayoutY(MAGIC_NUM + gamePanel.getLayoutY() + SET_GAMEPANEL_LAYOUT_Y + brick.getShadowYPosition() * shadowPanel.getHgap() + brick.getShadowYPosition() * BRICK_SIZE);
    }

    public void generateNextBrickInPreviewPanel(int[][] nextBrickData) {
        nextBrick.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick.add(rectangle, j, i);
                }
            }
        }
    }

    public void generateHoldBrickInPanel(int[][] heldBrickData) {
        holdBrick.getChildren().clear();
        if (heldBrickData == null) {
            // No brick is currently held, clear the panel.
            return;
        }

        for (int i = 0; i < heldBrickData.length; i++) {
            for (int j = 0; j < heldBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(heldBrickData[i][j], rectangle);
                if (heldBrickData[i][j] != 0) {
                    holdBrick.add(rectangle, j, i);
                }
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void setShadowRectangleData(int colorIndex, Rectangle rectangle) {
        rectangle.setFill(getShadowColor(colorIndex));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
        rectangle.setOpacity(0.4);
    }

    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            case 8 -> Color.GRAY;
            default -> Color.WHITE;
        };
    }

    private Paint getShadowColor(int i) {
        // Case 0 (Empty cell) should still be transparent
        if (i == 0) {
            return Color.TRANSPARENT;
        }

        return new Color(0.4,0.4,0.4,1);
    }
}
