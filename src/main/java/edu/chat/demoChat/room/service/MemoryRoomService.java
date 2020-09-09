package edu.chat.demoChat.room.service;

import edu.chat.demoChat.room.Room;
import edu.chat.demoChat.janus.dto.AttachAudioBridgePluginDTO;
import edu.chat.demoChat.janus.dto.CreateAudioRoomBody;
import edu.chat.demoChat.janus.dto.CreateAudioRoomDTO;
import edu.chat.demoChat.janus.dto.CreateSessionDTO;
import edu.chat.demoChat.janus.service.JanusService;
import edu.chat.demoChat.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
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
      final var roomId = UUID.randomUUID().toString();
      final var transaction = UUID.randomUUID().toString();
      final var newRoom = new Room(roomId, LocalDateTime.now());
      final var sessionId = createAndGetSession(transaction);
      final var handleId = createAndGetHandle(transaction, sessionId);

      final var createRoomBody =
          CreateAudioRoomBody
              .builder()
              .room(roomId)
              .is_private(false)
              .request("create")
              .build();

      final var createRoomDto =
          CreateAudioRoomDTO
              .builder()
              .transaction(transaction)
              .body(createRoomBody)
              .build();

      janusService.createAudioRoom(createRoomDto, sessionId, handleId);

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

  private String createAndGetSession(String transaction) throws IOException, InterruptedException {
    final var createSessionDto =
        CreateSessionDTO
            .builder()
            .transaction(transaction)
            .build();

    return janusService.createSession(createSessionDto);
  }

  private String createAndGetHandle(String transaction, String sessionId) throws IOException, InterruptedException {
    final var attachAudioBridgePluginDTO =
        AttachAudioBridgePluginDTO
            .builder()
            .transaction(transaction)
            .build();

    return janusService.attachAudioBridgePlugin(attachAudioBridgePluginDTO, sessionId);
  }
}
