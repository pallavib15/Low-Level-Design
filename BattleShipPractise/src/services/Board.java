package services;

import models.Cell;
import models.Ship;

public class Board {
    private Cell[][] grid;
    private int n;
    public int remainingShipsA;
    public int remainingShipsB;


    public Board(int n) {
        this.n = n;
        this.remainingShipsA = 0;
        this.remainingShipsB = 0;
        grid = new Cell[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getSize(){
        return n;
    }

    public void placeShip(Ship ship, int x, int y,String player){
        int half=ship.getSize()/2;

        for(int i=x-half;i<x+half;i++){
            for(int j=y-half;j<y+half;j++){
                if(i<0||j<0||i>=n||j>=n){
                    return;
                }
                if(grid[i][j].getShip()!=null){
                    throw  new IllegalArgumentException("already present");
                }

                grid[i][j].setShip(ship);
                grid[i][j].setPlayer(player);
                if(player=="A"){
                    remainingShipsA++;
                }
                else{
                    remainingShipsB++;
                }
            }
        }
    }

    public boolean fire(int x, int y){
        return grid[x][y].fire();
    }

    public boolean allShipsDestroyed(String player){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j].getPlayer().equals(player) && grid[i][j].getShip()!=null
                && grid[i][j].getShip().isDestroyed() ){
                    return true;
                }
            }
        }
        return  false;
    }





}
