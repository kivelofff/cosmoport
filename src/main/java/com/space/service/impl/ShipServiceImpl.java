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
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

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

        Ship savedShip = shipRepository.saveAndFlush(ship);
        return savedShip;
    }

    @Override
    public Ship updateShip(Long id, Ship ship) {

        Optional<Ship> foundShip = shipRepository.findById(id);
        if (foundShip.isPresent()) {
            Ship toBeUpdated = foundShip.get();
            toBeUpdated.setName(ship.getName());
            shipRepository.saveAndFlush(ship);

            return toBeUpdated;
        } else {
            ship.setId(id);
            shipRepository.saveAndFlush(ship);
            return ship;
        }
    }

    @Override
    public Ship getShip(Long id) {
        return null;
    }

    @Override
    public void deleteShip(Long id) {

    }


    public static Specification<Ship> getShipsByNameSpec(String name) {
        return name == null ? null : new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    public static Specification<Ship> getShipsByPlanetSpec(String planet) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("planet"), "%" +planet + "%");
            }
        };
    }

    public static Specification<Ship> getShipsByTypeSpec(ShipType shipType) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("shipType"), shipType);
            }
        };
    }

    public static Specification<Ship> getShipsByIsUsedSpec(boolean isUsed) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("isUsed"), isUsed);
            }
        };
    }

    public static Specification<Ship> getShipsByProdDateSpec(Long before, Long after) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("prodDate"), before, after);
            }
        };
    }
    public static Specification<Ship> getShipsBySpeedSpec(Double minSpeed, Double maxSpeed) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed);
            }
        };
    }

    public static Specification<Ship> getShipsByCrewSizeSpec(Integer minCrewSize, Integer maxCrewSize) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
            }
        };
    }

    public static Specification<Ship> getShipsByRatingSpec(Double minRating, Double maxRating) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("rating"), minRating, maxRating);
            }
        };
    }

}
