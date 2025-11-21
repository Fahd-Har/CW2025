# 1. GitHub:

# 2. Compilation Instructions:

# 3. Implemented and Working Properly:

# 4. Implemented but Not Working Properly:

# 5. Features Not Implemented:

# 6. New Java Classes:
### 6.1) GameView.java


# 7. Modified Java Classes:
### 7.1) GuiController.java
a)
<p>
Changes: Created an if else statement in pauseGame method and call it the initialize handle method.
</p>
<p>
Reason: When player press on the 'ESCAPE' key, the game pauses.
</p>
b)
<p>
Changes: Under the method bindScore, added a line of code to bine the score label to score property
</p>
<p>
Reason: Score class exists but was not shown during gameplay. This improvement is therefore to display the player's score
and live updates throughout the game.
</p>
c)
<p>
Changes: Replaced the long switch statement in 'getFillColor()' method with a static Map.
</p>
<p>
Reason: To ensure the function follows the open closed principle.
</p>
d)
<p>
Changes: Replaced the anonymous inner class in the 'initialize()' method with a new method called gameCommand that handles KeyEvents.
</p>
<p>
Reason: Improves code clarity and making the `initialize()` method cleaner and the event handler logic easier to locate and read.
</p>
e)
<p>
Changes: The body of the original 'initGameView()' method was refactored by extracting the separate tasks of board
initialization and brick initialization into two new dedicated private methods, 'initBoard()' and 'initBrick()'.
</p>
<p>
Reason: Improves code organization and readability by following the Single Responsibility Principle (SRP),
making the code easier to maintain and debug by isolating distinct initialization tasks.
</p>
f)
<p>
Changes: Define −42 as an initialization magic number constant within the GuiController class.
</p>
<p>
Reason: Improves code readability by clarifying the value's intent and ensures easy maintainability.
</p>
g)
<p>
Changes: Transferred all methods relating to display-related fields and the JavaFX visual elements from the GuiController
class to the new GameView class.
</p>
<p>
Reason: This separation enforces the Model-View-Controller (MVC) design pattern, making the GuiController class responsible
only for handling input and logic flow, while the GameView class is solely responsible for game visualization.
</p>


### 7.2) SimpleBoard.java
a)
<p>
Changes: Under the method 'createNewBrick()', change coordinate of new off set point from (4,10) to (4,0).
</p>
<p>
Reason: New created bricks will now drop from the top of game board rather than in the middle.
</p>
b)
<p>
Changes: Updated the SimpleBoard constructor and 'newGame()' method to initialize the game matrix using
new int[height][width] instead of new int[width][height].
</p>
<p>
Reason: To avoid logical error by ensuring the matrix correctly maps rows to height and columns to width.
</p>

### 7.3) MatrixOperations.java
<p>
Changes: Updated the 'intersect()' and 'merge()' method to correctly align i and j with the x and y coordinates when checking
brick collisions.
<p>
Reason: To improve code logic and clarity by ensuring coordinate calculations accurately reflect the intended matrix
structure.

# 8. Unexpected Problems:


