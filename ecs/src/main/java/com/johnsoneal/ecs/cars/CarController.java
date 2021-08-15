package com.johnsoneal.ecs.cars;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnsoneal.ecs.cars.dto.CarDto;
import com.johnsoneal.ecs.cars.dto.CarPage;
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

    @Operation(description = "Get a Car by identifier.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car found", content = {
            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarDto.class)) }),
        @ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable("id") Long id)
    {
        Optional<Car> request = cars.findAllById(id);
        Optional<CarDto> result = request.map(c -> mapper.map(c, CarDto.class));
        return ResponseEntity.of(result);
    }

    @GetMapping
    public ResponseEntity<CarPage> getPage(
        @RequestParam(required = false, name = "page", defaultValue = "0") int page,
        @RequestParam(required = false, name = "size", defaultValue = "20") int size)
    {
        // Here we are only showing a sketch of 'How to page results'.
        Page<Car> request = cars.getPage(page, size);
        List<CarDto> list = request.getContent().stream()
            .map(c -> mapper.map(c, CarDto.class))
            .collect(Collectors.toList());
        CarPage result = new CarPage(
            request.getNumber(), request.getSize(), list,
            request.hasPrevious(), request.hasNext());
        return ResponseEntity.ok(result);
    }
}
