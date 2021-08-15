package com.johnsoneal.ecs.cars;

import java.util.Optional;

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
}
