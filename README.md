# Developing Maintainable Software Coursework

## GitHub

## Compilation Instructions

## Implemented and Working Properly

## Implemented but Not Working Properly

## Features Not Implemented

## New Java Classes
- **CurrentBrickController** Path: src/main/java/com/comp2042/gameBoard/CurrentBrickController.java
    - Takes up brick position, movement, and rotation logic from SimpleBoard.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **GameFlowManager** Path: src/main/java/com/comp2042/controller/GameTimeline.java
    - Takes up the game timeline logic from GuiController
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **GameRenderer** Path: src/main/java/com/comp2042/controller/GameRenderer.java
    - Takes up initialization of game board and bricks logics from GuiController
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **Notifications** Path: src/main/java/com/comp2042/controller/Notifications.java
    - Takes up the logic for all pop-up notifications (+scores, future level ups) from GuiController.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.

- **KeyInputHandler** Path: src/main/java/com/comp2042/controller/KeyInputHandler.java
    - Takes up the logic for key inputs and their respective actions from GuiController.
    - This helps support Single Responsibility Principle (SRP) by separating responsibilities.
## Modified Java Classes
### File Refactoring

- **SimpleBoard.java**
    1. In `createNewBrick()`, changed the new offset point coordinate from **(4,10)** to **(4,0)** so new bricks drop from the top of the game board.
    2. Updated the constructor and `newGame()` to initialize the game matrix using new **int[height][width]** instead of
       new **int[width][height]** to correctly map rows to height and columns to width and avoid logical errors.
    3. Renamed variable **p** to **newOffset** for clarity.
    4. Introduced new methods `offsetMovement()` and `checkConflict()` to reduce duplicate code and handle brick movement and collision checks.
    5. Extracted brick position, movement, and rotation logic into a new `CurrentBrickController()` class to support SRP.


- **MatrixOperations.java**
    1. Updated the `intersect()` and `merge()` methods to correctly align i and j with the x and y coordinates when checking
       brick collisions to improve code logic and clarity.


- **GuiController.java**
    1. Created an **if-else** statement in `pauseGame()` and called it in `initialize()` handle method to pause the game
       when the _**'ESCAPE'**_ key is pressed.
    2. Under `bindScore()`, added a line of code to bind the score label to the score property to display the player's score and live updates.
    3. Extracted game timeline/game flow logic (such as `newGame`, `pauseGame` and `gameOver`) into a new `GameFlowManager` class.
    4. Extracted the original long `initialize()` method into smaller methods focusing on key controls.
    5. Renamed the method `initGameView()` to `initializeGameView()`.
    6. Extracted rendering logic of game board and brick into a new class, `GameRenderer()`.
    7. Extracted notification logics into a new class, `Notifications()`.
    8. Extracted key input logic into a new class, `KeyInputHandler()`.


- **GameController.java**
    1. Delegate post landing tasks into a new method called `handleBrickLandingTasks()` making the original method,
    `onDownEvent()` clearer and focused only on the move flow.
  

## Unexpected Problems
