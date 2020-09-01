package edu.chat.demoChat.signaler;

import edu.chat.demoChat.guest.Guest;
import edu.chat.demoChat.guest.repository.GuestRepository;
import edu.chat.demoChat.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DefaultSignalingService implements SignalingService {
  private final SimpMessagingTemplate messagingTemplate;
  @Qualifier("memoryGuestRepository")
  private final GuestRepository guestRepository;

  @Override
  public void signalGuestJoinedRoom(String guestId, String roomId) {
    var guests = guestRepository.findByRoomId(roomId);
    var url = "/queue/room/" + roomId + "/guestHasJoined";
    var joinedGuest = guestRepository.findById(guestId);

    sendToAll(guests, url, joinedGuest);
  }

  @Override
  public void signalToJoinedGuest(String id, String roomId) {
    var guest = guestRepository.findByRoomId(roomId);
    var url = "/queue/room/" + roomId + "/currentUser";

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
    headerAccessor.setSessionId(id);

    messagingTemplate.convertAndSendToUser(id, url, guest, headerAccessor.getMessageHeaders());
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
  private void handleSessionDisconnect(SessionDisconnectEvent event) {
    guestRepository.delete(event.getSessionId());
  }

  private void sendToAll(List<Guest> guests, String url, Object body) {
    guests.forEach(g -> {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
      headerAccessor.setSessionId(g.getId());
      messagingTemplate.convertAndSendToUser(g.getId(), url, body, headerAccessor.getMessageHeaders());
    });
  }
}
