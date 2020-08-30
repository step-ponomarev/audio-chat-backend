package edu.chat.demoChat.controller;

import edu.chat.demoChat.model.Guest;
import edu.chat.demoChat.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final SimpMessagingTemplate messagingTemplate;
  private final Map<String, String> sessionIdToRoomId;
  private final List<Room> rooms;

  public Room createRoom() {
    final var newRoom = new Room(UUID.randomUUID().toString(), LocalDateTime.now(), new ArrayList<>());

    rooms.add(newRoom);

    return newRoom;
  }

  public void registerUser(String roomId, String sessionId) {
    var room = findRoomById(roomId);

    sessionIdToRoomId.put(sessionId, roomId);

    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);

    messagingTemplate.convertAndSendToUser(sessionId, "/queue/registeredUser", room.addGuest(new Guest(sessionId)), headerAccessor.getMessageHeaders());
  }

  public void removeGuest(String sessionId) {
//    var room = findRoomById(sessionIdToRoomId.get(sessionId));
//
//    room.removeGuest(sessionId);

    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);

    messagingTemplate.convertAndSend("/topic/guestList", "SUCCESS", headerAccessor.getMessageHeaders());
  }

  private Room findRoomById(String id) {
    return rooms.stream().filter(r -> r.getId()
        .equals(id))
        .findAny()
        .orElseThrow();
  }
}
