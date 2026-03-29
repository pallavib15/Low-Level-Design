package models;

public class Ship {

    private int id;
    private int size;
    private int health;

    public Ship(int id,int size){
        this.id=id;
        this.size=size;
        this.health=size*size;
    }

    public void hit(){
        health--;
    }

    public boolean isDestroyed(){
        return health<=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
