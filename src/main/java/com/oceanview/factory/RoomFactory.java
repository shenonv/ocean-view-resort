package com.oceanview.factory;

import com.oceanview.model.*;

public class RoomFactory {

    public static Room createRoom(String type) {

        if (type.equalsIgnoreCase("Standard")) {
            return new StandardRoom();
        }
        else if (type.equalsIgnoreCase("Deluxe")) {
            return new DeluxeRoom();
        }
        else if (type.equalsIgnoreCase("Suite")) {
            return new SuiteRoom();
        }

        throw new IllegalArgumentException("Invalid room type: " + type);
    }
}