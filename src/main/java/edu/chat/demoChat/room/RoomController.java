package edu.chat.demoChat.room;

import edu.chat.demoChat.room.service.MemoryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/room")
public class RoomController {
  private final MemoryRoomService roomService;

  @PostMapping("create")
  public ResponseEntity<Room> createRoom() {
    try {
      var createdRoom = roomService.createRoom();

      return ResponseEntity.ok(createdRoom);
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
}