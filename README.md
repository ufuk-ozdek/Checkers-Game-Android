# Checkers-Game

8X8 Checkers Game with Kotlin





## **HOW TO PLAY**

 The game is played on a board with 8x8 squares -- Each player has 12 pieces

### **Moves and captures**

  White starts first
  
  *Ordinary* (Basic) pieces move one square diagonally forward to an unoccupied square.
  
  Enemy pieces can be captured __by jumping over__ the enemy piece, two squares forward or backward to an unoccupied square immediately beyond.
  
  Multiple successive jumps forward or backward in a single turn can and __must be made__ if after each jump there is an unoccupied square immediately      beyond the enemy piece.
  
  A jumped piece is removed from the board at the end of the turn. 
  
  The same piece may not be jumped more than once.

### **Crowning**

A piece is crowned if it stops on the far edge of the board at the end of its turn __that is, not if it reaches the edge but must then jump another piece   backward.__ 

Crowned pieces, *kings*, can move diagonally forward or backward. 

King can move multiple steps in any direction but must jump over and hence capture an opponent piece some distance away and choose where to stop afterwards, but must still capture the maximum number of pieces possible.

### **Win Condition**

Player wins when opponent has __no piece__ in the board.



![device-2022-08-28-104720](https://user-images.githubusercontent.com/70910355/187064026-43c4cedb-7cec-4af0-bde9-3288abe7be96.gif)
