package com.space.service.impl;

import com.space.model.Ship;
import junit.framework.TestCase;

import java.util.Date;

public class ShipServiceImplTest extends TestCase {

    
    public void testRaiting() {
        ShipServiceImpl service = new ShipServiceImpl();
        Ship ship = new Ship();
        Date prodDate = new Date(32945443200000L);
        ship.setProdDate(prodDate);
        System.out.println(ship.getProdDate());
        ship.setUsed(true);
        ship.setSpeed(0.88d);
        service.calculateRating(ship);
        assertEquals(5.87d, ship.getRating());
    }
}