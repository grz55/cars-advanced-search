package com.grz55.cars.service;

import com.grz55.cars.model.Car;
import com.grz55.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Page<Car> findAllByGivenParamsPaged(CarSpecification carSpecification, Pageable pageable) {
        return carRepository.findAll(carSpecification, pageable);
    }
}
