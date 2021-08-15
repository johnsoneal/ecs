package com.johnsoneal.ecs.cars.dto;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

/**
 * Represents a {@code Car} after it has been persisted and carries property
 * validated information.
 */
public class CarDto
{
    @JsonProperty(required = true)
    @NotNull
    private Long id;

    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private String make;

    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private String model;

    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private String colour;

    @JsonProperty(required = true)
    @NotNull
    @Min(1950)
    @Max(2050)
    private Integer year;

    public CarDto()
    {
        // Empty Block
    }

    public CarDto(
        Long id, String make, String model,
        String colour, Integer year)
    {
        this.id = id;
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CarDto other = (CarDto) obj;
        return Objects.equals(id, other.id)
            && Objects.equals(make, other.make)
            && Objects.equals(model, other.model)
            && Objects.equals(colour, other.colour)
            && Objects.equals(year, other.year);
    }

    public String getColour()
    {
        return colour;
    }

    public Long getId()
    {
        return id;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public Integer getYear()
    {
        return year;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, make, model, colour, year);
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CarDto:[id=");
        builder.append(id);
        builder.append(", make=");
        builder.append(make);
        builder.append(", model=");
        builder.append(model);
        builder.append(", colour=");
        builder.append(colour);
        builder.append(", year=");
        builder.append(year);
        builder.append("]");
        return builder.toString();
    }
}
