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
        Ship savedShip = shipRepository.save(ship);
        return savedShip;
    }

    @Override
    public Ship updateShip(Long id, Ship ship) {

        Optional<Ship> foundShip = shipRepository.findById(id);
        if (foundShip.isPresent()) {
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
        } else {
            ship.setId(id);
            shipRepository.save(ship);
            return ship;
        }
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




}
