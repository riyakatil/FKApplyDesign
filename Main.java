

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


//------------------------------------------------------------------------------------------------------------------------

// Exhibits Judge Behaviour
interface JudgeInterface
{
  void declareWinner(Player p1);
  boolean gameOver(ArrayList<cellInterface[]> b1,int a,int b);
}



class JudgeHex implements JudgeInterface{

  public void declareWinner(Player p1){
    System.out.println(p1.getTitle()+" has won");
  }

public boolean gameOver(ArrayList<cellInterface[]> ar1,int x,int y) 
{
       
      boolean f1=diagonalCrossedHex(ar1,x,y);
     
      boolean f3=  columnCrossedHex(ar1,x,y);
   
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

 

private boolean diagonalCrossedHex(ArrayList<cellInterface[]> ar1,int x,int y)
{
 int row=ar1.size();


int x_diag,y_diag,x_antiDiag,y_antiDiag;
x_diag=y_diag=x_antiDiag=y_antiDiag=0;


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
  a1.add(ar1.get(cur_x)[cur_y].getChar());
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
  a1.add(ar1.get(cur_x)[cur_y].getChar());
  cur_x++;
  cur_y--;
}

 countX=consecutiveChar(a1,'X');
 countO=consecutiveChar(a1,'O');

if(countX>=4 || countO>=4){return true;}


return false;
}


private boolean columnCrossedHex(ArrayList<cellInterface[]> ar1,int x,int y){

int cur_x=0;
int cur_y=y;
int row=ar1.size();

ArrayList<Character> a1=new ArrayList<Character>();

while(cur_x<row && cur_y<row){
  a1.add(ar1.get(cur_x)[cur_y].getChar());
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

  public boolean gameOver(ArrayList<cellInterface[]> ar1,int basicUnit,int b)   // It checks in row,column,diagonal for win condition
{   
     

     if(ar1.size()==basicUnit && basicUnit==ar1.get(0).length){

      boolean f1=diagonalCrossed(ar1);
      boolean f2= rowCrossed(ar1);
      boolean f3=  columnCrossed(ar1);
    
      return f1||f2||f3;
     }

    boolean t1=rowCrossed(ar1,basicUnit);
   boolean t2= columnCrossed(ar1,basicUnit);
     boolean t3=diagonalCrossed(ar1,basicUnit) ; 
      return t1 || t2 || t3;

}


//row crosswed

private boolean rowCrossed(ArrayList<cellInterface[]> ar1,int basicUnit){

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


  ArrayList<cellInterface[]> arr=new ArrayList<cellInterface[]>();
   arr = new ArrayList<cellInterface[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
           { arr.add(new cellInterface[blockSize]);}



for(int i=0;i<depth;i=i+blockSize){

   int count=0;


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

private boolean columnCrossed(ArrayList<cellInterface[]> ar1,int basicUnit){

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


  ArrayList<cellInterface[]> arr=new ArrayList<cellInterface[]>();
   arr = new ArrayList<cellInterface[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
            arr.add(new cellInterface[blockSize]);
  

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
private boolean diagonalCrossed(ArrayList<cellInterface[]> ar1,int basicUnit){

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

   ArrayList<cellInterface[]> arr=new ArrayList<cellInterface[]>();
   arr = new ArrayList<cellInterface[]>(blockSize);


  for(int i=0;i<blockSize;i++) 
            arr.add(new cellInterface[blockSize]);

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


private boolean diagonalCrossed(ArrayList<cellInterface[]> ar1) 
{

  //ArrayList<char[]> b1=tmp.arr;
  int row=ar1.size();
  int col=row;


  boolean flag=false;
   


int countO=0,countX=0;

    for (int i=0; i<row; i++) 
    {   
       

        
       if(ar1.get(i)[i].getChar()=='X'){countX++ ;}
       else if(ar1.get(i)[i].getChar()=='O'){countO++ ;}
      
     
     
    } 
      if(countX==row ||  countO==row) {flag=true; }
      countX=0;
      countO=0;

    for (int i=row-1; i>=0; i--) 
    {   
      

        
       if(ar1.get(row-1-i)[i].getChar()=='X'){countX++ ;}
       else if(ar1.get(row-1-i)[i].getChar()=='O'){countO++ ;}
      
     
     
    } 

     if(countX==row ||  countO==row) {flag=true; }
  

    
    return flag; 
}


private boolean columnCrossed(ArrayList<cellInterface[]> ar1) 
{ 
    int row=ar1.size();
  int col=row;

boolean flag=false;
    for (int i=0; i<col; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<row;j++){
        
       if(ar1.get(j)[i].getChar()=='X'){countX++ ;}
       else if(ar1.get(j)[i].getChar()=='O'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 


    
    return flag; 
} 

private boolean rowCrossed(ArrayList<cellInterface[]> ar1) 
{ 

    
  int row=ar1.size();
  int col=row;

boolean flag=false;
    for (int i=0; i<row; i++) 
    {   
      int countX=0,countO=0;
      for(int j=0;j<col;j++){
        
       if(ar1.get(i)[j].getChar()=='X'){countX++ ;}
       else if(ar1.get(i)[j].getChar()=='O'){countO++ ;}
      }
      if(countX==row ||  countO==row) {flag=true; break;}
     
    } 




    
    return flag;
} 
}


//----------------------------------------------------------------------------------------------------------------------------------------

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
  

  Human(GameManager cm1,int i){
    this.cm1=cm1;
    this.title="Human"+(i+1);
   }

  public void printBoardStatus(){
    cm1.printBoardStatus();
  }


public int[] nextMove(){

        int pos[]=new int[2];
        String s="Enter cell no for next move";
        cm1.printMessage(s);
        int cell_no=cm1.getUserIntegerInput();
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


//------------------------------------------------------------------------------------------------------------------

interface cellPrimaryInterface{
 // int[] getCoOrdinates();
   char getChar();
}

interface cellInterface extends cellPrimaryInterface{
  String getTitle();
}

class Squarecell implements cellInterface{
 
  private String title;
  private char ch;
  Squarecell(char ch){  
    this.title="SquareCell";
    this.ch=ch;
  }
  
  public String getTitle(){return this.title;}

  public char getChar(){return this.ch;}

  public void updateCell(char ch){ this.ch=ch;}

}

class HexCell implements cellInterface{

private String title;
  private char ch;
  HexCell(char ch){  
    this.title="HexCell";
    this.ch=ch;
  }
  
  public String getTitle(){return this.title;}

  public char getChar(){return this.ch;}

  public void updateCell(char ch){ this.ch=ch;}

}



//-------------------------------------------------------------------------------------------------------------------------------------

//BoardInterface is responsible to create instance of board type


interface BoardInterface 
{ 
  /*
    void updateBoard(int []coOrdinates,char ch);
     
    boolean isFull();

    int[] getEmpty();
    int getDepth();
    int getBasicUnit();

    boolean isValid(int i,int j);
    char getCharAtPosition(int i,int j);
    
    void reInitBoard(int n,int m);
    */


    void fillBoard();
   // ArrayList<cellInterface[]> getState();
    int getDepth();
    int  getBasicUnit();
    void reInitBoard(int n);
    void printInitialFormat();
    void updateBoard(int x,int y,char ch);
    boolean checkIfBoardIsFull();
    ArrayList<cellInterface[]> getBoardPositions();

    char getCharAtPosition(int x,int y);

}


class SquareBoard implements BoardInterface{

    private ArrayList<cellInterface[]> arr;
    private int depth;
    private int basicUnit;
    private static BoardInterface b1=null;
    


    private SquareBoard(int n)
    { 
       arr=new ArrayList<cellInterface[]>(n);
       this.depth=n;
       this.basicUnit=n;

       this.fillBoard();
       
       // this.arr=getSquareBoardMatrix(n);
      
    }

    //get Board method
    public static BoardInterface getSquareBoard(int n)
    {
       if(Objects.isNull(b1)){ b1=new SquareBoard(n); }
       return b1;
    }




    public void fillBoard()
    {


        for(int i=0;i<this.getDepth();i++) 
            arr.add(new cellInterface[this.getDepth()]);

          for(int i=0;i<this.getDepth();i++) 
            {
            for(int j=0;j<this.getDepth();j++) 
                { 
                  char ch='_';
                  cellInterface tmp=new Squarecell(ch);

                  arr.get(i)[j]=tmp;
                }
            }

                
    }

    public int getDepth(){
      return this.depth;
    }

    public int  getBasicUnit(){
      return this.basicUnit;
    }

    public void reInitBoard(int n){
      System.out.println("re");
        this.arr=new ArrayList<cellInterface[]>(n);
        this.depth=n;
       //this.basicUnit=n;
       this.fillBoard();
       
    }

   public void printInitialFormat(){
  int row=this.getDepth();

     int index=1;
  System.out.println("Enter Cell no in wrt following format ");
      for(int i=0;i<row;i++){
        for(int j=0;j<row;j++){
          System.out.print(index+" ");
          index++;
        }
        System.out.println();
      }}


 public void updateBoard(int x,int y,char ch){
        this.arr.get(x)[y]=new Squarecell(ch);
    }
         
          public boolean checkIfBoardIsFull(){
       boolean flag=true;
        for(int i=0;i<this.getDepth();i++){
            for(int j=0;j<this.getDepth();j++){

            char tmp=arr.get(i)[j].getChar(); 
              if(tmp!='X' &&  tmp!='O' && tmp!='*') {flag=  false; };
            }
        }

        return flag;
    }  


    public ArrayList<cellInterface[]> getBoardPositions(){return this.arr;} 

    public char getCharAtPosition(int x,int y){
      return arr.get(x)[y].getChar();
    }
 
}



class HexBoard implements BoardInterface{

    private ArrayList<cellInterface[]> arr;
    private int depth;
    private int basicUnit;
    private static BoardInterface b1=null;
    


    private HexBoard(int n)
    { 
       arr=new ArrayList<cellInterface[]>(2*n);
        this.depth=2*n;
       this.basicUnit=2*n;
       this.fillBoard();
      

       // this.arr=getSquareBoardMatrix(n);
      
    }

    //get Board method
    public static BoardInterface getHexBoard(int n)
    {
       if(Objects.isNull(b1)){ b1=new HexBoard(n); }
       return b1;
    }




    public void fillBoard()
    {

        for(int i=0;i<this.getDepth();i++) 
            arr.add(new cellInterface[this.getDepth()]);

             char ch='*';
          for(int i=0;i<this.getDepth();i++) 
            { 
              boolean flag=true;
            for(int j=0;j<this.getDepth();j++) 
                { 
                  if(i%2==0){ if(flag){ch='*';} else {ch='_';}}
                  else{
                    if(flag){ch='_';} else {ch='*';}
                  } flag=!flag;

                  cellInterface tmp=new HexCell(ch);
                  arr.get(i)[j]=tmp;

                }
            }

              

    }

    public int getDepth(){
      return this.depth;
    }

    public int getBasicUnit(){
      return this.basicUnit;
    }

    public void reInitBoard(int n){
        this.arr=new ArrayList<cellInterface[]>(2*n);
        this.depth=2*n;
      // this.basicUnit=n;
       this.fillBoard();
       
    }


 public void printInitialFormat(){
      int row=this.getDepth();

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
            }}


            public void updateBoard(int x,int y,char ch){
        this.arr.get(x)[y]=new HexCell(ch);
    }

    public boolean checkIfBoardIsFull(){
       boolean flag=true;
        for(int i=0;i<this.getDepth();i++){
            for(int j=0;j<this.getDepth();j++){

            char tmp=arr.get(i)[j].getChar(); 
              if(tmp!='X' &&  tmp!='O' && tmp!='*') {flag=  false; };
            }
        }

        return flag;
    }
    

    public ArrayList<cellInterface[]> getBoardPositions(){return this.arr;}

    public char getCharAtPosition(int x,int y){
      return arr.get(x)[y].getChar();
    }
 

   }







//--------------------------------------------------------------------------------------------------------------------------


//second interface  (game manager will talk to Play GameManager)
// Board, Player,Judge all will communicate with each other using this class only.

interface PrimaryGameManagerInterface{
 void playGame();
   void configureGame(int gameChoice,int cellChoice,int dimension,String pathToStoreWinner);


}
interface GameManagerInterface extends PrimaryGameManagerInterface{
  

    void exitGame();
    int getDepthOfBoard();
   
    void printBoardStatus();
    int[]getFreeCellOfBoard();
     
 
     String getPath();
      void printMessage(String s);
      int getUserIntegerInput();
}

class GameManager implements GameManagerInterface {
  
  private BoardInterface b1;
  private String path;
  private Player firstPlayer;
  private Player secondPlayer;
  private JudgeInterface j1;
  private static GameManagerInterface cm1;
  private int basicUnit;
  private int depth;
  private UserInteractionInterface u1;

  private GameManager(){}

  public static GameManagerInterface getGameManager(){
    if(cm1==null){cm1=new GameManager();}
    return cm1;
  }



  public void configureGame(int gameChoice,int cellChoice,int basicUnit, String path)
    {   
        this.path=path;

        this.basicUnit=basicUnit;

          u1=new UserInteraction();

         b1=this.getBoard(cellChoice,basicUnit);
        
        Player[] tmp=getPlayer(gameChoice);
      firstPlayer=tmp[0];
      secondPlayer=tmp[1];

       j1=getJudge(cellChoice);

    this.playGame();

    
  }

  private BoardInterface getBoard(int cellChoice,int basicUnit){

    if(cellChoice==1){
      return HexBoard.getHexBoard(basicUnit);
    }
        return SquareBoard.getSquareBoard(basicUnit);
  }



  private Player[] getPlayer(int choice){
    Player[] tmp=new Player[2];
    if(choice==1)
    {
        tmp[0]=getHumanPlayer(0);
        tmp[1]=getHumanPlayer(1);
    }
    else{
          tmp[0]=getHumanPlayer(0);
          tmp[1]=getComputerPlayer();
    }
    return tmp;
  }




            private Human getHumanPlayer(int n)
            {return new Human(this,n);}

            private Computer getComputerPlayer()
            {return new Computer(this);}




            private JudgeInterface getJudge(int choice){
              JudgeInterface tmp;
              if(choice==1)
              tmp=new JudgeHex();
              else tmp=new JudgeSquare();

              return tmp;
            }



  public void playGame()

  {  
      String s;
      int pos[]=new int[2];


        // Flag is used to know if any one has won

        boolean f1=false;

          while(true){ 

        int row=this.getDepthOfBoard();
     
        Player whoseTurn=firstPlayer;

          boolean chance =true;   // which Player has its turn 
          boolean flag=false;
             
          int s1=0;  //Scores
          int s2=0;
        System.out.println();
        System.out.println();
        int index=1;

        this.printInitialFormat();



           s="Initial Board Status is: ";
            this.printMessage(s);

          this.printBoardStatus();

      while(!this.checkIfBoardIsFull())
      {

            s="Now "+whoseTurn.getTitle()+" Will Move";
            this.printMessage(s);
            while(true){
            pos=this.nextMove(whoseTurn);

            boolean t1=checkIfThisIsValidPosition(pos);
            if(!t1)
              { 
                s="Please enter valid cell no";
                this.printMessage(s);
             }
              else{ break;}
            }


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



            if(this.checkIfGameIsOver(pos))
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

       if(flag){
       b1.reInitBoard(row*row);}
       else{break;}

     }
          if(!f1){
          System.out.println("Game Draw");
          }
    }



//depth of board

  public int getDepthOfBoard(){
    return b1.getDepth();
  }

//Initial format
      public void printInitialFormat(){
        b1.printInitialFormat();
      }

    
//Board Status
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

    public char getCharAtPosition(int i,int j){
      return (char)this.b1.getCharAtPosition(i,j);
    }

//check board is full

  private boolean checkIfBoardIsFull(){

    return b1.checkIfBoardIsFull();
   

  }


//print message
  private void printMessage(Player whoseTurn,int []pos,char ch){


    int row=this.getDepthOfBoard();
    int cell= row*pos[0]+pos[1];
  String s=whoseTurn.getTitle()+" has put "+ch+" on cell "+(cell+1);
  u1.showMessageToUser(s);
  }

  public void printMessage(String s){
    u1.showMessageToUser(s);
  }



//next Move
 private int[] nextMove(Player p1){
    return p1.nextMove();
  }


   //valid position

  private boolean checkIfThisIsValidPosition(int[] pos){
    int i=pos[0];
    int j=pos[1];
    char tmp=getCharAtPosition(i,j);
if(i>=0 && j>=0 && i<this.getDepthOfBoard() && j<this.getDepthOfBoard() && tmp!='X' && tmp!='O'  && tmp!='*') {return true;}
      return false;

  } 


//Free cell Of Board
  public int[] getFreeCellOfBoard(){

    int [] pos=new int[2];
      boolean flag=false;
      for(int i=0;i<this.getDepthOfBoard();i++)
      {
        for(int j=0;j<this.getDepthOfBoard();j++)
        {
          char tmp=getCharAtPosition(i,j);
            if(tmp!='X' && tmp!='O' && tmp!='*' )
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

//update
private void updateBoard(int[]pos,char ch){


    this.b1.updateBoard(pos[0],pos[1],ch); 
  }

  
 

  private boolean checkIfGameIsOver(int pos[]){


      if(j1 instanceof JudgeHex){
        return  j1.gameOver(b1.getBoardPositions(),pos[0],pos[1]); 
      }

       
    
    return j1.gameOver(b1.getBoardPositions(),b1.getBasicUnit(),b1.getDepth() );
  }

  private void declareWinner(Player p1){
    j1.declareWinner(p1);
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


 public int getUserIntegerInput(){return u1.intInput();}

 public void exitGame()
 {
  System.exit(0);
 }

} 



//----------------------------------------------------------------------------------------------------------------------------

//first interface user will contact with this interface only to start the game.

interface PrimaryControlManagerInterface{
  void startNewGame(int gameChoice,int cellChoice,int dimension, String pathToStoreWinner);
    void exitGame();
}
interface ControlManagerInterface extends PrimaryControlManagerInterface{
    void showInfo(String path);
    void showLeaderBoard(String path);

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



    public void startNewGame(int gameChoice,int cellChoice,int dimension,String path){
      
      configMI=GameManager.getGameManager();
    
     
    this.configMI.configureGame(gameChoice,cellChoice,dimension, path);
    

  }
    public void exitGame(){System.exit(0);}

    public void showInfo(String path){
      
       u1=new UserInteraction();
       String s;
       int choice=0,choice1=0;
      while(true){
          s="Enter 1 for Two Player Game , 2 for Human-Computer Game, 3 for exit, 4 to print leaderBoard";
          this.printMessage(s);
         
          choice=this.getUserIntegerInput();

          if(choice==1 || choice==2 || choice==3 || choice==4){break;}
         
        }

        
      if(choice==1 || choice==2){

        while(true){
        s="Enter 1 HexCell and 2 for Square Cell, 3 for exit";
        this.printMessage(s);
         
          choice1=this.getUserIntegerInput();

          if(choice1==1 || choice1==2 || choice1==3){break;}
          s="Enter valid choice!!!";
          this.printMessage(s);
            }
       
          if(choice1==3){this.exitGame();}
        
          s="Enter Board Dimension";
          this.printMessage(s);
          int dim=this.getUserIntegerInput();

          this.startNewGame(choice,choice1,dim,path);

    }
      else if(choice==3){
        this.exitGame();

      }
      else{
        this.showLeaderBoard(path);

      }


    }

     public void printMessage(String s){
    u1.showMessageToUser(s);
  }
    private int getUserIntegerInput(){return u1.intInput();}

      public  void showLeaderBoard(String p1){
  
       System.out.println("Previous leaders are");
       try{
      BufferedReader br = new BufferedReader(new FileReader(p1)); 
  
  String st; 
  while ((st = br.readLine()) != null) 
    System.out.println(st); 
  
      
    }
    
    catch(Exception e){String s="Error in Reading"; printMessage(s);}
}
}


//-------------------------------------------------------------------------------------------

public class Main{

    public static void main(String[] args) {

        String s="/Users/riya.kathil/Desktop/Text1.txt";
        ControlManagerInterface g1;
        g1=ControlManager.getControlManager();
        g1.showInfo(s);


    }
}
