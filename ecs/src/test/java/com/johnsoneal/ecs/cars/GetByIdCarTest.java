package com.johnsoneal.ecs.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetByIdCarTest
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
    void get()
    {
        Car car = repository.save(new Car(null, "Ford", "Focus", "Red", 2021));

        client.get()
            .uri("/api/v1/cars/{id}", car.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(car.getId())
            .jsonPath("$.make").isEqualTo("Ford")
            .jsonPath("$.model").isEqualTo("Focus")
            .jsonPath("$.colour").isEqualTo("Red")
            .jsonPath("$.year").isEqualTo(2021);
    }

    @Test
    void not_found() throws Exception
    {
        client.get()
            .uri("/api/v1/cars/5678")
            .exchange()
            .expectStatus().isNotFound();
    }

}
