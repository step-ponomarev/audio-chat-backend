package edu.chat.demoChat.controller;

import edu.chat.demoChat.model.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@RestController
@CrossOrigin("*")
@RequestMapping("api")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @MessageMapping("/room/{roomId}/registerGuest")
  public void registerGuest(@DestinationVariable String roomId, @Header("simpSessionId") String sessionId) {
    roomService.registerUser(roomId, sessionId);
  }

  @EventListener
  private void handleSessionDisconnect(SessionDisconnectEvent event) {
    roomService.removeGuest(event.getSessionId());
  }
}