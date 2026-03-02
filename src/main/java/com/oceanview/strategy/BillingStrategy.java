package com.oceanview.strategy;

public interface BillingStrategy {
    double calculateBill(int numberOfNights, double pricePerNight);
}
