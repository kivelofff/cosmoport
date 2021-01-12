package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.service.ShipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/rest")
public class ShipController {

    @Autowired
    private ShipService service;

    @Autowired
    private ShipValidator shipValidator;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(shipValidator);
    }

    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    public List<Ship> getShipList(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String planet,
                                  @RequestParam(required = false) ShipType shipType,
                                  @RequestParam(required = false) Long after,
                                  @RequestParam(required = false) Long before,
                                  @RequestParam(required = false) Boolean isUsed,
                                  @RequestParam(required = false) Double minSpeed,
                                  @RequestParam(required = false) Double maxSpeed,
                                  @RequestParam(required = false) Integer minCrewSize,
                                  @RequestParam(required = false) Integer maxCewSize,
                                  @RequestParam(required = false) Double minRating,
                                  @RequestParam(required = false) Double maxRating,
                                  @RequestParam(required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, order.getFieldName());
        return service.getAll(Specification.where(ShipService.getShipsByNameSpec(name).
                and(Specification.where(ShipService.getShipsByPlanetSpec(planet)))).
                and(Specification.where(ShipService.getShipsByTypeSpec(shipType))).
                and(Specification.where(ShipService.getShipsByProdDateSpec(before, after))).
                and(Specification.where(ShipService.getShipsByIsUsedSpec(isUsed))).
                and(Specification.where(ShipService.getShipsBySpeedSpec(minSpeed, maxSpeed))).
                and(Specification.where(ShipService.getShipsByCrewSizeSpec(minCrewSize, maxCewSize))).
                and(Specification.where(ShipService.getShipsByRatingSpec(minRating, maxRating))), page);
    }

    @GetMapping("/ships/count")
    public Integer getShipsCount(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String planet,
                                 @RequestParam(required = false) ShipType shipType,
                                 @RequestParam(required = false) Long after,
                                 @RequestParam(required = false) Long before,
                                 @RequestParam(required = false) Boolean isUsed,
                                 @RequestParam(required = false) Double minSpeed,
                                 @RequestParam(required = false) Double maxSpeed,
                                 @RequestParam(required = false) Integer minCrewSize,
                                 @RequestParam(required = false) Integer maxCewSize,
                                 @RequestParam(required = false) Double minRating,
                                 @RequestParam(required = false) Double maxRating) {
        return service.countShips(Specification.where(ShipService.getShipsByNameSpec(name).
                and(Specification.where(ShipService.getShipsByPlanetSpec(planet)))).
                and(Specification.where(ShipService.getShipsByTypeSpec(shipType))).
                and(Specification.where(ShipService.getShipsByProdDateSpec(before, after))).
                and(Specification.where(ShipService.getShipsByIsUsedSpec(isUsed))).
                and(Specification.where(ShipService.getShipsBySpeedSpec(minSpeed, maxSpeed))).
                and(Specification.where(ShipService.getShipsByCrewSizeSpec(minCrewSize, maxCewSize))).
                and(Specification.where(ShipService.getShipsByRatingSpec(minRating, maxRating))));
    }

    @PostMapping("/ships")
    public Ship createShip(@Validated @RequestBody Ship ship) {
        service.createShip(ship);
        return ship;
    }

    @GetMapping("/ships/{id}")
    public Ship getShip(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/ships/{id}")
    public Ship updateShip(@PathVariable Long id, @Validated @RequestBody Ship ship) {
        return null;
    }

    @DeleteMapping("/ships/{id}")
    public void deleteShip(@PathVariable Long id) {

    }

}
