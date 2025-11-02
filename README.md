# 1. GitHub:
Link: https://github.com/Fahd-Har/CW2025.git

# 2. Compilation Instructions:

# 3. Implemented and Working Properly:

# 4. Implemented but Not Working Properly:

# 5. Features Not Implemented:

# 6. New Java Classes:

# 7. Modified Java Classes:
### 7.1) GuiController.java
i)
<p>
Changes: Under the method bindScore, added a line of code to bine the score label to score property
</p>
<p>
Reason: This improvement is to display the player's score and live updates throughout the game.
</p>
ii)
<p>
Changes: Converting the access modifier for the newGame method from public to method.
</p>
<p>
Reason: To restrict access to the method within the class, preventing unintended external calls and improving encapsulation.
</p>
iii)
<p>
Changes: Added a new method called showNextBrickPanel to show the next brick shape in a panel below the scores.
</p>
<p>
Reason: To provide players with a visual preview of the upcoming brick, enhancing gameplay awareness and user experience.
</p>
iv)
<p>
Changes: Created an if else statement in pauseGame method and call it the initialize handle method.
</p>
<p>
Reason: When player press on the 'ESCAPE' key, the game pauses.
</p>


### 7.2) SimpleBoard.java
i)
<p>
Changes: Under the method 'createNewBrick', change coordinate of new off set point from (4,10) to (4,0).
</p>
<p>
Reason: New created bricks will now drop from the top of game board rather than in the middle.
</p>
ii)
<p>
Changes: Updated the SimpleBoard constructor and newGame method to initialize the game matrix using
new int[height][width] instead of new int[width][height].
</p>
<p>
Reason: To avoid logical error by ensuring the matrix correctly maps rows to height and columns to width.
</p>

### 7.3) MatrixOperations.java
<p>
Changes: Updated the intersect and merge method to correctly align i and j with the x and y coordinates when checking
brick collisions.
<p>
Reason: To improve code logic and clarity by ensuring coordinate calculations accurately reflect the intended matrix
structure.

# 8. Unexpected Problems:


