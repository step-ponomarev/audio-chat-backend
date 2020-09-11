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
  public ResponseEntity<List<Guest>> getGuests(@PathVariable("roomId") String roomId) {
    try {
      return ResponseEntity.ok(guestService.getGuests(roomId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{guestId}")
  public ResponseEntity<Guest> getGuest(@PathVariable("guestId") String guestId) {
    try {
      return ResponseEntity.ok(guestService.getGuest(guestId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("room/{roomId}/create")
  public ResponseEntity<Guest> createGuest(@PathVariable("roomId") String roomId) {
    try {
      return ResponseEntity.ok(guestService.createGuest(roomId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("{guestId}/remove")
  public ResponseEntity removeGuest(@PathVariable("guestId") String guestId) {
    try {
      guestService.removeGuest(guestId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @MessageMapping("guest/{guestId}/room/{roomId}/registerGuest")
  public void registerUser(@Header("simpSessionId") String sessionId,
                           @DestinationVariable("guestId") String guestId,
                           @DestinationVariable("guestId") String roomId) {
    guestService.registerUser(sessionId, guestId, roomId);
  }
}
