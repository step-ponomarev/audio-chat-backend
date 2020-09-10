package edu.chat.demoChat.message;

import edu.chat.demoChat.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {
  private final MessageService messageService;

  @GetMapping("room/{roomId}")
  public ResponseEntity<List<Message>> getMessages(@PathVariable("roomId") String roomId) {
    try {
      return ResponseEntity.ok(messageService.getMessages(roomId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @MessageMapping("message/room/{roomId}/guest/{guestId}/sendMessage")
  public void sendMessage(@DestinationVariable("roomId") String roomId,
                          @DestinationVariable("guestId") String guestId,
                          @Payload String message) {

    messageService.sendMessage(roomId, guestId, message);
  }
}
