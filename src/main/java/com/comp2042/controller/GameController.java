package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.gameBoard.Board;
import com.comp2042.model.gameBoard.TetrisBoard;
import com.comp2042.model.logic.ClearFullRow;
import com.comp2042.model.logic.GameMode;
import com.comp2042.model.logic.MatrixOperations;
import com.comp2042.model.logic.MovingDownData;
import com.comp2042.model.scoreBoard.HighScoreEntry;
import com.comp2042.model.scoreBoard.HighScoreSaver;
import com.comp2042.view.data.ViewData;

/**
 * The main application controller that acts as the bridge between the view ({@code GuiController})
 * and the model/game logic ({@code Board}).
 *
 * <p>It implements {@code InputEventListener}, positioning it as the **Concrete Observer** in the
 * **Observer pattern**, reacting to all user and system-generated input events (Movement, Rotation,
 * Hold, Slam, and Down events).</p>
 *
 * <p>This class primarily fulfills the **Controller** role in the **Model-View-Controller (MVC)**
 * pattern, orchestrating game state changes (scoring, level-up, game over) by delegating to the {@code Board} model.</p>
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Dependency Inversion Principle (DIP) / Strategy Pattern**: Depends on the {@code HighScoreSaver} interface rather than a concrete class for saving game statistics, allowing the storage mechanism to be swapped without modifying the core game logic.</li>
 * <li>**Single Responsibility Principle (SRP)**: Logic is separated into methods like {@code setupViewsAndBindings()} and {@code handleBrickLandingTasks()}, keeping the constructor and core event handlers focused.</li>
 * </ul>
 */
public class GameController implements InputEventListener {

    private final Board board = new TetrisBoard(10, 25);

    private final GuiController viewGui;
    private final GameMode gameMode;
    private final HighScoreSaver scoreSaver;

    /**
     * Constructs the GameController, initializing the game board and linking it to the GUI.
     *
     * @param viewGui The GUI controller responsible for rendering the game.
     * @param mode The selected difficulty mode for this game session.
     * @param scoreSaver The dependency for saving high scores upon game over.
     */
    public GameController(GuiController viewGui, GameMode mode, HighScoreSaver scoreSaver) {
        this.viewGui = viewGui;
        gameMode = mode;
        this.scoreSaver = scoreSaver;
        board.newGame();
        setupViewsAndBindings();
    }

    /**
     * Configures the {@code GuiController} with game state bindings (score, time, lines, level)
     * and sets this controller as the event listener.
     */
    private void setupViewsAndBindings() {
        this.viewGui.setGameMode(gameMode);
        this.viewGui.setEventListener(this);
        this.viewGui.setGameTimer(board.getGameTime());
        this.viewGui.bindLevel(board.getLevelUp().levelProperty());
        this.viewGui.initializeGameView(board.getBoardMatrix(), board.getViewData());
        this.viewGui.bindScore(board.getScore().scoreProperty());
        this.viewGui.bindTimer(board.getGameTime().timeStringProperty());
        this.viewGui.bindLines(board.getCountRows().countRowsProperty());
    }

    /**
     * Handles the periodic event for adding a rising row from the bottom of the board.
     * This logic only runs if the current {@code gameMode} affects rising rows.
     *
     * <p>Checks for immediate game over if the rising row pushes the current falling brick into a conflict.</p>
     */
    public void onRisingRowEvent() {

        if (gameMode.affectsRisingRows()) {
            int currentLevel = board.getLevelUp().getLevel();

            board.addRisingRow(currentLevel);
            if (MatrixOperations.intersect(
                    board.getBoardMatrix(),
                    board.getViewData().getBrickData(),
                    board.getViewData().getxPosition(),
                    board.getViewData().getyPosition()
            )) {
                board.getGameTime().stop();
                viewGui.gameOver();
                return;
            }
            viewGui.refreshGameBackground(board.getBoardMatrix());
            viewGui.updateView(board.getViewData());
        }
    }

    /**
     * Handles the move down event, triggered by the game loop (THREAD) or soft drop (USER).
     *
     * <p>When a brick lands/cannot move further down, it triggers the landing sequence:
     * merge, clear rows, score update, level-up check, and new brick spawn/game over check.</p>
     *
     * @param event The move event details.
     * @return A {@code MovingDownData} object containing information about cleared rows and the updated view state.
     */
    @Override
    public MovingDownData onDownEvent(MoveEvent event) {
        int previousLevel = board.getLevelUp().getLevel();
        boolean canMove = board.moveBrickDown();
        ClearFullRow clearFullRow = null;

        if (!canMove) {
            // if brick cannot move further down, call the handleBrickCannotMove method to determine if brick placement
            // fills row hence deletion or game over
            clearFullRow = handleBrickLandingTasks();

            // check for level up
            int currentLevel = board.getLevelUp().getLevel();
            if (currentLevel > previousLevel) {
                viewGui.updateGameSpeed(currentLevel);
                viewGui.showLevelUpNotification(currentLevel);
            }

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new MovingDownData(clearFullRow, board.getViewData());
    }

    /**
     * Executes the tasks required when a falling brick lands:
     * 1. Merges the brick to the background matrix.
     * 2. Clears any full rows and adds the bonus score.
     * 3. Attempts to create a new brick and checks for immediate game over.
     *
     * @return A {@code ClearFullRow} object with details on removed lines and score bonus.
     */
    private ClearFullRow handleBrickLandingTasks() {
        board.mergeBrickToBackground();
        ClearFullRow clearFullRow = board.clearRows();
        // if there exists lines that were removed, update the score
        if (clearFullRow.getLinesRemoved() > 0) {
            board.getScore().add(clearFullRow.getScoreBonus());
        }
        // if there exists a clash with initial spawn of brick, implement game over
        if (board.createNewBrick()) {
            board.getGameTime().stop();
            viewGui.gameOver();
        }

        viewGui.refreshGameBackground(board.getBoardMatrix());
        return clearFullRow;
    }

    /**
     * Collects the final game statistics (score, level, lines, time, and GameMode)
     * and delegates the saving process to the injected {@code HighScoreSaver} dependency.
     */
    public void getFinalGameStats() {
        int finalScore = board.getScore().scoreProperty().get();
        int finalLevel = board.getLevelUp().getLevel();
        int finalLines = board.getCountRows().countRowsProperty().get();
        String finalTime = board.getGameTime().timeStringProperty().get();

        HighScoreEntry entry = new HighScoreEntry(finalScore, finalLevel, finalLines, finalTime, gameMode);
        scoreSaver.saveScore(entry);
    }

    /**
     * Handles the move left event, delegating the action to the board.
     * @param event The move event details.
     * @return The updated {@code ViewData}.
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles the move right event, delegating the action to the board.
     * @param event The move event details.
     * @return The updated {@code ViewData}.
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles the rotate event, delegating the action to the board.
     * @param event The move event details.
     * @return The updated {@code ViewData}.
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles the hold event, delegating the swap logic to the board.
     * @param event The move event details.
     * @return The updated {@code ViewData}.
     */
    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        board.holdBrick();
        return board.getViewData();
    }

    /**
     * Handles the hard drop (slam) event by repeatedly executing the {@code onDownEvent}
     * with an event source of USER until the brick can no longer move.
     *
     * @param event The move event details.
     * @return The {@code MovingDownData} resulting from the final down event (after landing).
     */
    @Override
    public MovingDownData onSlamEvent(MoveEvent event) {
        MovingDownData movingDownData;
        ViewData previousState;
        ViewData currentState = null;

        do {
            previousState = currentState;
            movingDownData = onDownEvent(new MoveEvent(EventType.SLAM, EventSource.USER));
            currentState = movingDownData.getViewData();
        } while (previousState == null || currentState.getyPosition() > previousState.getyPosition());

        return movingDownData;
    }

    /**
     * Initiates a new game by resetting the board state and refreshing the GUI background.
     * This is typically called by {@code GameFlowManager.newGame()}.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGui.refreshGameBackground(board.getBoardMatrix());
    }
}