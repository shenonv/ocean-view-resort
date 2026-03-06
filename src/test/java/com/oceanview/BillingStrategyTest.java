package com.oceanview;

import com.oceanview.factory.RoomFactory;
import com.oceanview.factory.StandardRoomFactory;
import com.oceanview.model.Room;
import com.oceanview.strategy.BillingStrategy;
import com.oceanview.strategy.DeluxeBillingStrategy;
import com.oceanview.strategy.StandardBillingStrategy;
import com.oceanview.strategy.SuiteBillingStrategy;
import junit.framework.TestCase;

public class BillingStrategyTest extends TestCase {
    public void testStandardRoomFactory() {
        RoomFactory f = new StandardRoomFactory();
        Room room = f.createRoom();
        assertNotNull(room);
        assertEquals("Standard", room.getType());
        assertEquals(5000.0, room.getPricePerNight(), 0.01);
    }
}
