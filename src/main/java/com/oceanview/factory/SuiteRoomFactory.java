package com.oceanview.factory;

import com.oceanview.model.Room;
import com.oceanview.model.SuiteRoom;

public class SuiteRoomFactory extends RoomFactory {

    @Override
    public Room createRoom() {
        return new SuiteRoom();
    }
}
