# Developing Maintainable Software Coursework

Name: Mohamed Fahd Haris Bin Shaiful Nizam
<br>ID: 20702811

## GitHub
This is my GitHub repository link:

[GitHub Repository Link](https://github.com/Fahd-Har/CW2025.git)

## Compilation Instructions
### 1. Clone the repository to your local machine:

`git clone https://github.com/Fahd-Har/CW2025.git`

### 2. Open the project in one of the following IDE:
   - IntelliJ IDEA
   - Eclipse

### 3. Install Java Development Kit (JDK) 21 or newer (21 recommended) in your chosen IDE.
This project uses JavaFX 21, which works best with JDK 21.

### 4. Make your chosen IDE load Maven dependencies:

IntelliJ IDEA
   - Go to View → Tool Windows → Maven
   - In the Maven window, click the Reload (🔄) icon
   - This forces IntelliJ to download all required libraries.

Eclipse
   - Right-click the project
   - Select Maven → Update Project
   - Click OK

### 5. Open the Maven tool window

   IntelliJ IDEA
   - Open: View → Tool Windows → Maven

   Eclipse
   - Open: Window → Show View → Other… → Maven → Maven Projects

### 6. Run the game using the JavaFX Maven plugin
   
In the Maven window:

   - Expand Plugins
   - Expand javafx
   - Double-click javafx:run
   
This will start the game.
## Implemented and Working Properly

### Gameplay Mechanics
- **Brick Movement and Collision:** Logic for brick position, movement (down, left, right), and rotation is handled, including collision checks with existing blocks and the board boundaries.
- **Brick Spawning:** New bricks drop from the top of the game board at the correct starting position.
- **Line Clearing:** The system correctly checks for and removes full rows, shifting the remaining blocks down.
- **Ghost Piece:** A visual Ghost Piece is displayed, showing the possible final placement point of the current falling brick.
- **Hard Drop:** Moves current brick to the lowest possible instantly.
- **Hold Brick:** Players can store a falling brick for later use in the Hold Brick Panel (using key C). This feature prevents multiple swaps during a single turn.

### GameFlow
- **New Game:** The 'N' key starts a new game, resetting all game stats and ensuring the falling speed is also reset.
- **Pause/Resume:** The 'ESCAPE' key correctly pauses and resumes the game, stopping the falling brick and the game timer.
- **Game Over:** The logic correctly ends the game when bricks stack too high, stopping the game timer and activating the Game Over Panel.
- **Level Up System:** The game progresses one level for every 10 lines removed, up to a maximum of level 5.

### Game Tracking
- **Game Timer:** Shows the time the player plays, starts when the game begins and ends when game over.
- **Score Tracking:** Tracks and live updates the player's score in the given panel.
- **Lines Cleared Tracking:** Tracks and live updates the number of lines the player cleared in the given panel.
- **Next Brick Preview:** The Next Brick Panel displays the shape of the brick coming after the current one.
- **Notifications:** Animated pop-up notifications are displayed for awarded scores, score bonuses and when the player advances a level.

### Difficulty Modes
The game includes three distinct difficulty modes:

- **Normal Mode:** Falling speed increases by level, starting at 400ms and decreasing by 70ms per level up until level 5.
- **Hard Mode:** Includes Rising Rows that are pushed from the bottom, getting faster with level ups (from 20 seconds at level 1 to 10 seconds at level 5), and having up to 3 holes.
- **Extreme Mode:** Combines the increasing falling speed of Normal Mode with the Rising Rows feature of Hard Mode.

### Scenes
- **Main Menu Scene:** Shows the player the option to get started with the game, check the scoreboard or get to know the abouts of the game.
- **Choose Difficulty Scene:** Player get to choose which mode (Normal, Hard, Extreme) they would like to play. Also include the option to go back to the main menu screen if they decide to.
- **Game Layout Scene:** Game scene/panel where player plays the game.
- **Pause Panel:** Panel that pops up when player pauses the game. Has the option to return back to main menu scene.
- **Game Over Panel:** Panel that pops up when game ends. Has the option to return back to main menu scene.
- **Scoreboard:** Scoreboard scene where player gets to view their highest score achievements. Only top 5 are shown and player gets to view each scoreboard for different difficulty modes by toggling the button.
Has the option to return to main menu scene too.
- **About Scene:** Scene that shows the player the abouts (game info and difficulty modes) of the game. Has the option to return back to main menu scene.

### Others
- **Background Music:** Applied background music for players to enjoy when playing the game.
- **Sound Effects:** Sound is implemented with effects for key actions like rotation, line deletion, and game over.
- **Background Images:** Applied images to almost all of the scenes for a better player experience. Fitting using the Neon aesthetic.

## Implemented but Not Working Properly
- Background music can be a bit too soft to the actual sound effects of the game.
- If player use slam down to fill and clear a line straight away after clearing a different one, music will not catch up and no sound effect will play.

## Features Not Implemented
- A smokescreen game mode where player has blocked vision of the current board for a specific amount of time.
- Game mode where for each minute, only one specific shape of the brick is spawn and can be played. Changes every minute.

## New Java Classes
- **CurrentBrickController** Path: src/main/java/com/comp2042/model/gameBoard/CurrentBrickController.java
    - Takes up brick position, movement, and rotation logic from TetrisBoard.
    - Added core Hold Brick logic to manage the heldBrick state and swap the current piece, including a check to prevent multiple swaps per turn.
    - This helps support Single Responsibility Principle (SRP), a part of the SOLID Design Pattern by separating responsibilities.


- **GameFlowManager** Path: src/main/java/com/comp2042/controller/GameFlowManager.java
    - Takes up the game timeline logic from GuiController
    - This helps support SRP by separating responsibilities.


- **GameRenderer** Path: src/main/java/com/comp2042/controller/GameRenderer.java
    - Takes up initialization of game board and bricks logics from GuiController.
    - Generates all related panels for the game layout fxml file.
    - Ensure bricks fall in the middle by adding constants of the LayoutX and LayoutY of gamePanel.
    - This helps support SRP by separating responsibilities.


- **Notifications** Path: src/main/java/com/comp2042/events/Notifications.java
    - Takes up the logic for all pop-up notifications (+scores, future level ups) from GuiController.
    - This helps support SRP by separating responsibilities.


- **KeyInputHandler** Path: src/main/java/com/comp2042/controller/keyInput/KeyInputHandler.java
    - Takes up the logic for key inputs and their respective actions from GuiController using consumer.
    - This helps support SRP by separating responsibilities.


- **MainMenuScene** Path: src/main/java/com/comp2042/view/scenes/MainMenuScene.java
    - An enhancement for player to choose whether they want to see the game rules/what each difficulty does (About),
    or straight away choose their difficulty game mode.
    - Maintains a clear separation between the welcome screen, variance of game difficulty and the game itself.


- **GameTime** Path: src/main/java/com/comp2042/model/logic/GameTime.java
    - Initialize game clock as an enhancement for gaming experience using JavaFX's `Timeline()`.
    - Exposes the elapsed time as a bindable `StringProperty` (MM:SS).
    - Implements pause/resume logic by tracking `pauseDuration` to prevent the timer from including time spent while paused, ensuring accurate time keeping.
    - This helps support SRP by dedicating a class purely to time tracking.


- **CountClearedRows** Path: src/main/java/com/comp2042/model/logic/CountClearedRows.java
    - Count the number of lines removed throughout the game as an enhancement for gaming experience.
    - Uses a JavaFX **_IntegerProperty_** to allow for automatic, real-time updates in the GUI.


- **LevelUp** Path: src/main/java/com/comp2042/model/logic/LevelUp.java
    - Manages the game level from level 1.
    - For every 10 lines removed, the game levels up automatically by calling `checkAndAdvance()`.


- **GamePausePanel** Path: src/main/java/com/comp2042/view/scenes/GamePausePanel.java
    - Handles loading and positioning of the pauseScreen.fxml screen.
    - Manages the visibility of the pause menu panel by binding it directly to the `isPause()` state from the `GameFlowManager` class.


- **ChooseDifficultyScene** Path: src/main/java/com/comp2042/view/scenes/ChooseDifficultyScene.java
    - Links the game's UI and logic by retrieving the GuiController from the FXML and using it to initialize the GameController before displaying the game scene.
    - Separates game logic between each game difficulties.


- **GameMode** Path: src/main/java/com/comp2042/view/model/GameMode.java
    - New enumeration to defines the three difficulty modes and holds two core boolean properties to control difficulty scaling in the game loop.


- **Sound** Path: src/main/java/com/comp2042/view/soundBoard/Sound.java
    - Handles and imports all sound related logic to enhance game play.


- **HighscoreEntry** Path: src/main/java/com/comp2042/model/scoreBoard/HighscoreEntry.java
  - Uses serializable to store the player's final score, level, lines, time, and the GameMode to ensure scores are tracked separately for each difficulty mode.


- **ScoreBoardManager** Path: src/main/java/com/comp2042/model/scoreBoard/ScoreBoardManager.java
  - Stores the player's records in the file, **_highScores.dat_**
  - Manages a map to keep the scoreboard for each mode different.
  - Includes logic for loading, sorting, and maintaining only the top 5 scores for each mode.


- **ScoreBoardScene** Path: src/main/java/com/comp2042/model/scoreBoard/ScoreBoardScene.java
  - Handles the scoreboard scene that shows the player.
  - Implements logic to switch the displayed scoreboard via buttons for Normal, Hard, and Extreme modes.


- **AboutScene** Path: src/main/java/com/comp2042/view/scenes/AboutScene.java
    - Handles the about panel to show the player the Gameplay Info and Difficulty Modes.


- **SceneSwitch** Path: src/main/java/com/comp2042/view/scenes/SceneSwitch.java
    - Handles the common method of switching from current scene to Main Menu Scene.
    - If required to change, we can just change in this class rather than going to each individual class with this method and changing it.


- **HighScoreSaver** Path: src/main/java/com/comp2042/model/scoreBoard/HighScoreSaver.java
  - Defines the abstraction for saving high scores.


- **BrickMovement** Path: src/main/java/com/comp2042/model/gameBoard/BrickMovement.java
    - Contains methods for controlling the current falling brick (move down, left, right, rotate, hold).
    - Implements the Interface Segregation Principle (ISP) by dedicating an interface solely to brick control actions.


- **Gameplay** Path: src/main/java/com/comp2042/model/gameBoard/Gameplay.java
    - Contains methods for core game logic actions that modify the gameplay (merge brick, clear rows, add rising row, createNewBrick, newGame).
    - Implements the ISP by dedicating an interface solely to gameplay.


- **GameStats** Path: src/main/java/com/comp2042/model/gameBoard/GameStats.java
    - Provides read-only access to all important game state and statistics (board matrix, view data, score, time, lines, level).
    - Implements the ISP, allowing the GUI binding to depend only on retrieval methods.

## Modified Java Classes
### File Refactoring

- **Board.java**
    1. The interface was split into three segregated interfaces.
    2. The new `Board` interface now acts as a **composite interface**, simply extending all three smaller interfaces to maintain backward compatibility.


- **TetrisBoard.java**
    1. Renamed the class from `SimpleBoard` to `TetrisBoard` for a better description of the class.
    2. In `createNewBrick()`, changed the new offset point coordinate from **(4,10)** to **(4,1)** so new bricks drop from the top of the game board.
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
    2. Created a new method `implementRisingRow()` to initialize the logic for new rows to be pushed from the bottom.
    3. Implement logic on number of holes in the new rising row depends on the current level.


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
    12. Added FXML field `@FXML private Text gameTime;` to bind to the clock display.
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
    21. Added a setter to pass mode to GameFlowManager.
    22. Call specific sounds from the Sound class for specified actions.
    23. Added new public method `showLevelUpNotification()` for the GameController to read from instead of reading from notification class which may violate SRP.
    24. Simplified the `gameOver()` method by delegating the entire score packaging and saving process to the GameController class.
    25. Add a null check and clear error handling if the specified font file is not found.


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
    9. The `onRisingRowEvent()` now only runs if game mode is hard or extreme. Does not run on normal mode.
    10. Now checks in the `onDownEvent()` method if player reaches the number of removed lines, level up pop up occurs.
    11. Added the method `getFinalGameStats()` to encapsulate the final game score, level, lines, time, and GameMode into a HighScoreEntry and delegate the saving process to the injected HighScoreSaver dependency.
    This supports the Dependency Inversion Principle (DIP) in the SOLID design pattern.


- **Main.java**
    1. Class now loads a different scene, a main menu screen. This scene does not involve any Gui or game control logic,
    hence the lines involve with this logic was removed. Window screen has now increased to 1280x800 pixels size.


- **ViewData.java**
    1. Create new getters for shadow position of the brick so that alterations to these getters are independent and does
    not affect the positions for actual brick.
    2. Updated the class structure to include the shape data for the brick currently in the hold queue.


- **GameOverPanel.java**
    1. Alter the code to put in my own Game Over Scene.


- **NotificationPanel.java**
    1. Code was updated so that the created notification panel for score and level up can have two distinct styles rather than the same style.
    2. Remove the hardcoded color and glow and put them in the css file to differentiate tasks easily.


- **Notifications.java**
    1. Added a new method to show the pop up for every time the player level's up.
    2. Separate groupNotification call into scoreNotification and levelUpNotification for easier change in their layouts.
    Also helps so that change in one does not affect the other.


- **InputEventListener.java**
    1. Added `onHoldEvent()` to support the Hold feature, allowing the user to store a brick for later use.
    2. Added `onSlamEvent()` To support the Slam (or instant drop) feature, instantly moving the current brick to the lowest available position.

## Unexpected Problems

- **Problem 1:**
<br> Pause game logic still activates when game over is logic is activated.

- **Solution**
<br> In the GuiController class, call an if statement to not call the pause game logic if game over logic is true.


- **Problem 2:**
<br> Falling bricks speed does not reset if new game by pressing the N key occurs.

- **Solution**
<br> In the new game logic method, recreate the game action timeline to ensure speed of bricks falling is always reset.


- **Problem 3:**
<br> If a row was instantly remove after another row was removed, sound effect was not played.

- **Solution**
<br> Added a for loop to make the sound effect play every time a line has been removed. Whether back to back there is gap in between.


- **Problem 4:**
<br> Hold brick panel still shows brick held from previous game.

- **Solution**
<br> Create a method in `CurrentBrickController` class and call it in `TetrisBoard`to reset the empty panel when new game occurs.