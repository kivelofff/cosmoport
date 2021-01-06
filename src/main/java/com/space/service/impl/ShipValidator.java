package com.space.service.impl;

import com.space.model.Ship;

import java.util.Calendar;
import java.util.Date;

public class ShipValidator {


    public static boolean validate(Ship ship) {

        String name = ship.getName();
        boolean isNameValid = (name != null && name.length()>0 && name.length()<=50);
        String planet = ship.getPlanet();
        boolean isPlanetValid = (planet != null && planet.length()>0 && planet.length()<=50);
        Calendar shipProdDate = Calendar.getInstance();
        shipProdDate.setTime(ship.getProdDate());
        boolean isProdDateValid = (shipProdDate.get(Calendar.YEAR) >= 2800 && shipProdDate.get(Calendar.YEAR) <= 3800);

        return isNameValid && isPlanetValid && isProdDateValid;
    }
}
