package edu.chat.demoChat.controller;

import edu.chat.demoChat.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@RestController
@CrossOrigin("*")
@RequestMapping("api/room")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @GetMapping("create")
  public ResponseEntity<String> createRoom() {
    try {
      return ResponseEntity.ok(roomService.createRoom().getId());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{roomId}")
  public ResponseEntity<Room> getRoom(@PathVariable("roomId") String roomId) {
    try {
      var room = roomService.getRoom(roomId);

      return ResponseEntity.ok(room);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @MessageMapping("/room/{roomId}/registerGuest")
  public void registerGuest(@DestinationVariable String roomId, @Header("simpSessionId") String sessionId) {
    roomService.joinUser(roomId, sessionId);
  }

  @EventListener
  private void handleSessionDisconnect(SessionDisconnectEvent event) {
    roomService.leaveGuest(event.getSessionId());
  }
}