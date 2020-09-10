package edu.chat.demoChat.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Room {
  private String id;
  private LocalDateTime lastActiveTime;
  
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
