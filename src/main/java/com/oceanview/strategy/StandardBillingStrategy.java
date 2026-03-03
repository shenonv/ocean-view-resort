package com.oceanview.strategy;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double calculateBill(int numberOfNights, double pricePerNight) {
        return numberOfNights * pricePerNight;
    }
}
