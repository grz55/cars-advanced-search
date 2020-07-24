package com.grz55.cars.controller;

import com.grz55.cars.service.CarService;
import com.grz55.cars.service.CarSpecification;
import com.grz55.cars.service.SearchCriteria;
import com.grz55.cars.service.SearchOperation;
import com.grz55.cars.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private final String SPACE = "\\s+";
    private final String EMPTY = "";

    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/all")
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("/cars")
    public Page<Car> getCarsByGivenParams(@RequestParam(name = "brand") Optional<String> brand,
                                          @RequestParam(name = "model") Optional<String> model,
                                          @RequestParam(name = "topSpeedFrom") Optional<Integer> topSpeedFrom,
                                          @RequestParam(name = "topSpeedTo") Optional<Integer> topSpeedTo,
                                          @RequestParam(name = "yearFrom") Optional<Integer> productionYearFrom,
                                          @RequestParam(name = "yearTo") Optional<Integer> productionYearTo,
                                          @RequestParam(name = "mileageFrom") Optional<Integer> mileageFrom,
                                          @RequestParam(name = "mileageTo") Optional<Integer> mileageTo,
                                          @RequestParam(name = "priceFrom") Optional<Integer> priceFrom,
                                          @RequestParam(name = "priceTo") Optional<Integer> priceTo,
                                          @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(name = "sortBy", defaultValue = "brand") String sortBy,
                                          @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {

        CarSpecification carSpecification = new CarSpecification();

        brand.ifPresent(string -> carSpecification.add(new SearchCriteria("brand", string, SearchOperation.MATCH)));
        model.ifPresent(string -> carSpecification.add(new SearchCriteria("model", string, SearchOperation.MATCH)));
        topSpeedFrom.ifPresent(integer -> carSpecification.add(new SearchCriteria("topSpeed", integer, SearchOperation.GREATER_THAN_EQUAL)));
        topSpeedTo.ifPresent(integer -> carSpecification.add(new SearchCriteria("topSpeed", integer, SearchOperation.LESS_THAN_EQUAL)));
        productionYearFrom.ifPresent(integer -> carSpecification.add(new SearchCriteria("productionYear", integer, SearchOperation.GREATER_THAN_EQUAL)));
        productionYearTo.ifPresent(integer -> carSpecification.add(new SearchCriteria("productionYear", integer, SearchOperation.LESS_THAN_EQUAL)));
        mileageFrom.ifPresent(integer -> carSpecification.add(new SearchCriteria("mileage", integer, SearchOperation.GREATER_THAN_EQUAL)));
        mileageTo.ifPresent(integer -> carSpecification.add(new SearchCriteria("mileage", integer, SearchOperation.LESS_THAN_EQUAL)));
        priceFrom.ifPresent(integer -> carSpecification.add(new SearchCriteria("price", integer, SearchOperation.GREATER_THAN_EQUAL)));
        priceTo.ifPresent(integer -> carSpecification.add(new SearchCriteria("price", integer, SearchOperation.LESS_THAN_EQUAL)));

        sortBy = sortBy.replaceAll(SPACE, EMPTY);
        sortOrder = sortOrder.toLowerCase();
        Sort sort = Sort.by(sortBy).ascending();
        if (sortOrder.equals("desc")) {
            sort = sort.descending();
        }

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);

        return carService.findAllByGivenParamsPaged(carSpecification, page);
    }

}
