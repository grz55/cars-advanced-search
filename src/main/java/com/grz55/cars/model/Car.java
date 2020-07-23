package com.grz55.cars.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private int topSpeed;

    @NotEmpty
    private int productionYear;

    @NotEmpty
    private int mileage;

    @NotEmpty
    private int price;

    @NotEmpty
    public Car() {
    }

    public Car(@NotEmpty String brand, @NotEmpty String model, @NotEmpty int topSpeed, @NotEmpty int productionYear, @NotEmpty int mileage, @NotEmpty int price) {
        this.brand = brand;
        this.model = model;
        this.topSpeed = topSpeed;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
