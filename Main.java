//package com.company;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.HashMap;
import  java.util.*;
import java.io.*;

//user input
interface userInput{
    String getInput();
   // int convertToInt(String s);
    //String convertToDouble(String s);
}

class HandleUserInput implements  userInput{
    private Scanner obj;
    HandleUserInput(){ obj=new Scanner((System.in));}

   public String getInput(){return obj.next();}


}


//Tiles
interface tile{

    boolean getStatus();
    void changeStatus();
}

class singleTile implements tile{
    private ArrayList<Integer> dimension;
    private  boolean status;
    singleTile(int n){
      //  int num=l1.length;
        dimension=new ArrayList<>(Collections.nCopies(n,0));
        this.status=false;
    }
 public   boolean getStatus(){
        return this.status;
    }

public  void changeStatus(){
        this.status=true;
    }

}


//Tile event handler
interface tileEventHandlerManager{
void changeStatus(tile t1);
boolean getStatus(tile t1);
}



class tileEventHandler implements  tileEventHandlerManager{
 public void changeStatus(tile t1){
     t1.changeStatus();
 }
 public boolean getStatus(tile t1){
return t1.getStatus();
 }
}


//Board


interface boardInterface{
    boolean isEmpty(Board b);
    boolean isFull(Board b);
    void changeStatus(Board b,tile t1);
   // Board getBoard(int n,int m);
}

class Board implements  boardInterface{
    private ArrayList<ArrayList<singleTile>> arr;
    private int row;
    private int col;
    private tileEventHandlerManager tileEHM;
    private static Board b1=null;

    //private constructor becuase this is singleton class
    private Board(int n,int m,int dim){
        arr = new ArrayList<ArrayList<singleTile>>(n);
        for(int i=0;i<n;i++) arr.set(i, new ArrayList<singleTile>(m));

        for(int i=0;i<n;i++) for(int j=0;j<m;j++) arr.get(i).set(j, new singleTile(dim));
      tileEHM =new tileEventHandler();
    }



    //get Board method
    public static Board getBoard(int n,int m,int dim){
       if(Objects.isNull(b1)){ b1=new Board(n,m,dim); }
       return b1;
    }
   public boolean isEmpty(Board b){

   for(int i=0;i<row;i++){
        for(int j=0;j<col;j++){ if(b.tileEHM.getStatus(arr.get(i).get(j))==false) {return  true; };
   }
    }
   return false;

}
    public boolean isFull(Board b){

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){ if(b.tileEHM.getStatus(arr.get(i).get(j))) {return  false; };
            }
        }
        return true;

    }


    public void changeStatus(Board b,tile t1){
        b.tileEHM.changeStatus(t1);

    }

}


//second interface  (game manager will talk to config manager)
interface configurationManagerInterface{
  
    void startGame();
    void exitGame();
  //  boardManager getBoard(); //BoardManager
    //void getRules(); //JudgeManager
    //void getPlayerTeam();  // playerTeamManager
    //void playGame(); //PlayGameManager  -- start game

}

class configurationManager implements configurationManagerInterface{
  
  private Board b1;
  userInput u1;
  private void configureGame()
  {
       System.out.println("Enter no of rows in game");
   int rows= Integer.parseInt(u1.getInput());
   System.out.println("Enter no of columns in game");
   int cols= Integer.parseInt(u1.getInput());


    System.out.println("Enter no of dimensions in game");
    int dim= Integer.parseInt(u1.getInput());    

   System.out.println("Enter no of players in game");
    int noOfPlayers=Integer.parseInt(u1.getInput());

    ArrayList<Integer> l1=new ArrayList<>();
    for(int i=0;i<noOfPlayers;i++){
        System.out.println("enter 0 if "+i+" current player is human");
        int choice=Integer.parseInt(u1.getInput());
        if(choice==0){ l1.add(0);}
        else l1.add(1);
    }

    b1.getBoard(rows,cols,dim);
    
  }
public  void startGame()
  { 


  
configureGame();


  }


 public void exitGame(){

  }
} 

interface playerType{
    void getPlayerType();
}


//first interface user will contact with this interface only to start the game.
interface gameManagerInterface{
    void startNewGame(gameManager gm1);
    void exitGame();
 //   gameManager getGameManager();
}

class gameManager implements gameManagerInterface{
    private configurationManagerInterface configMI;
    private static gameManager gm1=null;
    private gameManager(){

    }
    public static gameManager getGameManager(){
            if(gm1==null){gm1=new gameManager();}
            return gm1;
    }

    public void startNewGame(gameManager gm1){
                    gm1.configMI.startGame();
    }

    public void exitGame(){System.exit(0);}

}



interface  playGameManager
{

    String getTurn();
    void startGame();
}





public class Main {

    public static void main(String[] args) {
    }
}
