package edu.chat.demoChat.message.repository;

import edu.chat.demoChat.message.Message;

import java.util.List;

public interface MessageRepository {
  Message save(Message message);

  List<Message> findByRoomId(String roomId);
}
