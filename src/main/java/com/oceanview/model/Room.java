package com.oceanview.model;

public abstract class Room {
    protected String type;
    protected double pricePerNight;

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}
