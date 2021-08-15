package com.johnsoneal.ecs.cars;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CarService
{
    private final CarRepository cars;

    public CarService(CarRepository cars)
    {
        this.cars = cars;
    }

    public Car create(Car car)
    {
        return cars.save(car);
    }

    public Optional<Car> findAllById(Long id)
    {
        return cars.findById(id);
    }

    public Page<Car> getPage(int pageNumber, int pageSize)
    {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return cars.findAll(pageable);
    }
}
