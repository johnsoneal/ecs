package com.johnsoneal.ecs.cars.dto;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

/**
 * Represents properties for a {@code Car} that has not yet been persisted.
 */
public class CreateCar
{
    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private String make;

    @JsonProperty
    private String model;

    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private String colour;

    @JsonProperty(required = true)
    @NotNull
    @Min(value = 1950, message = "Invalid year minumum 1950")
    @Max(value = 2050, message = "Invalid year maximum 2050")
    private Integer year;

    public CreateCar()
    {
        // Empty Block
    }

    public CreateCar(
        String make, String model,
        String colour, Integer year)
    {
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
        CreateCar other = (CreateCar) obj;
        return Objects.equals(make, other.make)
            && Objects.equals(model, other.model)
            && Objects.equals(colour, other.colour)
            && Objects.equals(year, other.year);
    }

    public String getColour()
    {
        return colour;
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
        return Objects.hash(make, model, colour, year);
    }

    public void setColour(String colour)
    {
        this.colour = colour;
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
        builder.append("NewCarDto:[make=");
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
