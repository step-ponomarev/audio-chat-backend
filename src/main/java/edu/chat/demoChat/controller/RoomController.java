package edu.chat.demoChat.controller;

import edu.chat.demoChat.model.Guest;
import edu.chat.demoChat.model.Message;
import edu.chat.demoChat.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/room")
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

  @GetMapping("{roomId}/guest")
  public ResponseEntity<List<Guest>> getGuestsWithoutCurrent(@PathVariable("roomId") String roomId, @Header("simpSessionId") String sessionId) {
    try {
      return ResponseEntity.ok(roomService.getGuestsWithoutCurrent(roomId, sessionId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{roomId}/message")
  public ResponseEntity<List<Message>> getMessages(@PathVariable("roomId") String roomId) {
    try {
      return ResponseEntity.ok(roomService.getMessages(roomId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @MessageMapping("/sendMessage")
  public void sendMessage(@Header("simpSessionId") String sessionId, @Payload String message) {
    roomService.sendMessage(sessionId, message);
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