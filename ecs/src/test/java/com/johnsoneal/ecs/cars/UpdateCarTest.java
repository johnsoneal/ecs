package com.johnsoneal.ecs.cars;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.johnsoneal.ecs.cars.dto.CarDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UpdateCarTest
{
    @Autowired
    private WebTestClient client;

    @Autowired
    private CarRepository repository;

    private Car car;

    @AfterEach
    void after_each()
    {
        repository.deleteAll();
    }

    @Test
    void bad_request() throws Exception
    {
        int year_invalid = 1920;
        CarDto update = convert(car);
        update.setYear(year_invalid);

        client.put()
            .uri("/api/v1/cars/{id}", car.getId())
            .contentType(APPLICATION_JSON)
            .bodyValue(update)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @BeforeEach
    void before_each()
    {
        car = repository.save(new Car(null, "Ford", "Focus", "Red", 2019));
    }

    @Test
    void not_found() throws Exception
    {
        CarDto update = convert(car);

        client.put()
            .uri("/api/v1/cars/{id}", 1234)
            .contentType(APPLICATION_JSON)
            .bodyValue(update)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void update() throws Exception
    {
        CarDto update = new CarDto(car.getId(), "Honda", "Jazz", "Green", 2020);

        client.put()
            .uri("/api/v1/cars/{id}", car.getId())
            .contentType(APPLICATION_JSON)
            .bodyValue(update)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(car.getId())
            .jsonPath("$.make").isEqualTo("Honda")
            .jsonPath("$.model").isEqualTo("Jazz")
            .jsonPath("$.colour").isEqualTo("Green")
            .jsonPath("$.year").isEqualTo(2020);
    }

    private CarDto convert(Car c)
    {
        return new CarDto(
            c.getId(), c.getMake(), c.getModel(), c.getColour(), c.getYear());
    }
}
