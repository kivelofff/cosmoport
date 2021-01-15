package com.space.service.impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    public void calculateRating(Ship ship) {
        int CURRENT_YEAR=3019;
        GregorianCalendar prodDate = new GregorianCalendar();
        prodDate.setTime(ship.getProdDate());
        int prodYear = prodDate.get(Calendar.YEAR);
        double k = ship.getUsed()? 0.5 : 1;
        double shipSpeed = ship.getSpeed();
        Double rating = 80*shipSpeed*k/(CURRENT_YEAR-prodYear+1);
        rating = Math.round(rating*100)/100.0;
        ship.setRating(rating);
    }

    @Override
    public List<Ship> getAll(Specification<Ship> shipSpecification, Pageable pageable) {

        return shipRepository.findAll(shipSpecification, pageable).getContent();
    }

    @Override
    public Integer countShips(Specification<Ship> shipSpecification) {

        return shipRepository.findAll(shipSpecification).size();
    }

    @Override
    public Ship createShip(Ship ship) {
        calculateRating(ship);
        Ship savedShip = shipRepository.saveAndFlush(ship);
        return savedShip;
    }

    @Override
    public Ship updateShip(Long id, Ship ship) {

        Optional<Ship> foundShip = shipRepository.findById(id);

            Ship toBeUpdated = foundShip.get();
            toBeUpdated.setName(ship.getName());
            toBeUpdated.setPlanet(ship.getPlanet());
            toBeUpdated.setShipType(ship.getShipType());
            toBeUpdated.setProdDate(ship.getProdDate());
            toBeUpdated.setUsed(ship.getUsed());
            toBeUpdated.setSpeed(ship.getSpeed());
            toBeUpdated.setCrewSize(ship.getCrewSize());
            calculateRating(toBeUpdated);
            shipRepository.saveAndFlush(toBeUpdated);

            return toBeUpdated;

    }

    @Override
    public Ship getShip(Long id) {
        return shipRepository.findById(id).get();
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public boolean isIdValid(Long id) {
        return id > 0;
    }

    @Override
    public boolean isIdExists(Long id) {
        return shipRepository.existsById(id);
    }

    @Override
    public String validateShip(Ship ship) {

        String errorMessage = new String();
        String shipName = ship.getName();
        if (shipName == null || shipName.isEmpty() || shipName.length() > 50) {
            errorMessage = errorMessage.concat("Name is too long or empty. ");
        }
        String shipPlanet = ship.getPlanet();

        if (shipPlanet == null || shipPlanet.length() > 50) {
            errorMessage = errorMessage.concat("Planet name is too long or empty. ");
        }
        Calendar shipProdDate = new GregorianCalendar();
        Date date = ship.getProdDate();
        if (date == null) {
            errorMessage = errorMessage.concat("Year of production is null. ");
        } else {
            shipProdDate.setTime(date);
            Integer yearOfProd = shipProdDate.get(Calendar.YEAR);
            if (yearOfProd > 3019 || yearOfProd < 2800) {
                errorMessage = errorMessage.concat("Year of production should be between 2800 and 3019. ");
            }
        }
        Boolean shipIsUsed = ship.getUsed();
        if (shipIsUsed == null) {
            ship.setUsed(false);
        }
        Double shipSpeed = ship.getSpeed();
        if (shipSpeed == null || shipSpeed < 0.01d || shipSpeed > 0.99d) {
            errorMessage = errorMessage.concat("Speed should be between 0.01 and 0.99. ");
        }

        Integer shipCrewSize = ship.getCrewSize();
        if (shipCrewSize == null || shipCrewSize < 1 || shipCrewSize > 9999) {
            errorMessage = errorMessage.concat("Crew size should be between 1 and 9999. ");
        }
        return errorMessage;
    }



}
