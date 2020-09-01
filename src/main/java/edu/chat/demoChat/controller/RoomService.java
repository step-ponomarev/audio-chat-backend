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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final SimpMessagingTemplate messagingTemplate;
  private final Map<String, String> sessionIdToRoomId;
  private final List<Room> rooms;

  public Room createRoom() {
    final var newRoom = new Room(UUID.randomUUID().toString(), LocalDateTime.now());

    rooms.add(newRoom);

    return newRoom;
  }

  public void joinUser(String roomId, String sessionId) {
    var room = findRoomById(roomId);
    var guests = room.getGuestList();
    var guest = room.addGuest(new Guest(sessionId));

    sessionIdToRoomId.put(sessionId, roomId);

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
    headerAccessor.setSessionId(sessionId);

    messagingTemplate.convertAndSendToUser(sessionId, "/queue/room/" + roomId + "/currentUser", guest, headerAccessor.getMessageHeaders());
    sendToAll(getGuestsWithout(sessionId, guests), "/queue/room/" + roomId + "/guestHasJoined", guest);
  }

  public void leaveGuest(String sessionId) {
    var roomId = sessionIdToRoomId.get(sessionId);
    var room = findRoomById(roomId);
    var guest = room.getGuest(sessionId);

    room.removeGuest(sessionId);

    sessionIdToRoomId.remove(sessionId);

    messagingTemplate.convertAndSend("/queue/room/" + roomId + "/guestHasLeaved", guest);
  }

  public List<Guest> getGuests(String roomId) {
    var room = findRoomById(roomId);

    return room.getGuestList();
  }

  public List<Guest> getGuestsWithoutCurrent(String roomId, String sessionId) {
    var room = findRoomById(roomId);

    return getGuestsWithout(sessionId, room.getGuestList());
  }


  public Room getRoom(String id) {
    return this.findRoomById(id);
  }

  private void sendToAll(List<Guest> guests, String url, Object body) {
    guests.forEach(g -> {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
      headerAccessor.setSessionId(g.getSessionId());
      messagingTemplate.convertAndSendToUser(g.getSessionId(), url, body, headerAccessor.getMessageHeaders());
    });
  }

  private List<Guest> getGuestsWithout(String sessionId, List<Guest> guests) {
    return guests.stream().filter(g -> !g.getSessionId().equals(sessionId)).collect(Collectors.toList());
  }

  private Room findRoomById(String id) {
    return rooms.stream().filter(r -> r.getId()
        .equals(id))
        .findAny()
        .orElseThrow();
  }
}
