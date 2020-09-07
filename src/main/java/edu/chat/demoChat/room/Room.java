package edu.chat.demoChat.room;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.message.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class Room {
  private String id;
  private LocalDateTime lastActiveTime;

  public Room(String id, LocalDateTime lastActiveTime) {
    this.id = id;
    this.lastActiveTime = lastActiveTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Room room = (Room) o;
    return Objects.equals(id, room.id) &&
        Objects.equals(lastActiveTime, room.lastActiveTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lastActiveTime);
  }
}
