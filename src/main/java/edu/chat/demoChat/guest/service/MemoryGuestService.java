package edu.chat.demoChat.guest.service;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.guest.repository.GuestRepository;
import edu.chat.demoChat.signalingService.SignalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    guest.setSessionId(sessionId);

    signalingService.signalGuestJoinedRoom(guestRepository.save(guest));
  }
}
