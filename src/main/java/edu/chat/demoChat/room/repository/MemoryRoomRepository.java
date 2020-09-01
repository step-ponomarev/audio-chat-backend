package edu.chat.demoChat.room.repository;

import edu.chat.demoChat.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository("memoryRoomRepository")
public class MemoryRoomRepository implements RoomRepository {
  private final List<Room> rooms;

  @Override
  public Room save(Room room) {
    rooms.add(room);

    return room;
  }

  @Override
  public Room findById(String id) {
    return rooms.stream().filter(r -> r.getId()
        .equals(id))
        .findAny()
        .orElseThrow(RuntimeException::new);
  }
}
