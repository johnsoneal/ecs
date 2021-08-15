package com.johnsoneal.ecs.cars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.johnsoneal.ecs.cars.dto.CarDto;
import com.johnsoneal.ecs.cars.dto.CarPage;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPageTest
{
    @Autowired
    private WebTestClient client;

    @Autowired
    private CarRepository repository;

    private Car c1;
    private Car c2;
    private Car c3;

    @AfterEach
    void after_each()
    {
        repository.deleteAll();
    }

    @BeforeEach
    void before_each()
    {
        c1 = repository.save(new Car(null, "Ford", "Focus", "Red", 2019));
        c2 = repository.save(new Car(null, "Honda", "Jazz", "Pink", 2020));
        c3 = repository.save(new Car(null, "Fiat", "Panda", "Blue", 2021));
    }

    @Test
    void get_with_default_values()
    {
        List<CarDto> cars = Stream.of(c1, c2, c3).map(this::convert)
            .collect(Collectors.toList());
        CarPage expected = new CarPage(0, 20, cars, false, false);

        CarPage actual = client.get()
            .uri("api/v1/cars")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CarPage.class)
            .returnResult()
            .getResponseBody();

        assertEquals(expected, actual);
    }

    @Test
    void get_with_specified_size()
    {
        List<CarDto> cars = Stream.of(c1, c2).map(this::convert)
            .collect(Collectors.toList());
        CarPage expected = new CarPage(0, 2, cars, false, true);

        CarPage actual = client.get()
            .uri("api/v1/cars?page={page}&size={size}", 0, 2)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CarPage.class)
            .returnResult()
            .getResponseBody();

        assertEquals(expected, actual);
    }

    private CarDto convert(Car c)
    {
        return new CarDto(
            c.getId(), c.getMake(), c.getModel(), c.getColour(), c.getYear());
    }
}
