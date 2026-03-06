package com.oceanview;

import com.oceanview.strategy.BillingStrategy;
import com.oceanview.strategy.StandardBillingStrategy;
import junit.framework.TestCase;

public class BillingStrategyTest extends TestCase {
    public void testStandardBillingThreeNights() {

        BillingStrategy strategy = new StandardBillingStrategy();

        double result = strategy.calculateBill(3, 100.0);

        assertEquals(300.0, result, 0.01);
    }
}
