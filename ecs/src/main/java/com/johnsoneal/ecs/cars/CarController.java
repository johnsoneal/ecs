package com.johnsoneal.ecs.cars;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/cars", produces = APPLICATION_JSON_VALUE)
public class CarController
{
    private static final Logger log = LoggerFactory
        .getLogger(CarController.class);
}
