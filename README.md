# Developing Maintainable Software Coursework

## GitHub

## Compilation Instructions

## Implemented and Working Properly

## Implemented but Not Working Properly

## Features Not Implemented

## New Java Classes
- **CurrentBrickController** Path: src/main/java/com/comp2042/model/gameBoard/CurrentBrickController.java
    - Takes up brick position, movement, and rotation logic from TetrisBoard.
    - Added core Hold Brick logic to manage the heldBrick state and swap the current piece, including a check to prevent multiple swaps per turn.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **GameFlowManager** Path: src/main/java/com/comp2042/controller/GameTimeline.java
    - Takes up the game timeline logic from GuiController
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **GameRenderer** Path: src/main/java/com/comp2042/controller/GameRenderer.java
    - Takes up initialization of game board and bricks logics from GuiController.
    - Generates all related panels for the game layout fxml file.
    - Ensure bricks fall in the middle by adding constants of the LayoutX and LayoutY of gamePanel.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **Notifications** Path: src/main/java/com/comp2042/events/Notifications.java
    - Takes up the logic for all pop-up notifications (+scores, future level ups) from GuiController.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **KeyInputHandler** Path: src/main/java/com/comp2042/controller/keyInput/KeyInputHandler.java
    - Takes up the logic for key inputs and their respective actions from GuiController using consumer.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **MainMenuScreen** Path: src/main/java/com/comp2042/view/scenes/MainMenuScreen.java
    - An enhancement for player to choose whether they want to see the game rules/what each difficulty does (About),
    or straight away choose their difficulty game mode.
    - Maintains a clear separation between the welcome screen, variance of game difficulty and the game itself.

- **GameTime** Path: src/main/java/com/comp2042/model/logic/GameTime.java
    - Initialize game clock as an enhancement for gaming experience using JavaFX's `Timeline()`.
    - Exposes the elapsed time as a bindable `StringProperty` (MM:SS).
    - Implements pause/resume logic by tracking `pauseDuration` to prevent the timer from including time spent while paused, ensuring accurate time keeping.
    - This helps support Single Responsibility Principle (SRP) by dedicating a class purely to time tracking.

- **CountClearedRows** Path: src/main/java/com/comp2042/model/logic/CountClearedRows.java
    - Count the number of lines removed throughout the game as an enhancement for gaming experience.
    - Uses a JavaFX **_IntegerProperty_** to allow for automatic, real-time updates in the GUI.

- **LevelUp** Path: src/main/java/com/comp2042/model/logic/LevelUp.java
    - Manages the game level from level 1.
    - For every 10 lines removed, the game levels up automatically by calling `checkAndAdvance()`.

- **PauseMenu** Path: src/main/java/com/comp2042/view/scenes/PauseMenu.java
    - Handles loading and positioning of the pauseScreen.fxml screen.
    - Manages the visibility of the pause menu panel by binding it directly to the `isPause()` state from the `GameFlowManager` class.

- **ChooseDifficultyScreen** Path: src/main/java/com/comp2042/view/scenes/ChooseDifficultyScreen.java
    - Links the game's UI and logic by retrieving the GuiController from the FXML and using it to initialize the GameController before displaying the game scene.
    - Separates game logic between each game difficulties.

## Modified Java Classes
### File Refactoring

- **Board.java**
    1. Added `getGameTime()` to expose the game's time/clock state.
    2. Added `getCountRows()` to expose the number of rows removed during that state.
    3. Added method `holdBrick()` to hold brick logic and functionality.
    4. Added `getLevelUp()` to expose the game's level state.
    5. Added the method `addRisingRow()` to pass the current game level, allowing the creation of level-dependent holes in the rising row.

- **TetrisBoard.java**
    1. Renamed the class from `SimpleBoard` to `TetrisBoard` for a better description of the class.
    2. In `createNewBrick()`, changed the new offset point coordinate from **(4,10)** to **(4,0)** so new bricks drop from the top of the game board.
    3. Updated the constructor and `newGame()` to initialize the game matrix using new **int[height][width]** instead of
    new **int[width][height]** to correctly map rows to height and columns to width and avoid logical errors.
    4. Renamed variable **p** to **newOffset** for clarity.
    5. Introduced new methods `offsetMovement()` and `checkConflict()` to reduce duplicate code and handle brick movement and collision checks.
    6. Extracted brick position, movement, and rotation logic into a new `CurrentBrickController()` class to support SRP.
    7. Added a `GameTimer` field, initialized it in the constructor, and implemented `getTimer()`.
    8. Integrated the new `countRow` counter, adding to the total count whenever rows are cleared and resetting the counter in `newGame()`.
    9. Implemented the `holdBrick()` method and updated `createNewBrick()` and `newGame()` to manage the swap state and ensure the held piece is exposed via `getViewData()`.
    10. Initialized the LevelUp and modified `clearRows()` to check if next level can be advanced after lines are cleared.
    11. Implemented `getLevelUp()` and update `newGame()` to reset the level state.
    12. Implement the add rising row to show in the Tetris gameboard, and that it moves up by one.


- **MatrixOperations.java**
    1. Updated the `intersect()` and `merge()` methods to correctly align i and j with the x and y coordinates when checking
       brick collisions to improve code logic and clarity.
    2. Created a new method `implementRisingRows()` to initialize the logic for new rows to be pushed from the bottom.
    3. Implement logic on number of holes in the new rising row depends with the current level.


- **GuiController.java**
    1. Created an **if-else** statement in `pauseGame()` and called it in `initialize()` handle method to pause the game
       when the _**'ESCAPE'**_ key is pressed.
    2. Under `bindScore()`, added a line of code to bind the score label to the score property to display the player's score and live updates.
    3. Extracted game timeline/game flow logic (such as `newGame()`, `pauseGame()` and `gameOver()`) into a new `GameFlowManager` class.
    4. Extracted the original long `initialize()` method into smaller methods focusing on key controls.
    5. Renamed the method `initGameView()` to `initializeGameView()`.
    6. Extracted rendering logic of game board and brick into a new class, `GameRenderer`.
    7. Extracted notification logics into a new class, `Notifications()`.
    8. Extracted key input logic into a new class, `KeyInputHandler()`.
    9. Created a new method called `hardDrop()`, to drop the brick instantly.
    10. Calls method from `GameRenderer` class to generate next brick (the brick after the current falling brick) into the next brick panel.
    11. Added a shadow panel code into the FXML.
    12. Added FXML field `@FXML private Text timeValue;` to bind to the clock display.
    13. Added `bindTimer()` to link the time property to the UI.
    14. Added `setGameTimer()` to receive the `GameTimer` instance and pass it to the `GameFlowManager`.
    15. Implemented a new `bindLines()` method to connect the lines counter property to the GUI element, using the format
    specifier `"%03d"` to display a three-digit zero-padded number.
    16. The method `handleMovementKeys()` was renamed to `handleBrickControlKeys()` in both KeyInputHandler.java and GuiController.java
    for improved clarity regarding its responsibility over all in-game brick actions.
    17. Initialized the `PauseMenu` class and called its `loadPauseScreen()` in `initialize().`
    18. Update `pauseGame()` and `newGame()` methods to show the panel when the game pauses, and removes it when game continues or a new game happens.
    19. Added the call for the rising row timeline in the `initializeGameView()` method.
    20. Added a public helper method to call refresh brick method in other classes without calling the actual private method.


- **GameController.java**
    1. Delegate post landing tasks into a new method called `handleBrickLandingTasks()` making the original method,
    `onDownEvent()` clearer and focused only on the move flow.
    2. Extracted all view initialization and binding calls (`setEventListener`, `setGameTimer`, `initializeGameView`,
    `bindScore`, `bindTimer`) into a new private method named `setupViewAndBindings()` to keep the constructor focused on state initialization logic (SRP).
    3. Added logic to call `board.getTimer().stop()` when `handleBrickLandingTasks()` detects a game over condition.
    4. Implemented the new `onHoldEvent()` method to process the Hold Brick request to the C key.
    5. Update logic by removing board.createNewBrick() and place board.newGame() at the top of the constructor to ensure a consistent,
    full game state initialization on launch.
    6. Bound the new LevelUp property to the GUI constructor.
    7. In `onDownEvent()`, after a brick lands and rows are cleared, a check is performed to see if the level increased.
    8. Added `onRisingRowEvent()` to handle the timer tick. This method fetches the current level, and checks for immediate game over, and updates the view.


- **Main.java**
    1. Class now loads a different scene, a main menu screen. This scene does not involve any Gui or game control logic,
    hence the lines involve with this logic was removed. Window screen has now increased to 1280x800 pixels size.


- **ViewData.java**
    1. Create new getters for shadow position of the brick so that alterations to these getters are independent and does
    not affect the positions for actual brick.
    2. Updated the class structure to include the shape data for the brick currently in the hold queue.
  

## Unexpected Problems
