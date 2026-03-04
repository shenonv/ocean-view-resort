package com.oceanview.factory;

import com.oceanview.model.Room;

public abstract class RoomFactory {

    // Factory Method — subclasses decide which Room to instantiate
    public abstract Room createRoom();
}