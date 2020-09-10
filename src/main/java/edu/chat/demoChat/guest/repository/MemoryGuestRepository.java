package edu.chat.demoChat.guest.repository;

import edu.chat.demoChat.guest.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository("memoryGuestRepository")
public class MemoryGuestRepository implements GuestRepository {
  private final List<Guest> guestList;

  @Override
  public List<Guest> findAll() {
    return guestList;
  }

  @Override
  public Guest findById(String id) {
    return guestList.stream()
        .filter(g -> g.getId().equals(id))
        .findAny()
        .orElse(null);
  }

  @Override
  public Guest findBySessionId(String sessionId) {
    return guestList.stream()
        .filter(g -> sessionId.equals(g.getSessionId()))
        .findAny()
        .orElse(null);
  }

  @Override
  public List<Guest> findByRoomId(String roomId) {
    return guestList.stream()
        .filter(g -> g.getRoomId().equals(roomId))
        .collect(Collectors.toList());
  }

  @Override
  public Guest save(Guest guest) {
    guestList.removeIf(g -> g.getId().equals(guest.getId()));

    guest.setName("Guest " + guest.getId());

    guestList.add(guest);

    return guest;
  }

  @Override
  public void delete(String id) {
    guestList.removeIf(g -> g.getId().equals(id));
  }
}
