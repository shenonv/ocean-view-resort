package com.oceanview.strategy;

public class SuiteBillingStrategy implements BillingStrategy {
    @Override
    public double calculateBill(int numberOfNights, double pricePerNight) {
        double total = numberOfNights * pricePerNight;
        return total + (total * 0.15); // Adding 15% service charge for suite rooms
    }

}
