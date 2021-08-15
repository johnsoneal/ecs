package com.johnsoneal.ecs.cars;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.johnsoneal.ecs.cars.dto.CreateCar;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CreateCarTest
{
    @Autowired
    private WebTestClient client;

    @Autowired
    private CarRepository repository;

    @AfterEach
    void after_each()
    {
        repository.deleteAll();
    }

    @Test
    void create() throws Exception
    {
        CreateCar car = new CreateCar("Ford", "Focus", "Red", 2021);

        client.post()
            .uri("/api/v1/cars")
            .contentType(APPLICATION_JSON)
            .bodyValue(car)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.id").exists()
            .jsonPath("$.make").isEqualTo("Ford")
            .jsonPath("$.model").isEqualTo("Focus")
            .jsonPath("$.colour").isEqualTo("Red")
            .jsonPath("$.year").isEqualTo(2021);
    }

    @Test
    void invalid()
    {
        int year_invalid = 1920;
        CreateCar car = new CreateCar("Ford", "Focus", "Red", year_invalid);

        client.post()
            .uri("/api/v1/cars")
            .contentType(APPLICATION_JSON)
            .bodyValue(car)
            .exchange()
            .expectStatus().isBadRequest();
    }
}
