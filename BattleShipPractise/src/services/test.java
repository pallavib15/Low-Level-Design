package services;

import models.Ship;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class test {

    @Test
    public void testGame(){
        Board b=new Board(6);
        Ship ship = new Ship(1, 2);

        b.placeShip(ship, 2, 1, "A");

        assertNotNull(b.getGrid()[2][1].getShip());

    }



}
