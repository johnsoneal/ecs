package com.johnsoneal.ecs.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DeleteTest
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

    @BeforeEach
    void before_each()
    {
        car = repository.save(new Car(null, "Ford", "Focus", "Red", 2021));
    }

    @Test
    void delete()
    {
        client.delete()
            .uri("/api/v1/cars/{id}", car.getId())
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void not_found()
    {
        client.delete()
            .uri("/api/v1/cars/{id}", 6789)
            .exchange()
            .expectStatus().isNotFound();
    }
}
