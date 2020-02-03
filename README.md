# FKApplyDesign
Tic Tac Toe Game Design
    Compile : javac
    Run .   : java
   
<h4> System Specifications<h5>
    1. Marker - Enum Class  for the symbols which can be present in one cell. <br>
    2. CellInterface- to deal with one cell,marker it is going to use<br>
    3. BoardInterface- Stores Board related data<br>
    4. PlayerInterface- Two types of players are present:Human,Computer<br>
    5. JudgeInterface- Decides whether game is over or not. If game is over ,declares the winner<br> 
    6. GameManager- It Initializes board,judge,player and run the game<br>
    7. ControlManager- It starts game by initializing instance of gameManager, save previous leaderBoardData<br>
   
  <h4> Why this design is good? </h4>
    I used combination of composition and inheritance(mostly composition).I created this design in such a way that at a time only one instance of game is running(Single thread) thus GameManager,ControlManage,Board all are created using Singleton
    Design pattern.
    
   <h4> Goals for Future</h4>
    1. Testing
    2. MultiThreading Design
    3. Multi Player Design.(Tournament Style)
    
