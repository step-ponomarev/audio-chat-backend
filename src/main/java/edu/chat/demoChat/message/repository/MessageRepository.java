package edu.chat.demoChat.message.repository;

import edu.chat.demoChat.message.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
  List<Message> findAllByRoomId(String roomId);
}
