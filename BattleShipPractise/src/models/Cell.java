package models;

public class Cell {

    private Ship ship;
    private boolean isHit;
    private String player;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }

    public synchronized boolean fire(){

        if(ship!=null) {
            ship.hit();
            return true;
        }
        return false;
    }
}
