package edu.chat.demoChat.controller;

import edu.chat.demoChat.model.PeerConnectDTO;
import edu.chat.demoChat.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PeerController {
  private final RoomService roomService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/connectToPeer")
  public void registerPeer(@Payload PeerConnectDTO peerConnectDTO) {
    System.err.println(peerConnectDTO.toString());
    messagingTemplate.convertAndSend("/peerRequest/" + peerConnectDTO.getUserId(), "success!");
  }

  public ResponseEntity<Room> createRoom() {
    try {
      return ResponseEntity.ok(roomService.createRoom());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
