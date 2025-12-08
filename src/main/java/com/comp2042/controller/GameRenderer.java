package com.comp2042.controller;

import com.comp2042.view.data.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Handles the low-level rendering and display of all game elements (board, falling brick,
 * ghost piece, and preview panels) onto their respective JavaFX {@code GridPane}s.
 *
 * <p>It abstracts the visual implementation details away from the {@code GuiController}
 * and game logic, supporting the **Single Responsibility Principle (SRP)** by focusing
 * purely on the presentation aspect. This makes it the dedicated
 * **Renderer** component within the larger MVC structure.</p>
 */
public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int MAGIC_NUM = -42;
    public static final int SET_GAMEPANEL_LAYOUT_X = 525;
    public static final int SET_GAMEPANEL_LAYOUT_Y = 147;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    private Rectangle[][] shadowRectangles;

    private final GridPane brickPanel, gamePanel, nextBrick, shadowPanel, holdBrick;

    /**
     * Constructs the GameRenderer by accepting references to all necessary GUI panels.
     *
     * @param brickPanel The panel for the current falling brick.
     * @param gamePanel The main game board panel where fixed blocks are rendered.
     * @param nextBrick The preview panel for the next brick.
     * @param shadowPanel The panel for the ghost piece.
     * @param holdBrick The panel for the held brick.
     */
    public GameRenderer(GridPane brickPanel, GridPane gamePanel, GridPane nextBrick, GridPane shadowPanel, GridPane holdBrick) {
        this.brickPanel = brickPanel;
        this.gamePanel = gamePanel;
        this.nextBrick = nextBrick;
        this.shadowPanel = shadowPanel;
        this.holdBrick = holdBrick;
    }

    /**
     * Initializes the entire rendering state at the start of a new game or view initialization.
     * This includes building all game board rectangles and initial brick shapes.
     *
     * @param boardMatrix The initial state of the game board matrix.
     * @param brick The initial view data of the first falling brick.
     */
    public void initializeRenderingState(int[][] boardMatrix, ViewData brick) {
        initializeGameBoard(boardMatrix);
        initializeBrick(brick);
        initializeShadowBrick(brick);
        updateBrickPosition(brick);
        updateShadowBrickPosition(brick);
        generateNextBrickInPreviewPanel(brick.getNextBrickData());
        generateHoldBrickInPanel(brick.getHeldBrickData());
    }

    /**
     * Initializes the main game board display grid.
     * Creates transparent {@code Rectangle} objects for each cell and adds them to the {@code gamePanel}.
     * Rendering starts from row 2 (`i=2`) to hide the top two rows of the board logic.
     *
     * @param boardMatrix The full matrix of the game board, including the hidden rows.
     */
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

    /**
     * Initializes the visual representation of the current falling brick.
     * Creates {@code Rectangle} objects based on the brick's shape data and adds them to the {@code brickPanel}.
     *
     * @param brick The {@code ViewData} for the current brick.
     */
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

    /**
     * Initializes the visual representation of the ghost piece (shadow brick).
     * Creates semi-transparent, gray {@code Rectangle} objects and adds them to the {@code shadowPanel}.
     *
     * @param brick The {@code ViewData} for the current brick.
     */
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

    /**
     * Refreshes the visual state of the currently falling brick and its ghost piece.
     * This method is called repeatedly during movement and rotation.
     *
     * @param brick The latest {@code ViewData} containing position and shape updates.
     */
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

    /**
     * Refreshes the display of the main game board background, typically after a block lands
     * and is merged or when rows are cleared.
     *
     * @param board The updated game board matrix.
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Updates the screen position (LayoutX and LayoutY) of the current falling brick panel
     * based on its logical X and Y position, applying constants to center it on the screen.
     *
     * @param brick The {@code ViewData} containing the current brick position.
     */
    private void updateBrickPosition(ViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + SET_GAMEPANEL_LAYOUT_X + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(MAGIC_NUM + gamePanel.getLayoutY() + SET_GAMEPANEL_LAYOUT_Y + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
    }

    /**
     * Updates the screen position (LayoutX and LayoutY) of the shadow brick panel
     * based on its logical shadow X and Y position, applying constants to center it on the screen.
     *
     * @param brick The {@code ViewData} containing the shadow brick position.
     */
    private void updateShadowBrickPosition(ViewData brick) {
        shadowPanel.setLayoutX(gamePanel.getLayoutX() + SET_GAMEPANEL_LAYOUT_X + brick.getShadowXPosition() * shadowPanel.getVgap() + brick.getShadowXPosition() * BRICK_SIZE);
        shadowPanel.setLayoutY(MAGIC_NUM + gamePanel.getLayoutY() + SET_GAMEPANEL_LAYOUT_Y + brick.getShadowYPosition() * shadowPanel.getHgap() + brick.getShadowYPosition() * BRICK_SIZE);
    }

    /**
     * Updates the content of the "Next Brick" preview panel.
     *
     * @param nextBrickData The matrix representation of the next falling brick.
     */
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

    /**
     * Updates the content of the "Hold Brick" panel. If no brick is held, the panel is cleared.
     *
     * @param heldBrickData The matrix representation of the held brick, or null.
     */
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

    /**
     * Sets the fill color and styling (arc height/width) for a visible, solid brick block.
     *
     * @param color The integer code representing the brick color.
     * @param rectangle The {@code Rectangle} object to modify.
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Sets the fill color, styling, and opacity for a shadow brick (ghost piece) block.
     *
     * @param colorIndex The integer code representing the brick color (used for shadow color determination).
     * @param rectangle The {@code Rectangle} object to modify.
     */
    private void setShadowRectangleData(int colorIndex, Rectangle rectangle) {
        rectangle.setFill(getShadowColor(colorIndex));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
        rectangle.setOpacity(0.4);
    }

    /**
     * Maps an integer color code to a JavaFX {@code Paint} object for the solid brick fill.
     *
     * @param i The color index (0 for transparent, 1-8 for specific colors).
     * @return The corresponding JavaFX {@code Paint} color.
     */
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

    /**
     * Maps an integer color code to a standardized gray shade for the ghost piece.
     * Empty cells (0) remain transparent.
     *
     * @param i The color index.
     * @return The JavaFX {@code Paint} object for the shadow.
     */
    private Paint getShadowColor(int i) {
        // Case 0 (Empty cell) should still be transparent
        if (i == 0) {
            return Color.TRANSPARENT;
        }

        return new Color(0.4,0.4,0.4,1);
    }
}