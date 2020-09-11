package edu.chat.demoChat.signalingService;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.guest.repository.GuestRepository;
import edu.chat.demoChat.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Service
@RequiredArgsConstructor
public class DefaultSignalingService implements SignalingService {
  private final SimpMessagingTemplate messagingTemplate;
  @Qualifier("memoryGuestRepository")
  private final GuestRepository guestRepository;

  @Override
  public void signalGuestJoinedRoom(Guest guest) {
    guestRepository.findByRoomId(guest.getRoomId()).forEach(g -> {
      if (!guest.getId().equals(g.getId())) {
        var url = "/queue/guest/" + g.getId() + "/room/" + guest.getRoomId() + "/guestHasJoined";
        messagingTemplate.convertAndSend(url, guest);
      }
    });
  }

  @Override
  public void signalGuestLeavedRoom(String id) {
    var guest = guestRepository.findById(id);
    var roomId = guest.getRoomId();
    var url = "/queue/room/" + roomId + "/guestHasLeaved";

    messagingTemplate.convertAndSend(url, guest);
  }

  @Override
  public void signalMessageSended(Message message) {
    var url = "/queue/room/" + message.getRoomId() + "/newMessage";

    messagingTemplate.convertAndSend(url, message);
  }

  @EventListener
  public void handleSessionDisconnect(SessionDisconnectEvent event) {
    var guestId = guestRepository.findBySessionId(event.getSessionId()).getId();

    this.signalGuestLeavedRoom(guestId);
  }
}