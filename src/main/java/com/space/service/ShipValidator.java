package com.space.service;

import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.GregorianCalendar;
@Service
public class ShipValidator implements Validator  {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Ship.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Ship ship = (Ship) o;

        String shipName = ship.getName();
        if (shipName == null || shipName.length() > 50) {
            errors.reject("Name is too long or empty");
        }
        String shipPlanet = ship.getPlanet();
        if (shipPlanet == null || shipPlanet.length() > 50) {
            errors.reject("Planet name is too long or empty");
        }
        Calendar shipProdDate = new GregorianCalendar();
        shipProdDate.setTime(ship.getProdDate());
        Integer yearOfProd = shipProdDate.get(Calendar.YEAR);
        if (yearOfProd > 3019 || yearOfProd < 2800) {
            errors.reject("Year of production should be between 2800 and 3019");
        }
        Double shipSpeed = ship.getSpeed();
        if (shipSpeed < 0.01d || shipSpeed > 0.99d) {
            errors.reject("Speed should be between 0.01 and 0.99");
        }
        Integer shipCrewSize = ship.getCrewSize();
        if (shipCrewSize < 1 || shipCrewSize > 9999) {
            errors.reject("Crew size should be between 1 and 9999");
        }


    }
}
