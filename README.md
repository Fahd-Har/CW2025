# 1. GitHub:
Link: https://github.com/Fahd-Har/CW2025.git

# 2. Compilation Instructions:

# 3. Implemented and Working Properly:

# 4. Implemented but Not Working Properly:

# 5. Features Not Implemented:

# 6. New Java Classes:
### 6.1) WelcomeScreen.java
Location:
<p>
This class manages the start screen of the application. It handles user interaction with the Start Game button and
transitions smoothly into the main game screen using a fade animation.
</p>

### 6.2) Sound.java
Location:
<p>
The Sound class manages all game audio, including background music and sound effects. It loads, plays, loops, and stops
sounds, allowing different tracks to be triggered during gameplay.
</p>


# 7. Modified Java Classes:
### 7.1) GuiController.java
i
a)
<p>
Changes: Under the method bindScore, added a line of code to bine the score label to score property
</p>
<p>
Reason: This improvement is to display the player's score and live updates throughout the game.
</p>
b)
<p>
Changes: Converting the access modifier for the newGame method from public to method.
</p>
<p>
Reason: To restrict access to the method within the class, preventing unintended external calls and improving encapsulation.
</p>
c)
<p>
Changes: Added a new method called showNextBrickPanel to show the next brick shape in a panel below the scores.
</p>
<p>
Reason: To provide players with a visual preview of the upcoming brick, enhancing gameplay awareness and user experience.
</p>
d)
<p>
Changes: Created an if else statement in pauseGame method and call it the initialize handle method.
</p>
<p>
Reason: When player press on the 'ESCAPE' key, the game pauses.
</p>
e)
<p>
Changes: Created a new method called nextLevelSpeed to speed up the falling bricks after a specific amount of score is reached.
</p>
<p>
Reason: Implements as new level and increase difficulty once player achieves a specific score.
</p>
f)
<p>
Changes: Called methods from Sound class into some methods of the GuiController class to implement both background music
and sound effects for gameplay events such as piece rotation, line clears, and game over.
</p>
<p>
Reason: To enhance the player’s experience by providing audio feedback and immersive background music, making the game
more engaging and responsive.
</p>
g)
<p>
Changes: Added a mew method called slamDown for brick to instantly drop into placement after player press the specific key.
</p>
<p>
Reason: To enhance player's experience in playing tetris gameplay.
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


