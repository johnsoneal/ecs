package com.johnsoneal.ecs.cars.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.johnsoneal.ecs.cars.Car;

/**
 * Represents a page of {@link Car} result.
 */
public class CarPage
{
    @JsonProperty(required = true)
    private int page;

    @JsonProperty(required = true)
    private int size;

    @JsonProperty
    private boolean previous;

    @JsonProperty
    private boolean next;

    @JsonProperty(required = true)
    private List<CarDto> cars;

    public CarPage(
        int page, int size, List<CarDto> cars,
        boolean hasPrevious, boolean hasNext)
    {
        this.page = page;
        this.size = size;
        this.cars = new ArrayList<>(cars);
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
        CarPage other = (CarPage) obj;
        return cars.size() == other.cars.size()
            && cars.containsAll(other.cars)
            && next == other.next
            && page == other.page
            && previous == other.previous
            && size == other.size;
    }

    public List<CarDto> getCars()
    {
        return cars;
    }

    public int getPage()
    {
        return page;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cars, next, page, previous, size);
    }

    public boolean hasNext()
    {
        return next;
    }

    public boolean hasPrevious()
    {
        return previous;
    }

    public void setCars(List<CarDto> cars)
    {
        this.cars = new ArrayList<>(cars);
    }

    public void setNext(boolean next)
    {
        this.next = next;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public void setPrevious(boolean previous)
    {
        this.previous = previous;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "CarPageDto:[page=" + page + ", size=" + size + ", cars=" + cars
            + ", previous=" + previous + ", next=" + next + "]";
    }
}
