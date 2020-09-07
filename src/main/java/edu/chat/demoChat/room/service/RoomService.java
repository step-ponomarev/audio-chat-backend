package edu.chat.demoChat.room.service;

import edu.chat.demoChat.room.Room;


public interface RoomService {
  Room createRoom();

  Room getRoom(String roomId);
}
