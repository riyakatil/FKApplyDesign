

import java.awt.image.AreaAveragingScaleFilter;
import java.util.HashMap;
import  java.util.*;
import java.io.*;
import java.util.Arrays; 
import java.util.stream.*;

// User Interaction Interface
interface UserInteractionInterface{
  int intInput();
  String stringInput();
  void showMessageToUser(String s);
}

class UserInteraction implements UserInteractionInterface{

private Scanner obj;
  UserInteraction(){
    obj=new Scanner(System.in);

  }


 public int intInput(){
 return obj.nextInt();

}

public  String stringInput(){
    return obj.next();
  }


  public void showMessageToUser(String s){

    System.out.println(s);
  }
}

// Exhibits Judge Behaviour
interface JudgeInterface
{
  void declareWinner(Player p1);
  boolean gameOver(ArrayList<char[]> b1,int a,int b);
}



class JudgeHex implements JudgeInterface{

  public void declareWinner(Player p1){
    System.out.println(p1.getTitle()+" has won");
  }

public boolean gameOver(ArrayList<char[]> ar1,int x,int y) 
{
       
      boolean f1=diagonalCrossedHex(ar1,x,y);
     // boolean f2= rowCrossed(ar1);
      boolean f3=  columnCrossedHex(ar1,x,y);
    //  System.out.println(f1+" "+f2+" "+f3);
      return f1||f3;
}



private int consecutiveChar(ArrayList<Character> a1,char ch)

{   
      int count = 0; //intitialize count 
    int result = 0; //initialize max 
  
    for (int i = 0; i < a1.size(); i++) 
    { 
         char c1=a1.get(i);
        if (c1!=ch) 
            count = 0; 
        else
        { 
            count++;
            result = Math.max(result, count); 
        } 
    } 
  
    return result; 
}

 

private boolean diagonalCrossedHex(ArrayList<char[]> ar1,int x,int y)
{
 int row=ar1.size();


int x_diag,y_diag,x_antiDiag,y_antiDiag;
x_diag=y_diag=x_antiDiag=y_antiDiag=0;


/*
int a=Math.min(x,y);
if(a==x){
  x_diag=0;
  y_diag-=a;
}
else{
  x_diag-=a;
  y_diag=0;

}*/

x_diag=x;
y_diag=y;



while(x_diag-1 >=0 && y_diag-1>=0){x_diag--;y_diag--;}

x_antiDiag=x;
y_antiDiag=y;

while(x_antiDiag-1 >=0 && y_antiDiag+1<row){x_antiDiag--;y_antiDiag++;}



//check in this diagonal
int cur_x=x_diag;
int cur_y=y_diag;
ArrayList<Character> a1=new ArrayList<Character>();
while(cur_x<row && cur_y<row){
  a1.add(ar1.get(cur_x)[cur_y]);
  cur_x++;
  cur_y++;
}

int countX=consecutiveChar(a1,'X');
 int countO=consecutiveChar(a1,'O');
 if(countX>=4 || countO>=4){return true;}



//check in opposite of diagon ally

cur_x=x_antiDiag;
cur_y=y_antiDiag;
a1=new ArrayList<Character>();
while(cur_x<row && cur_y>=0){
  a1.add(ar1.get(cur_x)[cur_y]);
  cur_x++;
  cur_y--;
}

 countX=consecutiveChar(a1,'X');
 countO=consecutiveChar(a1,'O');

if(countX>=4 || countO>=4){return true;}


return false;
}


private boolean columnCrossedHex(ArrayList<char[]> ar1,int x,int y){

int cur_x=0;
int cur_y=y;
int row=ar1.size();

ArrayList<Character> a1=new ArrayList<Character>();

while(cur_x<row && cur_y<row){
  a1.add(ar1.get(cur_x)[cur_y]);
  cur_x=cur_x+2;
  
}
int countX=consecutiveChar(a1,'X');
int countO=consecutiveChar(a1,'0');

if(countX>=4 || countO>=4){return true;}


return false;


}}





class JudgeSquare implements JudgeInterface
{


  public void declareWinner(Player p1)     
{ 
    System.out.println(p1.getTitle()+" has won");

} 

  public boolean gameOver(ArrayList<char[]> ar1,int basicUnit,int b)   // It checks in row,column,diagonal for win condition
{   
     

     if(ar1.size()==basicUnit && basicUnit==ar1.get(0).length){

      boolean f1=diagonalCrossed(ar1);
      boolean f2= rowCrossed(ar1);
      boolean f3=  columnCrossed(ar1);
    //  System.out.println(f1+" "+f2+" "+f3);
      return f1||f2||f3;
     }

    boolean t1=rowCrossed(ar1,basicUnit);
   boolean t2= columnCrossed(ar1,basicUnit);
     boolean t3=diagonalCrossed(ar1,basicUnit) ; 
      return t1 || t2 || t3;

}


//row crosswed

private boolean rowCrossed(ArrayList<char[]> ar1,int basicUnit){

if(ar1.size()<basicUnit){
 return false;
}

// basic size block
if(ar1.size()==basicUnit){
  boolean x1=gameOver(ar1,basicUnit,ar1.size());


  return x1;
}


  int depth=ar1.size();
  //int basicUnit=basicUnit;
   
   int blockSize= depth/basicUnit;


  ArrayList<char[]> arr=new ArrayList<char[]>();
   arr = new ArrayList<char[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
           { arr.add(new char[blockSize]);}



for(int i=0;i<depth;i=i+blockSize){

   int count=0;
//   arr = new ArrayList<char[]>(blockSize);

  for(int j=0;j<depth;j=j+blockSize){
     
     

     for(int p=0;p<blockSize;p++){
      for(int q=0;q<blockSize;q++){
       
          int a=p+i;
          int b=q+j;
        arr.get(p)[q]=ar1.get(a)[b];
          


      }
    
     }
        
      

      boolean tmp=gameOver(arr,basicUnit,2);
      if(tmp){count++;}




  }

  if(count==basicUnit){return true;}
}

return false;
}



//columncrosswed

private boolean columnCrossed(ArrayList<char[]> ar1,int basicUnit){

if(ar1.size()<basicUnit){
 return false;
}

// basic size block
if(ar1.size()==basicUnit){
  boolean x1=gameOver(ar1,basicUnit,ar1.size());


  return x1;
}


  int depth=ar1.size();
  //int basicUnit=basicUnit;
   
   int blockSize= depth/basicUnit;


  ArrayList<char[]> arr=new ArrayList<char[]>();
   arr = new ArrayList<char[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
            arr.add(new char[blockSize]);
  

  for(int j=0;j<depth;j=j+blockSize){

int count=0;
for(int i=0;i<depth;i=i+blockSize){
     

    for(int p=0;p<blockSize;p++){
      for(int q=0;q<blockSize;q++){
           arr.get(p)[q]=ar1.get(j)[i];

      }
     }

      boolean tmp=gameOver(arr,basicUnit,2);
      if(tmp){count++;}




  }

  if(count==3){return true;}
}

return false;
}



//diagonal crossed
private boolean diagonalCrossed(ArrayList<char[]> ar1,int basicUnit){

if(ar1.size()<basicUnit){
 return false;
}

// basic size block
if(ar1.size()==basicUnit){
  boolean x1=gameOver(ar1,basicUnit,ar1.size());
  return x1;
}

//Go in loop

  int count=0 ; // if it is equal to basic unit or not
  int depth=ar1.size();
  //int basicUnit=basicUnit;
   
   int blockSize= depth/basicUnit;

   ArrayList<char[]> arr=new ArrayList<char[]>();
   arr = new ArrayList<char[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
            arr.add(new char[blockSize]);

// diagonaly check


    for(int k=0;k<depth;k=k+basicUnit){
     
     


      for(int i=0;i<basicUnit;i++){
        for(int j=0;j<basicUnit;j++){
          arr.get(i)[j]=ar1.get(i+k)[j+k];

        }
      }

     boolean tmp=gameOver(arr,basicUnit,2);
      if(tmp){count++;}

    }


if(count== basicUnit){return true;}


//antidiagonaly

  for(int k=depth-1;k>=0;k=k-basicUnit){
     
     int y1=k;
     int x1=depth-1-k;
     y1=y1- basicUnit+1; 


      for(int i=0;i<basicUnit;i++){
        for(int j=0;j<basicUnit;j++){
          arr.get(i)[j]=ar1.get(i+x1)[j+y1];

        }
      }

     boolean tmp=gameOver(arr,basicUnit,2);
      if(tmp){count++;}

    }


if(count== basicUnit){return true;}


return false;
//Anti Diagonally




    

}


private boolean diagonalCrossed(ArrayList<char[]> ar1) 
{

  //ArrayList<char[]> b1=tmp.arr;
  int row=ar1.size();
  int col=row;


  boolean flag=false;
   


int countO=0,countX=0;

    for (int i=0; i<row; i++) 
    {   
       

        
       if(ar1.get(i)[i]=='X'){countX++ ;}
       else if(ar1.get(i)[i]=='O'){countO++ ;}
      
     
     
    } 
      if(countX==row ||  countO==row) {flag=true; }
      countX=0;
      countO=0;

    for (int i=row-1; i>=0; i--) 
    {   
      

        
       if(ar1.get(row-1-i)[i]=='X'){countX++ ;}
       else if(ar1.get(row-1-i)[i]=='O'){countO++ ;}
      
     
     
    } 

     if(countX==row ||  countO==row) {flag=true; }
  

    
    return flag; 
}


private boolean columnCrossed(ArrayList<char[]> ar1) 
{ 
    int row=ar1.size();
  int col=row;

boolean flag=false;
    for (int i=0; i<col; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<row;j++){
        
       if(ar1.get(j)[i]=='X'){countX++ ;}
       else if(ar1.get(j)[i]=='O'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 


    
    return flag; 
} 

private boolean rowCrossed(ArrayList<char[]> ar1) 
{ 

    
  int row=ar1.size();
  int col=row;

boolean flag=false;
    for (int i=0; i<row; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<col;j++){
        
       if(ar1.get(i)[j]=='X'){countX++ ;}
       else if(ar1.get(i)[j]=='O'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 




    
    return flag;
} 
}




// Every Player can check board status , will move and has its own title

interface PrimaryPlayerInterface{

  int[] nextMove();
  void printBoardStatus();
}

interface Player extends PrimaryPlayerInterface{

  String getTitle();

}


class Human implements Player{

  private String title;
  private GameManagerInterface cm1;
  private UserInteractionInterface u1;

  Human(GameManager cm1,int i){
    this.cm1=cm1;
    this.title="Human"+(i+1);
    this.u1=new UserInteraction();
  }

  public void printBoardStatus(){
    cm1.printBoardStatus();
  }


public int[] nextMove(){

        int pos[]=new int[2];
        String s="Enter cell no for next move";
        u1.showMessageToUser(s);
        int cell_no=u1.intInput();
        cell_no--;
        int row=cm1.getDepthOfBoard();

       pos[0]=cell_no/row;
       pos[1]=cell_no%row;
return pos;
}

public String getTitle(){return this.title;}



}

class Computer implements Player
{ 
  private String title;
       private GameManagerInterface cm1;

  Computer(GameManager cm1){
    this.cm1=cm1;
    this.title="Computer";

  }

  public void printBoardStatus(){
    cm1.printBoardStatus();
  }

public int[] nextMove(){
  
return this.cm1.getFreeCellOfBoard();
  

}

public String getTitle(){return this.title;}


}






//BoardInterface is responsible for any activity related to Board
interface PrimaryBoardInterface{
  boolean isEmpty();
  
}

interface BoardInterface extends PrimaryBoardInterface
{ 
    void updateBoard(int x,int y,char ch);
     
    boolean isFull();

    int[] getEmpty();
    int getDepth();
    int getBasicUnit();

    boolean isValid(int i,int j);
    char getCharAtPosition(int i,int j);
    ArrayList<char[]> getBoardPositions();
    void reInitBoard(int n,int m);

}


// Every board can be only one - singleton class
class Board implements  BoardInterface
{
    private ArrayList<char[]> arr;
    private int depth;
    private int basicUnit;

    private static Board b1=null;


    private ArrayList<char[]> getSquareBoardMatrix(int n){

      ArrayList<char[]> arr = new ArrayList<char[]>(n);
      


        for(int i=0;i<n;i++) 
            arr.add(new char[n]);

          for(int i=0;i<n;i++) 
            {
            for(int j=0;j<n;j++) 
                { //char ch=(char)(cell_no+'0');
                  char ch='_';
                  arr.get(i)[j]=ch;}
            }

              this.depth=n;
              this.basicUnit=n;

              return arr;
    }


    private ArrayList<char[]> getHexBoardMatrix(int n){

      ArrayList<char[]> arr = new ArrayList<char[]>(2*n);
      
      System.out.println("iioooo");

        for(int i=0;i<2*n;i++) 
            arr.add(new char[2*n]);

          char ch='*';
          for(int i=0;i<2*n;i++) 
            { boolean flag=true;
            for(int j=0;j<2*n;j++) 
                { 
                  if(i%2==0){ if(flag){ch='*';} else {ch='_';}}
                  else{
                    if(flag){ch='_';} else {ch='*';}
                  } flag=!flag;
                  arr.get(i)[j]=ch;

                }
            }

              this.depth=2*n;
              this.basicUnit=2*n;

              return arr;
    }



    private Board(int n,int choice)
    { ArrayList<char[]> arr;

      if(choice==1){
       this. arr = getHexBoardMatrix(n);
      
      }
      else{
        this.arr=getSquareBoardMatrix(n);
      }
}
  

                



    //get Board method
    public static BoardInterface getBoard(int n,int choice)
    {
       if(Objects.isNull(b1)){ b1=new Board(n,choice); }
       return b1;
    }



      //Board is empty
    public boolean isEmpty()
    {

       for(int i=0;i<depth;i++)
       {
            for(int j=0;j<depth;j++)
            {
             if(this.arr.get(i)[j]!='X' && this.arr.get(i)[j]!='O'  ) 
              {return  true; }
            }
      }
       return false;

    }

      //Board is full
    public boolean isFull(){


      boolean flag=true;
        for(int i=0;i<depth;i++){
            for(int j=0;j<depth;j++){ 
              if(this.arr.get(i)[j]!='X' &&  this.arr.get(i)[j]!='O' ) {flag=  false; };
            }
        }

       // System.out.println(" board is "+flag);
        return flag;

    }


    //get no of rows
    public int getDepth(){return this.depth;}


    //get no of column
    public int getBasicUnit(){return this.basicUnit;}


    //get empty cell
    public int[] getEmpty()
    {
      int [] pos=new int[2];
      boolean flag=false;
      for(int i=0;i<depth;i++)
      {
        for(int j=0;j<depth;j++)
        {
            if(this.arr.get(i)[j]!='X' && this.arr.get(i)[j]!='O'  )
            {
              pos[0]=i;
              pos[1]=j;
               
              flag=true;
              break;
          }
        }
        
        if(flag){break;}
      }
      return pos;
    }


    //updateBoard
    public void updateBoard(int x,int y,char ch){
      this.arr.get(x)[y]=ch;
    }

    //check if any position is valid
    public boolean isValid(int i,int j){
      if(i>=0 && j>=0 && i<this.getDepth() && j<this.getDepth() && this.arr.get(i)[j]!='X' && this.arr.get(i)[j]!='O'  && this.arr.get(i)[j]!='*') {return true;}
      return false;
    }


    // get current pos of board
    public char getCharAtPosition(int i,int j){
      return (char)this.arr.get(i)[j];
    }

    public ArrayList<char[]> getBoardPositions(){return this.arr;}

    public void reInitBoard(int n,int m){
         arr = new ArrayList<char[]>(n);


  for(int i=0;i<n;i++) 
            arr.add(new char[n]);

      int cell_no=1;

        for(int i=0;i<n;i++) 
            {
            for(int j=0;j<n;j++) 
                { //char ch=(char)(cell_no+'0');
                  char ch='_';
                  arr.get(i)[j]=ch; cell_no++;}
            }

              this.depth=n;
              this.basicUnit=m;
    }

}


//second interface  (game manager will talk to config manager)
// Board, Player,Judge all will communicate with each other using this class only.

interface PrimaryGameManagerInterface{
 void playGame(int ch);
    void configureGame(int i,String path);

}
interface GameManagerInterface extends PrimaryGameManagerInterface{
  
    //void Game();
    void exitGame();
    int getDepthOfBoard();
   
    void printBoardStatus();
    int[]getFreeCellOfBoard();
     void configureGame(int i,String path);

   //  void playGame();
     String getPath();

}

class GameManager implements GameManagerInterface {
  
  private BoardInterface b1;
  private String path;
  private Player firstPlayer;
  private Player secondPlayer;
  private JudgeInterface j1;
  private static GameManagerInterface cm1;

  private UserInteractionInterface u1;

  private GameManager(){}

  public static GameManagerInterface getGameManager(){
    if(cm1==null){cm1=new GameManager();}
    return cm1;
  }

  public void configureGame(int i,String path)
  { System.out.println("config");
        this.path=path;

         u1=new UserInteraction();
         int choice,depth,basicUnit;
         String s;

         while(true){
         s="Enter Tile Type: 1.Hex Tile 2.Square Tile  ";
         this.printMessage(s);
         choice=this.getUserIntegerInput();
         if(choice!=1 && choice !=2){System.out.println("Enter Valid Input");}
         else{break;}}
          
          s="Enter basic unit";
         this.printMessage(s);
         basicUnit=this.getUserIntegerInput();


         b1=this.getBoard(basicUnit,choice);
          

        

        


       if(i==1){
             firstPlayer=getHumanPlayer(0);
             secondPlayer=getHumanPlayer(1);}
    else{
          firstPlayer=getHumanPlayer(0);
          secondPlayer=getComputerPlayer();
          }

    if(choice==1)
    j1=new JudgeHex();
    else j1=new JudgeSquare();

    this.playGame(choice);

    
  }



private void PrintInitialForamat(int choice){
  int row=this.getDepthOfBoard();
  int col=row;
  if(choice==2){
    int index=0;
  System.out.println("Enter Cell no in wrt following format ");
      for(int i=0;i<row;i++){
        for(int j=0;j<col;j++){
          System.out.print(index+" ");
          index++;
        }
        System.out.println();
      }}

      else{
            
            System.out.println("Enter Cell no in wrt following format ");

            int index=1;



            for(int i=0;i<row;i++) 
            {
             boolean flag=true;
            for(int j=0;j<row;j++) 
                {   
                  if(i%2==0){
                    if(flag){System.out.print("* ");}
                    else{System.out.print(index+" ");}}
                 

                 else{
                  if(flag){System.out.print(index+" ");}
                    else{System.out.print("* ");}}

                  flag=!flag;index++;
                }
                  System.out.println();
            
            }
      
      }        
      

}
  public void playGame(int ch1)

  {  
      String s;
      int pos[]=new int[2];


        // Flag is used to know if any one has won

    boolean f1=false;

      while(true){ 

      int row=b1.getDepth();
      int col=row;
     int depth=row;
         Player whoseTurn=firstPlayer;

      boolean chance =true;   // which Player has its turn 
      boolean flag=false;
             
      int s1=0;
      int s2=0;
        System.out.println();System.out.println();
        int index=1;
      PrintInitialForamat(ch1);



      s="Initial Board Status is: ";
      this.printMessage(s);

      this.printBoardStatus();

      while(!this.checkIfBoardIsFull())
      {

            s="Now "+whoseTurn.getTitle()+" Will Move";
            this.printMessage(s);
            while(true){
            pos=this.nextMove(whoseTurn);

            if(checkIfThisIsValidPosition(pos)){
                break;
            }}
            char ch='O';
            
            if(!chance) 
            {
              ch='X';
            }
            this.printMessage(whoseTurn,pos,ch);
            this.updateBoard(pos,ch);

            //Bias
            boolean ff=false;

            if(whoseTurn instanceof Human ){
              s="Do you want to reverse? enter 1";
              this.printMessage(s);
              int i= this.getUserIntegerInput();
              if(i==1){ff=true;

                ch='_';
              this.updateBoard(pos,ch);
            }}
            if(!ff){
            if(chance)
            {      
              whoseTurn=secondPlayer;
            }

            else
            {
                whoseTurn=firstPlayer;
            } 

              chance=!chance;}
              this.printBoardStatus();



            if(this.checkIfGameIsOver(pos,ch1))
            {  f1=true;
                  if(whoseTurn.equals(firstPlayer))
                  { s2+=depth;
                     this.declareWinner(secondPlayer);
                     this.saveToLeaderBoard(secondPlayer,s2);
                   }
                  else
                  { s1+=depth;
                   j1.declareWinner(firstPlayer); 
                   this.saveToLeaderBoard(firstPlayer, s1);
                 }
                  flag=true;  //set true if any one has won the game
                  break;
            }

       }

       if(flag){ b1.reInitBoard(depth*depth,depth);}
       else{break;}

     }
          if(!f1){
          System.out.println("Game Draw");
          }
    }


    


    public void exitGame()
    {

      System.exit(0);
    }


    public void printBoardStatus()
    { 
      int row=b1.getDepth();
      int col=row;
      for(int i=0;i<row;i++)
      {
        for(int j=0;j<col;j++)
        {
          System.out.print(b1.getCharAtPosition(i,j)+" ");
        }
        System.out.println();
      }

    }

  private BoardInterface getBoard(int n,int choice){
        return Board.getBoard(n,choice);
  }

  private Human getHumanPlayer(int n)
  {return new Human(this,n);}

  private Computer getComputerPlayer()
  {return new Computer(this);}

  private JudgeInterface getJudge(){
    return new JudgeSquare();
  }


  public int[] getFreeCellOfBoard(){
    return this.b1.getEmpty();
  }

  private void updateBoard(int[]pos,char ch){
    this.b1.updateBoard(pos[0],pos[1],ch); 
  }

  private int[] nextMove(Player p1){
    return p1.nextMove();
  }

  private void printMessage(Player whoseTurn,int []pos,char ch){


    int row=this.getDepthOfBoard();
    int cell= row*pos[0]+pos[1];
  String s=whoseTurn.getTitle()+" has put "+ch+" on cell "+(cell+1);
  u1.showMessageToUser(s);
  }
  private void printMessage(String s){
    u1.showMessageToUser(s);
  }


  private boolean checkIfBoardIsFull(){
    return this.b1.isFull();
  }

  private boolean checkIfGameIsOver(int pos[],int choice){




       
    if(choice==1){
      return  j1.gameOver(b1.getBoardPositions(),pos[0],pos[1]);
    }

    return j1.gameOver(b1.getBoardPositions(),b1.getBasicUnit(),b1.getDepth() );
  }

  private void declareWinner(Player p1){
    j1.declareWinner(p1);
  }

  private int getUserIntegerInput(){return u1.intInput();}

  private boolean checkIfThisIsValidPosition(int[] pos){
    return this.b1.isValid(pos[0],pos[1]);
  }

  public int getDepthOfBoard(){
    return b1.getDepth();
  }

  private void saveToLeaderBoard(Player p1,int score) {

    try{
   BufferedWriter out = new BufferedWriter( 
                   new FileWriter(path, true)); 
            out.write(p1.getTitle()+" "+score); 
            out.close();}

            catch(Exception e){}
  }


 public String getPath(){return this.path;}
} 



//first interface user will contact with this interface only to start the game.

interface PrimaryControlManagerInterface{
  void startNewGame(int i,String path);
    void exitGame();
}
interface ControlManagerInterface extends PrimaryControlManagerInterface{
    void showInfo(String path);
    void showLeaderBoard(String path);

    
 //   ControlManager getControlManager();
}

class ControlManager implements ControlManagerInterface{
    private GameManagerInterface configMI;
    private static ControlManager gm1=null;
    
    private UserInteractionInterface u1;
    private ControlManager(){

    }
    public static ControlManagerInterface getControlManager(){
            if(gm1==null){gm1=new ControlManager();
                        
    }        return gm1;
    }



    public void startNewGame(int choice,String path){
      
      configMI=GameManager.getGameManager();
    
     
    this.configMI.configureGame(choice,path);
    

  }
    public void exitGame(){System.exit(0);}

    public void showInfo(String path){
      
       u1=new UserInteraction();
       String s;
       int choice=0;
      while(true){
          s="Enter 1 for Two Player Game , 2 for Human-Computer Game, 3 for exit, 4 to print leaderBoard";
          this.printMessage(s);
         
          choice=this.getUserIntegerInput();

          if(choice==1 || choice==2 || choice==3 || choice==4){break;}
         
        }

         // System.out.println(choice); 
      if(choice==1 || choice==2){
       this.startNewGame(choice,path);

      }
      else if(choice==3){
        this.exitGame();

      }
      else{
        this.showLeaderBoard(path);

      }


    }

     private void printMessage(String s){
    u1.showMessageToUser(s);
  }
    private int getUserIntegerInput(){return u1.intInput();}

  public  void showLeaderBoard(String p1){
    //  String p1=configMI.getPath();
       System.out.println("Previous leaders are");
       try{
      BufferedReader br = new BufferedReader(new FileReader(p1)); 
  
  String st; 
  while ((st = br.readLine()) != null) 
    System.out.println(st); 
  
      //System.out.println(p1);
    }
    
    catch(Exception e){}
}
}


public class Main{

    public static void main(String[] args) {

        String s="/Users/riya.kathil/Desktop/Text1.txt";
        ControlManagerInterface g1;
        g1=ControlManager.getControlManager();
        g1.showInfo(s);


    }
}
