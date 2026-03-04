package com.oceanview.factory;

import com.oceanview.model.Room;
import com.oceanview.model.DeluxeRoom;

public class DeluxeRoomFactory extends RoomFactory {

    @Override
    public Room createRoom() {
        return new DeluxeRoom();
    }
}
