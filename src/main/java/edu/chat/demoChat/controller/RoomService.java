package edu.chat.demoChat.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import edu.chat.demoChat.model.Guest;
import lombok.RequiredArgsConstructor;
import edu.chat.demoChat.model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Map;

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

  public void joinUser(String roomId, String sessionId) {
    var room = findRoomById(roomId);

    sessionIdToRoomId.put(sessionId, roomId);

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
    headerAccessor.setSessionId(sessionId);

    messagingTemplate.convertAndSend("/queue/room/" + roomId + "/guestHasJoined", room.addGuest(new Guest(sessionId)));
    messagingTemplate.convertAndSendToUser(sessionId, "/queue/room/" + roomId + "/currentUser", room.getGuest(sessionId), headerAccessor.getMessageHeaders());
  }

  public void leaveGuest(String sessionId) {
    var roomId = sessionIdToRoomId.get(sessionId);
    var room = findRoomById(roomId);

    room.removeGuest(sessionId);

    messagingTemplate.convertAndSend("/queue/room/" + roomId + "/guestHasLeaved", sessionId);
  }

  public List<Guest> getGuests(String roomId) {
    var room = findRoomById(roomId);

    return room.getGuestList();
  }

  public Room getRoom(String id) {
    return this.findRoomById(id);
  }

  private Room findRoomById(String id) {
    return rooms.stream().filter(r -> r.getId()
        .equals(id))
        .findAny()
        .orElseThrow();
  }
}
