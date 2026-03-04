package com.oceanview.factory;

import com.oceanview.model.Room;
import com.oceanview.model.StandardRoom;

public class StandardRoomFactory extends RoomFactory {

    @Override
    public Room createRoom() {
        return new StandardRoom();
    }
}
