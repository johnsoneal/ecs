package com.johnsoneal.ecs.cars;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entity for a Car.
 */
@Entity
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "colour")
    private String colour;

    @Column(name = "year")
    private Integer year;

    /* Here mainly to show how DTO can have different view of this car. */
    @UpdateTimestamp
    @Column(name = "modified")
    private Instant modifiedAt;

    public Car()
    {
        // Empty Block
    }

    public Car(
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
        Car other = (Car) obj;
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

    public Instant getModifiedAt()
    {
        return modifiedAt;
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

    public void setModifiedAt(Instant modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Car [id=");
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
