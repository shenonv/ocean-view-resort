package com.oceanview.factory;

import com.oceanview.model.DeluxeRoom;
import com.oceanview.model.Room;
import com.oceanview.model.StandardRoom;
import com.oceanview.model.SuiteRoom;

public class RoomFactory {
    public static Room createRoom(String type) {
        if (type.equalsIgnoreCase("Standerd")) {
            return new StandardRoom();
        }
        else if (type.equalsIgnoreCase("Delux")) {
            return new DeluxeRoom();
        } else if (type.equalsIgnoreCase("Suite")) {
            return new SuiteRoom();
        }
        throw new IllegalArgumentException("Invalid room type: " + type);
    }
}
