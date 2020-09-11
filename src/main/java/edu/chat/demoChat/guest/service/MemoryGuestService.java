package edu.chat.demoChat.guest.service;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.guest.repository.GuestRepository;
import edu.chat.demoChat.signalingService.SignalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("memoryGuestService")
public class MemoryGuestService implements GuestService {
  @Qualifier("memoryGuestRepository")
  private final GuestRepository guestRepository;
  private final SignalingService signalingService;

  @Override
  public List<Guest> getGuests(String roomId) {
    return guestRepository.findByRoomId(roomId);
  }

  @Override
  public List<Guest> getActiveGuests(String roomId) {
    return guestRepository
        .findByRoomId(roomId).stream().filter(Guest::getActive)
        .collect(Collectors.toList());
  }

  @Override
  public Guest getGuest(String guestId) {
    return guestRepository.findById(guestId);
  }

  @Override
  public Guest createGuest(String roomId) {
    var id = UUID.randomUUID().toString();

    return guestRepository.save(new Guest(id, roomId));
  }

  @Override
  public void removeGuest(String guestId) {
    signalingService.signalGuestLeavedRoom(guestId);

    guestRepository.delete(guestId);
  }

  @Override
  public void registerUser(String sessionId, String guestId, String roomId) {
    var guest = guestRepository.findById(guestId);

    if (guest.getActive()) {
      return;
    }

    guest.setSessionId(sessionId);
    guest.setActive(true);

    signalingService.signalGuestJoinedRoom(guestRepository.save(guest));
  }
}
