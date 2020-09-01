package edu.chat.demoChat.message.repository;

import edu.chat.demoChat.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository("memoryMessageRepository")
public class MemoryMessageRepository implements MessageRepository {
  private final List<Message> messageList;

  @Override
  public List<Message> findByRoomId(String roomId) {
    return messageList.stream()
        .filter(msg -> msg.getRoomId().equals(roomId))
        .collect(Collectors.toList());
  }

  @Override
  public Message save(Message message) {
    messageList.add(message);

    return message;
  }
}
