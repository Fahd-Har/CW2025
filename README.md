# 1. GitHub:
Link: https://github.com/Fahd-Har/CW2025.git

# 2. Compilation Instructions:

# 3. Implemented and Working Properly:

# 4. Implemented but Not Working Properly:

# 5. Features Not Implemented:

# 6. New Java Classes:

# 7. Modified Java Classes:
### 7.1) GuiController.java
<p>
Changes: Under the method 'bindScore', added a line of code to bine the score label to score property
<p>
Reason: This improvement is to display the player's score and live updates throughout the game.

### 7.2) SimpleBoard.java
i)
<p>
Changes: Under the method 'createNewBrick', change coordinate of new off set point from (4,10) to (4,0).
<p>
Reason: New created bricks will now drop from the top of game board rather than in the middle.
<br>
<br>
ii)
<p>
Changes: Updated the SimpleBoard constructor and newGame method to initialize the game matrix using
new int[height][width] instead of new int[width][height].
<p>
Reason: To avoid logical error by ensuring the matrix correctly maps rows to height and columns to width.

# 8. Unexpected Problems:


