package edu.chat.demoChat.room.service;

import edu.chat.demoChat.message.Message;
import edu.chat.demoChat.room.Room;
import edu.chat.demoChat.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("memoryRoomService")
@RequiredArgsConstructor
public class MemoryRoomService implements RoomService {
  @Qualifier("memoryRoomRepository")
  private final RoomRepository roomRepository;

  @Override
  public Room createRoom() {
    final var newRoom = new Room(UUID.randomUUID().toString(), LocalDateTime.now());

    return roomRepository.save(newRoom);
  }

  @Override
  public Room getRoom(String roomId) {
    return roomRepository.findById(roomId);
  }
}
