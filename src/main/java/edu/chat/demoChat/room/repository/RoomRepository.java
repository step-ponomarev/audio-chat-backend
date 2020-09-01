package edu.chat.demoChat.room.repository;

import edu.chat.demoChat.room.Room;

public interface RoomRepository {
  Room save(Room newRoom);

  Room findById(String id);
}
