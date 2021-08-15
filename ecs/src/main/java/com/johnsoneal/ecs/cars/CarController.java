package com.johnsoneal.ecs.cars;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnsoneal.ecs.cars.dto.CarDto;
import com.johnsoneal.ecs.cars.dto.CreateCar;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1/cars", produces = APPLICATION_JSON_VALUE)
public class CarController
{
    private static final Logger log = LoggerFactory
        .getLogger(CarController.class);

    @Autowired
    private CarService cars;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Create a new Car.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Car has been created", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateCar.class))),
        @ApiResponse(responseCode = "400", description = "Car is invalid", content = @Content) })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> create(@RequestBody @Valid CreateCar car)
    {
        log.info("create car {}", car);
        Car request = mapper.map(car, Car.class);
        Car created = cars.create(request);
        CarDto result = mapper.map(created, CarDto.class);
        return ResponseEntity.status(CREATED).body(result);
    }
}
