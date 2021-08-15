# ECS Cars Test

This is the ECS car Test. This Document covers:

* How to load the test.
* What criteria of the test are covered.
* What criteria of the test are not covered.
* Other things

## How to load the test

The test has been complete using Java 8, Maven, and SpringBoot. 

The Maven build environment uses the SpringBoot provided maven-wrapper, we will
use the wrapper to start the service:

```
./mvnw spring-boot:run
```

The service once loaded is on port `8080` under the path `/api/v1/cars`, for example:

```
http://localhost:8080/api/v1/cars
```

## Criteria covered

All criteria under "The Acceptance Criteria" are covered:

* "add" as a POST request to  `/api/v1/cars`
* "retrieve" for one Car a GET request `/api/v1/cars/{id}` OR for a page GET request `/api/v1/cars`
* "remove" as a DELETE request to  `/api/v1/cars/{id}`
* A Car with the specified properties under `com.johnsoneal.ecs.cars.Car`
* "As a Developer, I want my code to be covered by tests, so I know if a change has broken something"
* "As a Consumer of the API, I would like to be able to update my existing cars"

## Criteria not covered

* "As a Consumer of the API, when reading the car model information I would like to see an additional field containing a string of a few words that sound like the model of the cars"
* "As a Consumer of the API, I would like any cars I add through the API to persist between application restarts (persistent storage)". NOTE: Although this is not covered this is configuration and can be made with no code changes, for example by adding `spring.datasource.url` to the `application.properties` file.
* "As a Consumer of the API, I would like cars to be represented as two separate, hierarchically linked resources"

## Other Things

The API is document with Swagger (Note, the swagger-ui is not configured) and
has health checks configured `http://localhost:8080/actuator`.

On my phone conversation, I was asked for additional details that I would consider when designing a REST API.

I try to focus the API so that a one controller handles a one use-case, although this is not always possible. The controller end point should have a version identifier and should not perform any logic. It is only responsible for mapping external to internal representations and routing to a service. The controller should document what it expects, and what it returns. In the test this is achieved with Swagger, for example:

```
    @Operation(description = "Update a car by identifier.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car has been updated", content = {
            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Car is invalid", content = @Content),
        @ApiResponse(responseCode = "404", description = "Car not found", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> update(
        @PathVariable("id") Long id, @Valid @RequestBody CarDto car)
    {
        ...
    }
```

In the example we can see that we expect a JSON object with the schema specified by `CarDto` (this class is annotated to describe the required JSON properties and the constraints on values in any). In addition the example also tells that if we call the method with a non-existing car or a car that has invalid values, then a client error status is returned.

When creating a new end point, like the example, I follow these steps:

1. Map the use case to HTTP verbs.
1. Create a method in the controller with the best best documentation.
1. If required create a DTO object with any required constraints.
1. If required create methods on the service
1. Complete the body of the method
1. Create a test that checks that the method covers the documentation.

Database entities should not be exposed directly even if, as with the test, this seems like the easiest solution. Some of the reason for not doing this are:

* The database and API become tightly coupled.
* Changing the database entity may break the API.
* Increases the chance that data is leaked through the API.
* Changing the API version becomes difficult.
* May force the client to supply more data that required.

By using DTO objects we can created specific objects for each API method and specify validation separately. For example, when we create a `Car` the identifier is not know and as such or DTO should not have a an `id`, however an update request must have an `id`.

The service should be able to be monitored, this should be an end-point on the service that can checked by an automated external monitor.

There are many things still not covered:

* logging
* security
* caching
* containers
* CI/CD
* event sourcing
* instrumentation
* PATCH as a replacement API

I hope I have demonstrated enough for a further conversation.

  

  
