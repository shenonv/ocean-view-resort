package com.oceanview.strategy;

public class DeluxeBillingStrategy implements BillingStrategy {
    @Override
    public double calculateBill(int numberOfNights, double pricePernight) {
        double total = numberOfNights * pricePernight;
        return total + (total * 0.10); // Adding 10% service charge for deluxe rooms
    }
}
