

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
  boolean gameOver(BoardInterface b1);
}



class Judge implements JudgeInterface
{


  public void declareWinner(Player p1)     
{ 
    System.out.println(p1.getTitle()+" has won");
} 

  public boolean gameOver(BoardInterface b1)   // It checks in row,column,diagonal for win condition
{   
  
    return(rowCrossed(b1) || columnCrossed(b1) 
            || diagonalCrossed(b1) ); 
}





private boolean diagonalCrossed(BoardInterface b1) 
{ 

  //ArrayList<char[]> b1=tmp.arr;
  int row=b1.getRow();
  int col=b1.getCol();


  boolean flag=false;
   


int countO=0,countX=0;

    for (int i=0; i<row; i++) 
    {   
       

        
       if(b1.getCharAtPosition(i,i)=='X'){countX++ ;}
       else if(b1.getCharAtPosition(i,i)=='O'){countO++ ;}
      
     
     
    } 
      if(countX==row ||  countO==row) {flag=true; }
      countX=0;
      countO=0;

    for (int i=row-1; i>=0; i--) 
    {   
      

        
       if(b1.getCharAtPosition(row-1-i,i)=='X'){countX++ ;}
       else if(b1.getCharAtPosition(row-1-i,i)=='O'){countO++ ;}
      
     
     
    } 

     if(countX==row ||  countO==row) {flag=true; }
  

    
    return flag; 
} 

private boolean columnCrossed(BoardInterface b1) 
{ 
    int row=b1.getRow();
  int col=b1.getCol();

boolean flag=false;
    for (int i=0; i<col; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<row;j++){
        
       if(b1.getCharAtPosition(j,i)=='X'){countX++ ;}
       else if(b1.getCharAtPosition(j,i)=='O'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 


    
    return flag; 
} 

private boolean rowCrossed(BoardInterface b1) 
{ 

    
  int row=b1.getRow();
  int col=b1.getCol();

boolean flag=false;
    for (int i=0; i<row; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<col;j++){
        
       if(b1.getCharAtPosition(i,j)=='X'){countX++ ;}
       else if(b1.getCharAtPosition(i,j)=='O'){countO++ ;}
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
  private ConfigurationManagerInterface cm1;
  private UserInteractionInterface u1;

  Human(ConfigurationManager cm1,int i){
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
        int row=cm1.getRowOfBoard();
        
       pos[0]=cell_no/row;
       pos[1]=cell_no%row;
return pos;
}

public String getTitle(){return this.title;}



}

class Computer implements Player
{ private String title;
       private ConfigurationManagerInterface cm1;

  Computer(ConfigurationManager cm1){
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
    int getRow();
    int getCol();

  
  boolean isValid(int i,int j);
  char getCharAtPosition(int i,int j);
}


// Every board can be only one - singleton class
class Board implements  BoardInterface{
    private ArrayList<char[]> arr;
    private int row;
    private int col;

    private static Board b1=null;


    private Board(int n,int m)
    {


        arr = new ArrayList<char[]>(n);


  for(int i=0;i<n;i++) 
            arr.add(new char[m]);

      int cell_no=1;

        for(int i=0;i<n;i++) 
            {
            for(int j=0;j<m;j++) 
                { //char ch=(char)(cell_no+'0');
                  char ch='_';
                  arr.get(i)[j]=ch; cell_no++;}
            }

              this.row=n;
              this.col=m;
    }



    //get Board method
    public static BoardInterface getBoard(int n,int m)
    {
       if(Objects.isNull(b1)){ b1=new Board(n,m); }
       return b1;
    }



      //Board is empty
    public boolean isEmpty()
    {

       for(int i=0;i<row;i++)
       {
            for(int j=0;j<col;j++)
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
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){ 
              if(this.arr.get(i)[j]!='X' &&  this.arr.get(i)[j]!='O' ) {flag=  false; };
            }
        }

       // System.out.println(" board is "+flag);
        return flag;

    }


    //get no of rows
    public int getRow(){return this.row;}


    //get no of column
    public int getCol(){return this.col;}


    //get empty cell
    public int[] getEmpty()
    {
      int [] pos=new int[2];
      boolean flag=false;
      for(int i=0;i<row;i++)
      {
        for(int j=0;j<col;j++)
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
      if(i>=0 && j>=0 && i<this.getRow() && j<this.getCol() && this.arr.get(i)[j]!='X' && this.arr.get(i)[j]!='O' ) {return true;}
      return false;
    }


    // get current pos of board
    public char getCharAtPosition(int i,int j){
      return (char)this.arr.get(i)[j];
    }

}


//second interface  (game manager will talk to config manager)
// Board, Player,Judge all will communicate with each other using this class only.

interface PrimaryConfigurationManagerInterface{
 void playGame();
    void configureGame(int i);

}
interface ConfigurationManagerInterface extends PrimaryConfigurationManagerInterface{
  
    //void Game();
    void exitGame();
    int getRowOfBoard();
   
    void printBoardStatus();
    int[]getFreeCellOfBoard();
     void configureGame(int i);
     void playGame();


}

class ConfigurationManager implements ConfigurationManagerInterface{
  
  private BoardInterface b1;

  private Player firstPlayer;
  private Player secondPlayer;
  private JudgeInterface j1;
  private static ConfigurationManagerInterface cm1;

  private UserInteractionInterface u1;

  private ConfigurationManager(){}

  public static ConfigurationManagerInterface getConfigurationManager(){
    if(cm1==null){cm1=new ConfigurationManager();}
    return cm1;
  }

  public void configureGame(int i)
  {
       

         u1=new UserInteraction();
         int choice,row,col;
         String s;

        while(true){
          s="Enter Rows";
         this.printMessage(s);
          row=this.getUserIntegerInput();
         s="Enter No of columns";
         this.printMessage(s);
         col=this.getUserIntegerInput();

          if(row==col){break;}
        }
   
        b1=this.getBoard(row,col);


       if(i==1){
             firstPlayer=getHumanPlayer(0);
             secondPlayer=getHumanPlayer(1);}
    else{
          firstPlayer=getHumanPlayer(0);
          secondPlayer=getComputerPlayer();
          }

    
    j1=getJudge();

    
  }


  public void playGame()

  {  
      String s;
      int pos[]=new int[2];

      int row=b1.getRow();
      int col=b1.getCol();
     

      Player whoseTurn=firstPlayer;

      boolean chance =true;   // which Player has its turn 
      boolean flag=false;   // Flag is used to know if any one has won

      int index=1;
      System.out.println("Enter Cell no in wrt following format ");
      for(int i=0;i<row;i++){
        for(int j=0;j<col;j++){
          System.out.print(index+" ");
          index++;
        }
        System.out.println();
      }

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
            if(chance)
            {      
              whoseTurn=secondPlayer;
            }

            else
            {
                whoseTurn=firstPlayer;
            } 

              chance=!chance;
              this.printBoardStatus();



            if(this.checkIfGameIsOver())
            { 
                  if(whoseTurn.equals(firstPlayer))
                  {
                     this.declareWinner(secondPlayer);
                   }
                  else
                  {
                   j1.declareWinner(firstPlayer); 
                 }
                  flag=true;  //set true if any one has won the game
                  break;
            }

       }
          if(!flag){
          System.out.println("Game Draw");
          }
    }


    


    public void exitGame()
    {

      System.exit(0);
    }


    public void printBoardStatus()
    { 
      int row=b1.getRow();
      int col=b1.getCol();
      for(int i=0;i<row;i++)
      {
        for(int j=0;j<col;j++)
        {
          System.out.print(b1.getCharAtPosition(i,j)+" ");
        }
        System.out.println();
      }

    }

  private BoardInterface getBoard(int n,int m){
        return Board.getBoard(n,m);
  }

  private Human getHumanPlayer(int n)
  {return new Human(this,n);}

  private Computer getComputerPlayer()
  {return new Computer(this);}

  private JudgeInterface getJudge(){
    return new Judge();
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


    int row=this.getRowOfBoard();
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

  private boolean checkIfGameIsOver(){
    return j1.gameOver(b1);
  }

  private void declareWinner(Player p1){
    j1.declareWinner(p1);
  }

  private int getUserIntegerInput(){return u1.intInput();}

  private boolean checkIfThisIsValidPosition(int[] pos){
    return this.b1.isValid(pos[0],pos[1]);
  }

  public int getRowOfBoard(){
    return b1.getRow();
  }
} 



//first interface user will contact with this interface only to start the game.

interface PrimaryGameManagerInterface{
  void startNewGame(int i);
    void exitGame();
}
interface GameManagerInterface extends PrimaryGameManagerInterface{
    void showInfo();
    
 //   GameManager getGameManager();
}

class GameManager implements GameManagerInterface{
    private ConfigurationManagerInterface configMI;
    private static GameManager gm1=null;
    
    private UserInteractionInterface u1;
    private GameManager(){

    }
    public static GameManagerInterface getGameManager(){
            if(gm1==null){gm1=new GameManager();
                        
    }        return gm1;
    }



    public void startNewGame(int i){
      
      configMI=ConfigurationManager.getConfigurationManager();
    
     
    this.configMI.configureGame(i);
    this.configMI.playGame();

  }
    public void exitGame(){System.exit(0);}

    public void showInfo(){
      
       u1=new UserInteraction();
      String s;
        int choice=0;
      while(true){
          s="Enter 1 for Two Player Game , 2 for Human-Computer Game, 3 for exit";
          this.printMessage(s);
         
          choice=this.getUserIntegerInput();
          if(choice==1 || choice==2 || choice==3){break;}
        }


      if(choice==1 || choice==2){
        this.startNewGame(choice);

      }
      else {
        this.exitGame();

      }


    }

     private void printMessage(String s){
    u1.showMessageToUser(s);
  }
    private int getUserIntegerInput(){return u1.intInput();}


}


public class Main {

    public static void main(String[] args) {

        GameManagerInterface g1;
        g1=GameManager.getGameManager();
        g1.showInfo();


    }
}
