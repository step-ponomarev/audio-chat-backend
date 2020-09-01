package edu.chat.demoChat.guest.service;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.guest.repository.GuestRepository;
import edu.chat.demoChat.signaler.SignalingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("memoryGuestService")
public class MemoryGuestService implements GuestService {
  @Qualifier("memoryGuestRepository")
  private final GuestRepository guestRepository;
  private final SignalingService signalingService;

  @Override
  public Guest addGuest(String id, String roomId) {
    var guest = guestRepository.save(new Guest(id, roomId));

    signalingService.signalGuestJoinedRoom(id, roomId);
    signalingService.signalToJoinedGuest(id, roomId);

    return guest;
  }

  @Override
  public void removeGuest(String id) {
    signalingService.signalGuestLeavedRoom(id);
    guestRepository.delete(id);
  }

  @Override
  public List<Guest> getGuestsWithoutCurrent(String roomId, String sessionId) {
    return guestRepository.findByRoomId(roomId)
        .stream()
        .filter(guest -> !guest.getId().equals(sessionId))
        .collect(Collectors.toList());
  }
}
