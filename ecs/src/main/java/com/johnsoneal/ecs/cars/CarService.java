package com.johnsoneal.ecs.cars;

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
}