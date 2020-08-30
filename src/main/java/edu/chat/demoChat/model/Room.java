package edu.chat.demoChat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Room {
  private String id;
  private LocalDateTime lastActiveTime;
  private List<Guest> guestList;

  public Guest addGuest(Guest guest) {
    guest.setName("Guest" + (this.getGuestList().size() + 1));

    this.getGuestList().add(guest);

    return guest;
  }

  public void removeGuest(String sessionId) {
    guestList = guestList
        .stream()
        .filter(guest -> !guest.getSessionId().equals(sessionId))
        .collect(Collectors.toList());
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
