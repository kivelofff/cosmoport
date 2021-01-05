package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    List<Ship> getAll(Specification<Ship> shipSpecification, Pageable pageable);
    Integer countShips(Specification<Ship> shipSpecification);
    Ship createShip(Ship ship);
    Ship updateShip(Long id, Ship ship);
    Ship getShip(Long id);
    void deleteShip(Long id);
}
