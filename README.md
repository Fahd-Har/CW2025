# 1. GitHub:

# 2. Compilation Instructions:

# 3. Implemented and Working Properly:

# 4. Implemented but Not Working Properly:

# 5. Features Not Implemented:

# 6. New Java Classes:
### 6.1) CurrentBrickController
Location: CW2025/src/main/java/com/comp2042/gameBoard/CurrentBrickController.java
<br>
Purpose: Takes up brick position, movement, and rotation logic from SimpleBoard.
This helps support SRP by separating responsibilities.

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

### 7.2) SimpleBoard.java
a)
<p>
Changes: Under the method 'createNewBrick', change coordinate of new off set point from (4,10) to (4,0).
</p>
<p>
Reason: New created bricks will now drop from the top of game board rather than in the middle.
</p>
b)
<p>
Changes: Updated the SimpleBoard constructor and 'newGame' method to initialize the game matrix using
new int[height][width] instead of new int[width][height].
</p>
<p>
Reason: To avoid logical error by ensuring the matrix correctly maps rows to height and columns to width.
</p>
c)
<p>
Changes: Renamed variable p to newOffset.
</p>
<p>
Reason: To understand what this variable does/takes and not get confused later on when it's called again.
</p>
d)
<p>
Changes: Introduced new methods 'offsetMovement' and 'checkConflict' to check the movement of brick follows their respective
order and if it would collide with another brick respectively.
</p>
<p>
Reason: The same codes were repeated few times in multiple different methods with the only difference being where it moves.
This is a code smell known as duplicate code. So we create new methods and call them in the other methods to reduce duplication.
</p>
e)
<p>
Changes: Extracted brick position, movement, and rotation logic from SimpleBoard into a new CurrentBrickController class.
</p>
<p>
Reason: Supports SRP by separating brick control from board management, reducing complexity and reducing multiple
responsibilities in the SimpleBoard class.
</p>


### 7.3) MatrixOperations.java
<p>
Changes: Updated the 'intersect' and 'merge' method to correctly align i and j with the x and y coordinates when checking
brick collisions.
<p>
Reason: To improve code logic and clarity by ensuring coordinate calculations accurately reflect the intended matrix
structure.

# 8. Unexpected Problems:


