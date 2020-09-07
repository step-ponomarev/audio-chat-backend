package edu.chat.demoChat.room.service;

import edu.chat.demoChat.room.Room;
import edu.chat.demoChat.room.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.room.janus.service.JanusService;
import edu.chat.demoChat.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service("memoryRoomService")
@RequiredArgsConstructor
public class MemoryRoomService implements RoomService {
  @Qualifier("memoryRoomRepository")
  private final RoomRepository roomRepository;
  private final JanusService janusService;

  private enum Requests {
    CREATE("create");

    private final String req;

    Requests(String req) {
      this.req = req;
    }

    @Override
    public String toString() {
      return req;
    }
  }

  @Override
  public Room createRoom() {
    try {
      var id = UUID.randomUUID().toString();
      var transaction = UUID.randomUUID().toString();

      final var newRoom = new Room(id, LocalDateTime.now());
      final var createRoomDto =
          CreateAudioRoomDTO
              .builder()
              .request(Requests.CREATE.toString())
              .room(id)
              .allowed(new String[]{})
              .transaction(transaction)
              .build();

      janusService.createAudioRoom(createRoomDto);

      return roomRepository.save(newRoom);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
      //TODO: реализовать ошибку создания аудио комнаты
    }
  }

  @Override
  public Room getRoom(String roomId) {
    return roomRepository.findById(roomId);
  }
}
