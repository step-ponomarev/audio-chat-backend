package edu.chat.demoChat.model;

import lombok.AllArgsConstructor;
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
  private List<Guest> guestList;
  private List<String> names;
  private List<Message> messages;

  public Room(String id, LocalDateTime lastActiveTime) {
    this.id = id;
    this.lastActiveTime = lastActiveTime;
    this.guestList = new ArrayList<>();
    this.names = new ArrayList<>();
    this.messages = new ArrayList<>();
  }

  public Guest addGuest(Guest guest) {
    final String name = generateUniqueGuestName();

    names.add(name);
    guest.setName(name);

    this.getGuestList().add(guest);

    return guest;
  }

  public void removeGuest(String sessionId) {
    var name = getGuest(sessionId).getName();

    guestList = guestList
        .stream()
        .filter(guest -> !guest.getSessionId().equals(sessionId))
        .collect(Collectors.toList());

    names = names.stream().filter(n -> !n.equals(name)).collect(Collectors.toList());
  }

  public Message addMessage(String sessionId, String message) {
    var msg = new Message(getGuest(sessionId).getName(), message, LocalDateTime.now());

    messages.add(msg);

    return msg;
  }

  public Guest getGuest(String sessionId) {
    return guestList
        .stream()
        .filter(guest -> guest.getSessionId().equals(sessionId))
        .findAny()
        .orElseThrow();
  }

  private String generateUniqueGuestName() {
    for (int i = 1; i <= this.guestList.size(); i++) {
      String name = "Guest " + i;

      if (!names.contains(name)) {
        names.add(name);
        return name;
      }
    }

    return "Guest " + (this.getGuestList().size() + 1);
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
