package edu.chat.demoChat.guest;

import edu.chat.demoChat.guest.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/guest")
public class GuestController {
  private final GuestService guestService;

  @GetMapping("room/{roomId}")
  public ResponseEntity<List<Guest>> getGuests(@PathVariable("roomId") String roomId, @Header("simpSessionId") String sessionId) {
    try {
      return ResponseEntity.ok(guestService.getGuests(roomId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @MessageMapping("guest/room/{roomId}/registerGuest")
  public void registerGuest(@DestinationVariable String roomId, @Header("simpSessionId") String sessionId) {

    guestService.addGuest(sessionId, roomId);
  }
}
