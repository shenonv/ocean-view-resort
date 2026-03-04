package com.oceanview.factory;

import com.oceanview.strategy.BillingStrategy;
import com.oceanview.strategy.StandardBillingStrategy;
import com.oceanview.strategy.DeluxeBillingStrategy;
import com.oceanview.strategy.SuiteBillingStrategy;

public class BillingStrategyFactory {

    public static BillingStrategy getStrategy(String roomType) {
        if (roomType.equalsIgnoreCase("Standard")) {
            return new StandardBillingStrategy();
        } else if (roomType.equalsIgnoreCase("Deluxe")) {
            return new DeluxeBillingStrategy();
        } else if (roomType.equalsIgnoreCase("Suite")) {
            return new SuiteBillingStrategy();
        }
        throw new IllegalArgumentException("Unknown room type: " + roomType);
    }
}
