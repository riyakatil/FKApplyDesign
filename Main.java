

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
       else if(b1.getCharAtPosition(i,i)=='0'){countO++ ;}
      
     
     
    } 
      if(countX==row ||  countO==row) {flag=true; }
      countX=0;
      countO=0;

    for (int i=row-1; i>=0; i--) 
    {   
      

        
       if(b1.getCharAtPosition(row-1-i,i)=='X'){countX++ ;}
       else if(b1.getCharAtPosition(row-1-i,i)=='0'){countO++ ;}
      
     
     
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
       else if(b1.getCharAtPosition(j,i)=='0'){countO++ ;}
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
       else if(b1.getCharAtPosition(i,j)=='0'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 




    
    return flag;
} 
}



// Every Player can check board status , will move and has its own title

interface Player{
  
  void getBoardStatus();
  int[] nextMove();

  String getTitle();

}


class Human implements Player{
  private String title;
  private ConfigurationManager cm1;
  private UserInteractionInterface u1;

  Human(ConfigurationManager cm1,int i){
    this.cm1=cm1;
    this.title="Human"+(i+1);
    this.u1=new UserInteraction();
  }

  public void getBoardStatus(){
    cm1.getBoardStatus();
  }


public int[] nextMove(){

        int pos[]=new int[2];
        String s="Enter x";
        u1.showMessageToUser(s);
        int x=u1.intInput(); 

        s="Enter y";
         u1.showMessageToUser(s);
       int y=u1.intInput();
       pos[0]=x;
       pos[1]=y;
return pos;
}

public String getTitle(){return this.title;}



}

class Computer implements Player
{ private String title;
       private ConfigurationManager cm1;

  Computer(ConfigurationManager cm1){
    this.cm1=cm1;
    this.title="Computer";

  }

  public void getBoardStatus(){
    cm1.getBoardStatus();
  }

public int[] nextMove(){
  
return this.cm1.getFreeCellOfBoard();
  

}

public String getTitle(){return this.title;}


}






//BoardInterface is responsible for any activity related to Board


interface BoardInterface
{
     boolean isEmpty();
    boolean isFull();

    int[] getEmpty();
    int getRow();
    int getCol();

  void updateBoard(int x,int y,char ch);
  boolean isValid(int i,int j);
  char getCharAtPosition(int i,int j);
}


// Every board can be only one - singleton class
class Board implements  BoardInterface{
    public ArrayList<char[]> arr;
    private int row;
    private int col;

    private static Board b1=null;


    private Board(int n,int m)
    {


        arr = new ArrayList<char[]>(n);


  for(int i=0;i<n;i++) 
            arr.add(new char[m]);

 

        for(int i=0;i<n;i++) 
            for(int j=0;j<m;j++) 
                arr.get(i)[j]='_';

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
             if(this.arr.get(i)[j]=='_') 
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
              if(this.arr.get(i)[j]=='_') {flag=  false; };
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
            if(this.arr.get(i)[j]=='_')
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
      if(i>=0 && j>=0 && i<this.getRow() && j<this.getCol() && this.arr.get(i)[j]=='_'){return true;}
      return false;
    }


    // get current pos of board
    public char getCharAtPosition(int i,int j){
      return this.arr.get(i)[j];
    }

}


//second interface  (game manager will talk to config manager)
interface ConfigurationManagerInterface{
  
    void startGame();
    void exitGame();


}

class ConfigurationManager implements ConfigurationManagerInterface{
  
  private BoardInterface b1;

  private Player firstPlayer;
  private Player secondPlayer;
  private JudgeInterface j1;

  private UserInteractionInterface u1;
  private void configureGame()
  {
       

         u1=new UserInteraction();
         int choice,row,col;
         String s;


         while(true){
          s="Enter 1 for Two Player Game , 2 for Human-Computer Game, 3 for exit";
          this.printMessage(s);
         
          choice=this.getUserIntegerInput();
          if(choice==1 || choice==2 || choice==3){break;}
        }

        if(choice==3){this.exitGame();}


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


       if(choice==1){
             firstPlayer=getHumanPlayer(0);
             secondPlayer=getHumanPlayer(1);}
    else{
          firstPlayer=getHumanPlayer(0);
          secondPlayer=getComputerPlayer();
          }

    
    //b1.getBoard(rows,cols,dim);
    j1=getJudge();
    this.playGame();
    
  }


  private void playGame()

  {  
      String s;
      int pos[]=new int[2];

      int row=b1.getRow();
      int col=b1.getCol();
     

      Player whoseTurn=firstPlayer;

      boolean chance =true;   // which Player has its turn 
      boolean flag=false;   // Flag is used to know if any one has won


      s="Initial Board Status is: ";
      this.printMessage(s);

      this.getBoardStatus();

      while(!this.checkIfBoardIsFull())
      {

            s="Now "+whoseTurn.getTitle()+" Will Move";
            this.printMessage(s);
            while(true){
            pos=this.nextMove(whoseTurn);

            if(checkIfThisIsValidPosition(pos)){
                break;
            }}
            char ch='0';
            
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
              this.getBoardStatus();



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


    public  void startGame()
      {   
        configureGame();
      }


    public void exitGame()
    {

      System.exit(0);
    }


    public void getBoardStatus()
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

  String s=whoseTurn.getTitle()+" has put "+ch+" on position ("+pos[0]+","+pos[1]+")";
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
} 



//first interface user will contact with this interface only to start the game.
interface gameManagerInterface{
    void showInfo();
    void startNewGame();
    void exitGame();
 //   gameManager getGameManager();
}

class gameManager implements gameManagerInterface{
    private ConfigurationManager configMI;
    private static gameManager gm1=null;
    
    UserInteractionInterface u1;
    private gameManager(){

    }
    public static gameManager getGameManager(){
            if(gm1==null){gm1=new gameManager();
                        
    }        return gm1;
    }



    public void startNewGame(){
      
      configMI=new ConfigurationManager();
    
     
    this.configMI.startGame();

  }
    public void exitGame(){System.exit(0);}

    public void showInfo(){
      
       u1=new UserInteraction();
      String s;
        int choice=0;
      while(true){
      s="Enter 1 to start the game , 2 for exit";
      u1.showMessageToUser(s);

    
       choice=u1.intInput();
        if(choice==1 || choice ==2){break;}
        }
      if(choice==1){
        this.startNewGame();

      }
      else{
        this.exitGame();

      }


    }
}








public class Main {

    public static void main(String[] args) {

        gameManager g1;
        g1=gameManager.getGameManager();
        g1.showInfo();


    }
}
