package com.example.quiz_four;

import java.io.Serializable;

public class Apartment implements Serializable {
    private Integer apartmentId;
    private String unitNumber;
    private Float squareFootage;
    private Double rentAmount;
    private Boolean isAirBnb;
    private Boolean allowsPets;
    private String location;

    public Apartment() {
    }

    public Apartment(String unitNumber, Float squareFootage, Double rentAmount, Boolean isAirBnb, Boolean allowsPets, String location) {
        this.unitNumber = unitNumber;
        this.squareFootage = squareFootage;
        this.rentAmount = rentAmount;
        this.isAirBnb = isAirBnb;
        this.allowsPets = allowsPets;
        this.location = location;
    }

    public Apartment(Integer apartmentId, String unitNumber, Float squareFootage, Double rentAmount, Boolean isAirBnb, Boolean allowsPets, String location) {
        this.apartmentId = apartmentId;
        this.unitNumber = unitNumber;
        this.squareFootage = squareFootage;
        this.rentAmount = rentAmount;
        this.isAirBnb = isAirBnb;
        this.allowsPets = allowsPets;
        this.location = location;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Float getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Float squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Boolean getIsAirBnb() {
        return isAirBnb;
    }

    public void setIsAirBnb(Boolean isAirBnb) {
        this.isAirBnb = isAirBnb;
    }

    public Boolean getAllowsPets() {
        return allowsPets;
    }

    public void setAllowsPets(Boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
