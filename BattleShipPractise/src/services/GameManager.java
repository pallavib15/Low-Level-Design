package services;

import models.Player;
import models.Ship;

public class GameManager {


    private Board board;
    private Player player1;
    private Player player2;
    private FiringStrategy firingStrategy;

    private GameManager(){

    }

    private static GameManager instance;

    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void initGame(int n){
        board = new Board(n);
        player1 = new Player();
        player1.setName("A");
        player2 = new Player();
        player2.setName("B");
        firingStrategy=new RandomFiringStrategy();
    }


    public void addShip(int id,int size,int ax,int ay,int bx,int by){
        if(ay>= board.getSize()/2){
            throw new IllegalArgumentException("ax out of bound");
        }
        if(by< board.getSize()/2){
            throw new IllegalArgumentException("bx out of bound");
        }

        Ship shipA = new Ship(id,size);
        Ship shipB=new Ship(id,size);


        board.placeShip(shipA,ax,ay,"A");
        board.placeShip(shipB,bx,by,"B");
    }

    public void startGame(){
        boolean isA=true;

        while(true){
            int[] moves=firingStrategy.getNextMoves(board,isA);

           boolean hit= board.fire(moves[0],moves[1]);

           if(hit){
               if(isA){
                   board.remainingShipsA-= board.remainingShipsA;
               }else{
                   board.remainingShipsB-= board.remainingShipsB;
               }
               System.out.println("Player"+(isA?"A":"B")+"'s ship has fired at"+moves[0]+" "+moves
               [1]+"hit");
           }else{
               System.out.println("Player"+(isA?"A":"B")+"'s ship has fired at"+moves[0]+" "+moves
                       [1]+"miss");
           }

           if(isA && board.remainingShipsB<=0){
               System.out.println("Player A wins the game");
                break;
           }else if(!isA && board.remainingShipsA<=0){
               System.out.println("Player B wins the game");
               break;
           }

           isA=!isA;


        }
    }









}
